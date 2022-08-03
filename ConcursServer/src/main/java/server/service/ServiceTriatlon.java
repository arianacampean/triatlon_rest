package server.service;

import triatlon.domain.Arbitru;
import triatlon.domain.Participant;
import triatlon.domain.Proba;
import triatlon.domain.Proba_Participanti;
import triatlon.repository.interfete.RepoArbitru;
import triatlon.repository.interfete.RepoPP;
import triatlon.repository.interfete.RepoParticipanti;
import triatlon.repository.interfete.RepoProba;
import triatlon.services.IService;
import triatlon.services.TriatlonException;
import triatlon.services.TriatlonObserver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceTriatlon implements IService {

        private RepoArbitru repo_arb;
        private RepoParticipanti repo_part;
        private RepoProba repo_proba;
        private RepoPP repo_pp;
    private Map<Long, TriatlonObserver> loggedClients;


        public ServiceTriatlon(RepoArbitru repo_arb, RepoParticipanti repo_part, RepoProba repo_proba, RepoPP repo_pp) {
            this.repo_arb = repo_arb;
            this.repo_part = repo_part;
            this.repo_proba = repo_proba;
            this.repo_pp = repo_pp;
            this.loggedClients=new ConcurrentHashMap<>();;
        }


    @Override
    public synchronized void add_pp(Proba_Participanti p) throws TriatlonException {
            try {
                repo_pp.add(p);
                notifyAdaugareScor(p);
            }catch (Exception e) {
                throw new TriatlonException(e.getMessage());
            }
    }

    @Override
    public void login(Arbitru ar, TriatlonObserver tri) throws TriatlonException {
           Arbitru arb=repo_arb.findArbitru(ar.getUsername(),ar.getParola());
        if(arb!=null){
            if(loggedClients.get(arb.getId())!=null)
            {
                throw new TriatlonException("arb deja logat");
            }
            System.out.println("am pus aici");
            loggedClients.put(arb.getId(),tri);
        }else
        {
            throw new TriatlonException("Login nereusit");
        }
    }

    @Override
    public void logout(Arbitru arb, TriatlonObserver tri) {
        loggedClients.remove(arb);
    }

    @Override
    public  synchronized  Iterable<Participant> getAll_part() {
        return repo_part.findAll();
    }

    @Override
    public  synchronized  Iterable<Proba> getAll_proba() {
        return repo_proba.findAll();
    }

    @Override
    public  synchronized  Iterable<Proba_Participanti> getAll_pp() {
        return repo_pp.findAll();
    }

    @Override
    public  synchronized  Arbitru getOne_arb_log(String username, String parola) {
        return repo_arb.findArbitru(username,parola);
    }


    @Override
    public  synchronized  Proba getOne_proba(long id) {
        return repo_proba.findProba(id);
    }



    private final int defaultThreadsNo=9;

    private void notifyAdaugareScor(Proba_Participanti part) throws TriatlonException
    {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (var integer : loggedClients.keySet()) {
            TriatlonObserver client = loggedClients.get(integer);
            executor.execute(()->{
                try{
                    System.out.println("Notifying ");
                    client.update_puncte(part);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        executor.shutdown();
    }
}

