package triatlon.domain;

public class Arbitru extends Entity<Long>{
    private String nume;
    private String prenume;
    private String username;
    private String parola;

    public Arbitru(String nume, String prenume, String username, String parola) {
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.parola = parola;
    }
    public Arbitru(String username, String parola) {

     this(" "," ",username,parola);
    }
        public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "arbitru:"+
                 nume +" "+prenume +" "+ username +" "+ parola ;
    }
}
