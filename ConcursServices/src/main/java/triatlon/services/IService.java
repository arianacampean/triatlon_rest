package triatlon.services;

import triatlon.domain.Arbitru;
import triatlon.domain.Participant;
import triatlon.domain.Proba;
import triatlon.domain.Proba_Participanti;

import java.util.List;

public interface IService {
    public void add_pp(Proba_Participanti p) throws TriatlonException;
    public void login(Arbitru arb,TriatlonObserver tri) throws TriatlonException ;
    public void logout(Arbitru arb,TriatlonObserver tri) throws TriatlonException ;
    public Iterable<Participant>getAll_part() throws TriatlonException;
    public Iterable<Proba>getAll_proba() throws TriatlonException;
    public Iterable<Proba_Participanti>getAll_pp() throws TriatlonException;
    public Arbitru getOne_arb_log(String username,String parola) throws TriatlonException;

    public Proba getOne_proba(long id);




}
