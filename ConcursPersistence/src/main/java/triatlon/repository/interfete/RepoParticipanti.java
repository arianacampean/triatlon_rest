package triatlon.repository.interfete;


import triatlon.domain.Participant;

public interface RepoParticipanti extends Repository<Long,Participant> {
  Participant findParticipant(Long cod);
}
