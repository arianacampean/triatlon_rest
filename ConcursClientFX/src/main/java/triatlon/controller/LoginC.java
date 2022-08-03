package triatlon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.service.ServiceTriatlon;
import triatlon.domain.Arbitru;
import triatlon.services.IService;
import triatlon.services.TriatlonException;
import triatlon.services.TriatlonObserver;

import java.io.IOException;

public class LoginC {
    private IService serv;

    Stage stage;
    StartPageC crtPrincipal;
    Parent prt;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldParola;
    private StartPageC strtC;
    private AnchorPane anchorPane;


    public void setService(IService serv) {
        this.serv = serv;



    }


    @FXML
    private void logIn(ActionEvent actionEvent) throws TriatlonException {
        String prim= textFieldUsername.getText();
        String doi=textFieldParola.getText();
        Arbitru arb=new Arbitru(prim,doi);
        try {
            serv.login(arb,crtPrincipal);
            Arbitru arbb=serv.getOne_arb_log(arb.getUsername(),arb.getParola());
          //  showLogIn(arbb);
            Stage stage=new Stage();
            stage.setTitle("Start Page");
            stage.setScene(new Scene(prt));

//            stage.setOnCloseRequest(event -> {
//                crtPrincipal.exit();
//                System.exit(0);
//            });
            //asta
            crtPrincipal.setArbitru(arbb);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch(Exception e){
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
//        if (arbitru != null) {
//            showLogIn(arbitru);
//            textFieldParola.clear();
//            textFieldUsername.clear();
//        } else MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Log in", "Parola/username incorect!");
    }


    public void showLogIn(Arbitru user3) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/StartPage.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Home Page");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root, 600, 450);
            dialogStage.setScene(scene);
            StartPageC utilCont = loader.getController();
          //  utilCont.setService(serv, user3,dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void setControllerPrincipal(StartPageC crt){
        crtPrincipal=crt;
    }
    public void setParent(Parent p) {
        this.prt = p;
    }

}
