package tp;

import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import utils.Fail;
import utils.*;
import utils.Success;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
@CrossOrigin
public class Hello {

    public static void main(String[] args) {
        SpringApplication.run(Hello.class, args);
    }

    @GetMapping("/vehicules")
    String all() throws Exception {
        Vehicule am = new Vehicule();
        Gson gson = new Gson();
        Data data = new Data();
        data.setData(am.select(null));
        String texte = gson.toJson(data);
        return texte;
    }
    //    @GetMapping("/vehicules/{id}/photo")
// 
       @GetMapping("/vehicules/{id}/photo")
    String updatePhoto(@PathVariable int id,@RequestHeader String photos) throws Exception {
        Vehicule am = new Vehicule();
        am.setId(id);
        am.setPhoto(photos);
        am.update("Id",null);
        Gson gson = new Gson();
        Data data = new Data();
        data.setData(am.select(null));
        String texte = gson.toJson(data);
        return texte;
    }
 @GetMapping("/vehicules/assurances/3")
    String getalls() throws Exception {
        Assurance am = new Assurance();
        Gson gson = new Gson();
        ArrayList<Vehicule>lsit=am.getVehiculesExp3mois();
        Assurance one = ((Assurance) (am.select(null)).get(0));
        Data data = new Data();
        data.setData(lsit);
        return gson.toJson(data);
    }@GetMapping("/vehicules/assurances/1")
    String getall_() throws Exception {
        Assurance am = new Assurance();
        Gson gson = new Gson();
        ArrayList<Vehicule>lsit=am.getVehiculesExpmois();
        Assurance one = ((Assurance) (am.select(null)).get(0));
        Data data = new Data();
        data.setData(lsit);
        return gson.toJson(data);
    }
    
    @GetMapping("/assurances")
    String getall() throws Exception {
        Assurance am = new Assurance();
        Gson gson = new Gson();
        Assurance one = ((Assurance) (am.select(null)).get(0));
        Data data = new Data();
        ArrayList lisp=new ArrayList();
        lisp.add(one);
        data.setData(lisp);
        return gson.toJson(data);
    }
    

    @GetMapping("/vehicules/{id}")
    String getall(@PathVariable int id) throws Exception {
        Vehicule am = new Vehicule();
        am.setId(id);
        Gson gson = new Gson();
        Vehicule one = ((Vehicule) (am.select(null)).get(0));
        Data data = new Data();
         Kilometrage amd = new Kilometrage();
        amd.setVehiculeId(id);
        ArrayList lisp=new ArrayList();
        lisp.add(one);
        lisp.add(amd.select(null));
        
        data.setData(lisp);
        return gson.toJson(data);
    }

    @PostMapping("/vehicules")
    String create(@RequestParam String nomChauffeur,
            @RequestParam String matricule) throws Exception {
        Gson gson = new Gson();
        HashMap _val_ = new HashMap<String, Object>();
        String texte = "";
        try {
            Vehicule one = new Vehicule();
            one.setMatricule(matricule);
            one.setNomChauffeur(nomChauffeur);
            one.insert(null);
            texte = gson.toJson(new Message(new Success(((Vehicule) one.getLastObject()).getId(), "Success")));
        } catch (Exception ex) {
//            _val_.put("datas", new Fail( "Error deleting",));
            texte = gson.toJson(new Message(new Fail("Error deleting", "404")));
        }
        return texte;
//    return repository.save(newEmployee);
    }
    // Single item

    @PutMapping("/vehicules/{id}")
    String update(@PathVariable int id, @RequestParam String nomChauffeur,
            @RequestParam String matricule) throws Exception {
        HashMap _val_ = new HashMap<String, Object>();
        Gson gson = new Gson();
        String texte = "";
        try {
            Vehicule one = new Vehicule();
            one.setId(id);
            one.setMatricule(matricule);
            one.setNomChauffeur(nomChauffeur);
            one.update("id", null);
            texte = gson.toJson(new Message(new Success(id, "Update")));
        } catch (Exception ex) {
            texte = gson.toJson(new Message(new Fail("500", "Error deleting")));
        }
        return texte;
    }

    @DeleteMapping("/vehicules/{id}")
    String del(@PathVariable int id) throws Exception {
        Gson gson = new Gson();
        String texte = "";
        try {
            Vehicule one = new Vehicule();
            one.setId(id);
            one.delete("id", null);

            texte = gson.toJson(new Message(new Success(id, "Delete")));
        } catch (Exception ex) {
            texte = gson.toJson(new Message(new Fail("500", "Error deleting")));
        }
        return texte;
    }

}
