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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class ClientRpcReflectionWorker implements Runnable, TriatlonObserver {
    private IService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientRpcReflectionWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }

    }
    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        try {
            Arbitru arb=(Arbitru)request.data();
            server.logout(arb,this);
            connected=false;
            return okResponse;

        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        ArbitruDto udto=(ArbitruDto) request.data();
        Arbitru arb=DtoUtils.getFromDTO(udto);
        try {
            server.login(arb,this);
            return  new Response.Builder().type(ResponseType.LOGGED_IN).build();
        } catch (TriatlonException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleNEW_SCORE(Request request){
        System.out.println("Login request ..."+request.type());
        Proba_Participanti pr=(Proba_Participanti) request.data();
        try {
            server.add_pp(pr);
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (TriatlonException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }

    private Response handleGET_PART(Request request){
        System.out.println("Login request ..."+request.type());
        Participant pr=(Participant) request.data();
        try {
            List<Participant> p= (List<Participant>) server.getAll_part();
            return new Response.Builder().type(ResponseType.PARTICIPANTI_GASITI).data(p).build();
        } catch (TriatlonException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }
    private Response handleGET_PROBA(Request request){
        System.out.println("Login request ..."+request.type());
        Proba pr=(Proba)request.data();
        try {
            List<Proba> p= (List<Proba>) server.getAll_proba();
            return new Response.Builder().type(ResponseType.PROBE_GASITE).data(p).build();
        } catch (TriatlonException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_ARB(Request request){
        System.out.println("Login request ..."+request.type());
        Arbitru pr=(Arbitru) request.data();
        try {
            Arbitru p= (Arbitru)server.getOne_arb_log(pr.getUsername(),pr.getParola());
            return new Response.Builder().type(ResponseType.ARBITRU_GASIT).data(p).build();
        } catch (TriatlonException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleGET_PP(Request request){
        System.out.println("Login request ..."+request.type());
        Proba_Participanti pr=(Proba_Participanti) request.data();
        try {
            List<Proba_Participanti> p= (List<Proba_Participanti>) server.getAll_pp();
            return new Response.Builder().type(ResponseType.PP_GASIT).data(p).build();
        } catch (TriatlonException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }



    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }

        return response;
    }





    @Override
    public void update_puncte(Proba_Participanti p) throws TriatlonException {
        Response resp=new Response.Builder().type(ResponseType.NEW_SCORE_ADD).data(p).build();
        System.out.println("adaugare updated:  "+p);
        try {
            sendResponse(resp);
        } catch (IOException e) {
            throw new TriatlonException("Sending error: "+e);
        }
    }

    }

