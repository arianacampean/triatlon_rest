package triatlon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import server.service.ServiceTriatlon;
import triatlon.domain.Arbitru;
import triatlon.domain.Participant;
import triatlon.domain.Proba;
import triatlon.domain.Proba_Participanti;
import triatlon.services.IService;
import triatlon.services.TriatlonException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class raportC {
    ObservableList<String> model= FXCollections.observableArrayList();
    Arbitru arb;
    Stage diagStage;
    private IService serv;
    @FXML
    ListView<String> listView;


    public void setService( IService serv, Arbitru arb, Stage diagStage){
        this.serv=serv;
        this.arb=arb;
        this.diagStage=diagStage;
        afis_part();


    }
    @FXML
    public void initialize() {
        listView.setItems(model);
    }

    public void afis_part() {
        try {
            Iterable<Participant> ceva = serv.getAll_part();
            Iterable<Proba> all = serv.getAll_proba();
            List<String> list = new ArrayList<>();
            List<Proba_Participanti> pp= new ArrayList<>();
            long id_proba = 0;
            for (Proba p : all) {
                if (p.getCod_arb() == arb.getId())
                    id_proba = p.getId();
            }
            //List<Proba_Participanti> pp = serv.getOne_pp_proba(id_proba);
            List<Proba_Participanti> liss = (List<Proba_Participanti>) serv.getAll_pp();
            for(Proba_Participanti pi:liss)
            {
                if(pi.getId_proba()==id_proba)
                    pp.add(pi);
            }


            List<Proba_Participanti> lis = pp.stream()
                    .sorted((x1, x2) -> (int) (x2.getPuncte_obtinute() - x1.getPuncte_obtinute()))
                    .collect(Collectors.toList());


            for (Proba_Participanti p : lis) {
                for (Participant part : ceva) {
                    if (part.getId() == p.getId_participant()) {
                        String s =part.getNume() + " " + part.getPrenume() + " a obtinut " + p.getPuncte_obtinute() + " de puncte";
                        list.add(s);

                    }
                }

            }
            model.setAll(list);
        }catch (TriatlonException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void exit()
    {
        diagStage.close();
    }


}
