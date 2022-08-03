import server.service.ServiceTriatlon;
import triatlon.repository.ArbitruRepo;
import triatlon.repository.PPRepo;
import triatlon.repository.ParticipantRepo;
import triatlon.repository.ProbaRepo;
import triatlon.services.IService;
import utils.AbstractServer;
import utils.RpcConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties props=new Properties();
        try {
            props.load(StartRpcServer.class.getResourceAsStream("/triatlonserver.properties"));
            System.out.println("Server properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        ArbitruRepo arbrepo=new ArbitruRepo(props);
        ParticipantRepo partrepo=new ParticipantRepo(props);
        ProbaRepo probarepo=new ProbaRepo();
        PPRepo pprepo=new PPRepo(props);
        IService serv=new ServiceTriatlon(arbrepo,partrepo,probarepo,pprepo);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(props.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new RpcConcurrentServer(chatServerPort, serv);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
