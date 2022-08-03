package triatlon.repository.interfete;


import triatlon.domain.Proba_Participanti;

import java.util.List;

public interface RepoPP extends Repository<Long, Proba_Participanti> {
List<Proba_Participanti> findPP_codPart(Long cod_part);
List<Proba_Participanti> findPP_codProba(Long cod_proba);
}
