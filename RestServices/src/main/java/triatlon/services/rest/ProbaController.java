package triatlon.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import triatlon.domain.Proba;
import triatlon.repository.ProbaRepo;
import triatlon.repository.RepositoryException;

import java.util.List;
import java.util.Properties;


/**
 * Created by grigo on 5/10/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/triatlon/probe")
public class ProbaController {

    private static final String template = "Hello, %s!";

    @Autowired
    private ProbaRepo prRepository;



    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping( method=RequestMethod.GET)
    public List<Proba> getAll(){
        return (List<Proba>) prRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable long id){

        Proba prob=prRepository.findProba(id);
        if (prob==null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Proba>(prob, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Proba create(@RequestBody Proba pr){
        System.out.println("Add proba ... ");
        prRepository.add(pr);
        return pr;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Proba update(@RequestBody Proba user) {
        System.out.println("Updating user ...");
        prRepository.update(user.getId(),user);
        return user;

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable long id){
        System.out.println("Deleting proba ... "+id);
        try {
            prRepository.delete(id);
            return new ResponseEntity<Proba>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping("/{proba}/name")
    public String name(@PathVariable long proba){
        Proba result=prRepository.findProba(proba);
        System.out.println("Result ..."+result);

        return result.getNume();
    }

    @RequestMapping("/{proba}/cod_arb")
    public long cod_arb(@PathVariable long proba){
        Proba result=prRepository.findProba(proba);
        System.out.println("Result ..."+result);

        return result.getId_arb();
    }



    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}