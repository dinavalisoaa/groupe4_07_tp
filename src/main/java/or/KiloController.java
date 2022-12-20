package or;

import tp.*;
import java.util.List;
import java.util.ArrayList;
import model.*;
import com.google.gson.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class KiloController {
//
//    @PostMapping("/fitotona")
//    String  create(@RequestParam String debut,@RequestParam String fin,
//            @RequestParam double qteVato,@RequestParam int explosionId) throws Exception {
//      Fitotona toto=new Fitotona();
//      toto.setDebut(debut);
//      toto.setFin(fin);
//      toto.setExplosionId(explosionId);
//      toto.setQteVato(qteVato);
//   return "$$$$";
//   
//    }

    @PutMapping("/vehicules/{id}/kilometrages/{idKilo}")
    void upKilo(@PathVariable int id, @PathVariable int idKilo, @RequestParam double debut, @RequestParam double fin,
            @RequestParam String daty) throws Exception {
        Kilometrage one = new Kilometrage();
        one.setVehiculeId(id);
        one.setId(idKilo);
        one.setFin(fin);
        one.setDebut(debut);
        one.setDaty(daty);
        one.update("id", null);
    }

    @PostMapping("/vehicules/{id}/kilometrages")
    void newKilo(@PathVariable int id, @RequestParam double debut, @RequestParam double fin,
            @RequestParam String daty) throws Exception {
        Kilometrage one = new Kilometrage();
        one.setVehiculeId(id);
        one.setFin(fin);
        one.setDebut(debut);
        one.setDaty(daty);
        one.insert(null);
    }

    @DeleteMapping("/vehicules/{id}/kilometrages/{idkilo}")
    void delKilo(@PathVariable int idkilo, @PathVariable int id) throws Exception {
        Kilometrage one = new Kilometrage();
        one.setId(idkilo);
        one.setVehiculeId(id);
       
        one.delete("id", null);
    }

}
