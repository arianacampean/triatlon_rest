package triatlon.domain;

public class Proba_Participanti extends Entity<Long>{
    private Long id_proba;
    private Long id_participant;
    private int puncte_obtinute;

    public Proba_Participanti(Long id_proba, Long id_participant, Integer puncte_obtinute) {
        this.id_proba = id_proba;
        this.id_participant = id_participant;
        this.puncte_obtinute = puncte_obtinute;
    }
//    public Proba_Participanti( Long id_participant, Integer puncte_obtinute) {
//
//        this.id_participant = id_participant;
//        this.puncte_obtinute = puncte_obtinute;
//    }

    public float getPuncte_obtinute() {
        return puncte_obtinute;
    }

    public void setPuncte_obtinute(int puncte_obtinute) {
        this.puncte_obtinute = puncte_obtinute;
    }

    public Long getId_proba() {
        return id_proba;
    }

    public void setId_proba(Long id_proba) {
        this.id_proba = id_proba;
    }

    public Long getId_participant() {
        return id_participant;
    }

    public void setId_participant(Long id_participant) {
        this.id_participant = id_participant;
    }

    @Override
    public String toString() {
        return "proba-part:" + id_proba +
                " " + id_participant +
                " " + puncte_obtinute;
    }
}
