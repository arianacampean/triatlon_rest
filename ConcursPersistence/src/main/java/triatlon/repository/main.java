package triatlon.repository;

import triatlon.domain.Proba;

public class main {
    public static void main(String[] args) {
        ProbaRepo pr=new ProbaRepo();
        pr.delete((long) 6);
        Proba p=new Proba("balet", (long) 3);
       // pr.add(p);
        pr.update(4,p);
    }
}
