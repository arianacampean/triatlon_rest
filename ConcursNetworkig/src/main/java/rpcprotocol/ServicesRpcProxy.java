package rpcprotocol;

import dto.ArbitruDto;
import dto.DtoUtils;
import triatlon.domain.Arbitru;
import triatlon.domain.Participant;
import triatlon.domain.Proba;
import triatlon.domain.Proba_Participanti;
import triatlon.services.IService;
import triatlon.services.TriatlonException;
import triatlon.services.TriatlonObserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesRpcProxy implements IService {
    private String host;
    private int port;

    private TriatlonObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }


    @Override
    public void add_pp(Proba_Participanti p) throws TriatlonException {
        Request req = new Request.Builder().type(RequestType.NEW_SCORE).data(p).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new TriatlonException(err);
        }
    }

    @Override
    public void login(Arbitru arb, TriatlonObserver tri) throws TriatlonException {
        initializeConnection();
        ArbitruDto udto=DtoUtils.getDTO(arb);
        Request req = (new Request.Builder()).type(RequestType.LOGIN).data(udto).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.LOGGED_IN) {
            this.client = tri;
            return;
        }
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            this.closeConnection();
            throw new TriatlonException(err);
        }
    }

    @Override
    public void logout(Arbitru arb, TriatlonObserver tri) throws TriatlonException {
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(arb).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new TriatlonException(err);
        }
    }

    @Override
    public Iterable<Participant> getAll_part() throws TriatlonException {
        Request req = (new Request.Builder()).type(RequestType.GET_PART).data(null).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new TriatlonException(err);
        }
        List<Participant> part = (List<Participant>)response.data();
        return part;
    }

    @Override
    public Iterable<Proba> getAll_proba() throws TriatlonException{
        Request req = (new Request.Builder()).type(RequestType.GET_PROBA).data(null).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new TriatlonException(err);
        }
        List<Proba> part = (List<Proba>)response.data();
        return part;
    }


    @Override
    public Iterable<Proba_Participanti> getAll_pp() throws TriatlonException{
        Request req = (new Request.Builder()).type(RequestType.GET_PP).data(null).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            throw new TriatlonException(err);
        }
        List<Proba_Participanti> part = (List<Proba_Participanti>)response.data();
        return part;
    }

    @Override
    public Arbitru getOne_arb_log(String username, String parola) throws TriatlonException {
        Arbitru arb=new Arbitru(username,parola);
        Request req = (new Request.Builder()).type(RequestType.GET_ARB).data(arb).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.ARBITRU_GASIT) {
           return (Arbitru)response.data();
        }
        else throw new TriatlonException("erare log");
    }


    @Override
    public Proba getOne_proba(long id) {
        return null;
    }




    private void sendRequest(Request request)throws TriatlonException{
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new TriatlonException("Error sending object "+e);
        }

    }
    private Response readResponse() throws TriatlonException {
        Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void initializeConnection() throws TriatlonException {
        try {
            this.connection = new Socket(this.host, this.port);
            this.output = new ObjectOutputStream(this.connection.getOutputStream());
            this.output.flush();
            this.input = new ObjectInputStream(this.connection.getInputStream());
            this.finished = false;
            this.startReader();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
    private void closeConnection() {
        this.finished = true;
        try {
            this.input.close();
            this.output.close();
            this.connection.close();
            this.client = null;
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.NEW_SCORE_ADD;
    }


    private void handleUpdate(Response response) {
        Proba_Participanti p =(Proba_Participanti) response.data();
        System.out.println("Proba part updated: " + p);
        try {
            client.update_puncte(p);
        } catch (TriatlonException var6) {
            var6.printStackTrace();
        }
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while(!finished) {
                try {
                    Object response =input.readObject();
                   System.out.println("response received " + response);
                    if (isUpdate((Response)response)) {
                        handleUpdate((Response)response);
                    } else {
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException var3) {
                            var3.printStackTrace();
                        }
                    }
                } catch (IOException var4) {
                    System.out.println("Reading error " + var4);
                } catch (ClassNotFoundException var5) {
                    System.out.println("Reading error " + var5);
                }
            }

        }
    }






}
