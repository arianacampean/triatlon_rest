package dto;


import triatlon.domain.Arbitru;
import triatlon.domain.Participant;
import triatlon.domain.Proba_Participanti;
import triatlon.repository.ParticipantRepo;
import triatlon.services.IService;

import java.util.ArrayList;
import java.util.List;

public class DtoUtils {
//    public static IService serv;
//    public static Participant getFromDto(ParticipantiDto dto)
//    {
//        long id=dto.getId();
//        String nume=dto.getNume();
//        String prenume=dto.getPrenume();
//        Iterable<Participant>all=serv.getAll_part();
//        float pct = 0;
//        for(Participant pal:all) {
//            pct = serv.total_puncte(pal);
//        }
//        Participant p=new Participant(nume,prenume);
//        p.setId(id);
//        return p;
//
//    }
//
//    public static Proba_PartDto getDtoP (Proba_Participanti dto)
//    {
//        Participant p=serv.getOne_part(dto.getId_participant());
//        String nume=p.getNume();
//        String prenume=p.getPrenume();
//        Iterable<Participant>all=serv.getAll_part();
//        float pct = 0;
//        for(Participant pal:all) {
//            pct = serv.total_puncte(pal);
//        }
//        return new Proba_PartDto(nume,prenume, pct);
//    }
public static Arbitru getFromDTO(ArbitruDto usdto){
    String nume=usdto.getUsername();
    String pass=usdto.getPassword();

    return new Arbitru(nume, pass);

}
    public static ArbitruDto getDTO(Arbitru arb){
        String nume=arb.getUsername();
        String pass=arb.getParola();

        return new ArbitruDto(nume,pass);

    }
}

