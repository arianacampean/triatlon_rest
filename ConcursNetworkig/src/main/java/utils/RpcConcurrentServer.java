
package utils;

import rpcprotocol.ClientRpcReflectionWorker;
import triatlon.services.IService;

import java.net.Socket;


public class RpcConcurrentServer extends AbsConcurrentServer {
    private IService chatServer;
    public RpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
       // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        ClientRpcReflectionWorker worker=new ClientRpcReflectionWorker(chatServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
