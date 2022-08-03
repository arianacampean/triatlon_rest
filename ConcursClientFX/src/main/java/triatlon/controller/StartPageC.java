package triatlon.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import triatlon.domain.Arbitru;
import triatlon.domain.Participant;
import triatlon.domain.Proba;
import triatlon.domain.Proba_Participanti;
import triatlon.services.IService;
import triatlon.services.TriatlonException;
import triatlon.services.TriatlonObserver;

import java.io.IOException;
import java.lang.constant.Constable;
import java.net.URL;
import java.util.*;

public class StartPageC implements Initializable, TriatlonObserver {
    private IService serv;
    ObservableList<String> model= FXCollections.observableArrayList();
    Arbitru arb;
    Stage diagStage;
    @FXML
    private TextField nume;
    @FXML
    private TextField proba;
    @FXML
    private TextField adauga_id;
    @FXML
    private TextField adauga_scor;
    @FXML
    private TextField afis_puncte;
    @FXML
    ListView<String> listView;


    public void setService(IService serv) {
        this.serv = serv;


    }

    @FXML
    public void initialize() {
        listView.setItems(model);
    }

    public void nume_arb()
    {
        nume.setText(arb.getNume()+" "+arb.getPrenume());
    }
    public void afla_proba()  {

        try {
            Iterable<Proba>all= serv.getAll_proba();
            for(Proba p:all)
                if(p.getId_arb()==arb.getId())
                    proba.setText(p.getNume());
        } catch (TriatlonException e) {
            e.printStackTrace();
        }

    }
    public void lista() {

        List<String> list=lista_part();
        java.util.Collections.sort(list);
        for (String s :list) {
            listView.getItems().add(s);
        }
       model.setAll(list);
    }
    public void afis_id()
    {
        try {
            Iterable<Participant> all = serv.getAll_part();
            Iterable<Proba_Participanti> pp = serv.getAll_pp();
            String s=" ";
            for (Participant p : all) {
                int index = 0;
                for (Proba_Participanti pr : pp) {
                    if (p.getId() == pr.getId_participant() && arb.getId() == pr.getId_proba())
                        index++;
                }
                if (index == 0) {
                    s=s+p.getId()+" ";

                }

            }
            afis_puncte.setText(s);

        }catch (TriatlonException e)
        {
            e.printStackTrace();
        }
    }
    public void adauga_pp() throws TriatlonException
    {

        long id= Long.parseLong(adauga_id.getText());
        Iterable<Proba>all=serv.getAll_proba();
        long id_proba=0;
        for(Proba p:all) {
            if (p.getCod_arb() == arb.getId())
                id_proba = p.getId();
        }
        float pct_obt=Float.parseFloat(adauga_scor.getText());
        Proba_Participanti prb=new Proba_Participanti(id_proba,id, (int) pct_obt);
        serv.add_pp(prb);
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Adaugare facuta cu succes", " ");
        adauga_scor.clear();
        adauga_id.clear();
        afis_id();


    }

    public void raportt()
    {
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/raport.fxml"));
            AnchorPane root= (AnchorPane) loader.load();
            Stage dialogStage=new Stage();
            dialogStage.setTitle("Raport");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene=new Scene(root,600,450);
            dialogStage.setScene(scene);
            raportC utilCont=loader.getController();
            utilCont.setService(serv,arb,dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void exit()
    {
         try {
            serv.logout(arb,this);
            System.exit(0);
        } catch (TriatlonException e) {
            System.out.println("Logout error " + e);
        }
    }
     public void setArbitru(Arbitru arb) throws TriatlonException
     {
         this.arb=arb;
         nume_arb();
         afla_proba();
         lista();
         afis_id();
     }

    public Map<Long,Float> total_puncte()
    {
        try {
            float nr=0;
            Map<Long,Float>pctt =new HashMap<>();
            List<Float>list=new ArrayList<>();
            Iterable<Proba_Participanti> all = serv.getAll_pp();
            for(Proba_Participanti i : all)
            {
                nr=0;
                for(Proba_Participanti j : all) {
                    if(i.getId_participant()==j.getId_participant())
                        nr=nr+j.getPuncte_obtinute();
                }
                pctt.put(i.getId_participant(),nr);


            }
            return pctt;
        }
         catch (TriatlonException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String>lista_part(){
        try {
            Iterable<Participant> all = serv.getAll_part();
            List<String> listA = new ArrayList<>();
            Map<Long,Float>map=total_puncte();
            float pct;
            for (Participant pal : all) {
                for(Map.Entry<Long, Float> idd:map.entrySet())
                    if(pal.getId()==idd.getKey()) {
                        String s = pal.getId() + " " + pal.getNume() + " " + pal.getPrenume() + " puncte obtinute in total: " +idd.getValue();
                        listA.add(s);
                    }
            }
            return listA;
        }catch (TriatlonException e) {
            e.printStackTrace();
        }
        return null;



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @Override
    public void update_puncte(Proba_Participanti p) throws TriatlonException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                listView.getItems().clear();
                lista();
            }
        });

    }
}
