package triatlon.services;

import triatlon.domain.Participant;
import triatlon.domain.Proba_Participanti;

public interface TriatlonObserver {
    void update_puncte(Proba_Participanti p) throws TriatlonException;

}
