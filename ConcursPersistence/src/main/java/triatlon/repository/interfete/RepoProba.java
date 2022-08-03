package triatlon.repository.interfete;

import triatlon.domain.Proba;

public interface RepoProba extends Repository<Long,Proba> {
 Proba findProba(long id);

}
