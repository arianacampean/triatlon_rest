package triatlon.domain;

import java.util.List;
import java.util.Map;

public class Participant extends Entity<Long> {
    private String nume;
    private String prenume;


    public Participant(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    @Override
    public String toString() {
        return "participant:"+nume+" "+prenume;
    }
}
