package start;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import traitlon.client.ProbaClient;
import triatlon.domain.Proba;
import triatlon.services.rest.ServiceException;

import java.util.List;

public class StartRestClient {
    private final static ProbaClient usersClient=new ProbaClient();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        Proba userT=new Proba("test123", (long) 3);
        try{


           show(()-> System.out.println(usersClient.create(userT)));
            Proba up=new Proba("atletism", (long) 2);
            up.setId((long) 28);
            show(()-> usersClient.update(up));
            show(()-> System.out.println(usersClient.getById(3)));
            System.out.println("Am facut update");
            show(()->usersClient.delete(23));
            System.out.println("am facut stergerea");
            show(()->{
                Proba[] res=usersClient.getAll();
                for(Proba u:res){
                    System.out.println(u.getId()+": "+u.getNume()+" "+u.getId_arb());
                }
            });


        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}

