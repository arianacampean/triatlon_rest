import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rpcprotocol.ServicesRpcProxy;
import triatlon.controller.LoginC;
import triatlon.controller.StartPageC;
import triatlon.services.IService;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClientFX extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";



    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/triatlon.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService serv = new ServicesRpcProxy(serverIP, serverPort);
//        FXMLLoader loader=new FXMLLoader();
//        loader.setLocation(getClass().getResource("Login.fxml"));
//        AnchorPane root=loader.load();
//
//        LoginC ctrl=loader.getController();
//
//        primaryStage.setScene(new Scene(root, 600, 450));
//        primaryStage.setTitle("Log in");
//        ctrl.setService(serv,primaryStage);
//        primaryStage.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root=loader.load();
        //
        LoginC ctrl =loader.getController();
        ctrl.setService(serv);

        FXMLLoader cloader = new FXMLLoader(
                getClass().getResource("/StartPage.fxml"));
        Parent croot=cloader.load();

        StartPageC concursCtrl = cloader.getController();
        concursCtrl.setService(serv);

        ctrl.setControllerPrincipal(concursCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("LogIn");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



    }
}
