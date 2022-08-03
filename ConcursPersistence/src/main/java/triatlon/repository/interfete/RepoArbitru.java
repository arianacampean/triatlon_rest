package triatlon.repository.interfete;


import triatlon.domain.Arbitru;

public interface RepoArbitru extends Repository<Long, Arbitru>
{
   Arbitru findArbitru(String username,String parola);
  
}
