package triatlon.domain;

import java.util.List;

public class Proba extends Entity<Long>{
    private String nume;
    private Long id_arb;

    public Proba(){};
    public Proba(String nume, Long cod_arb) {
        this.nume = nume;
        this.id_arb = cod_arb;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    public Long getId_arb() {
        return id_arb;
    }

    public void setId_arb(Long id_arb) {
        this.id_arb = id_arb;
    }

    @Override
    public String toString() {
        return "proba:"+nume+" "+id_arb;
    }
}
