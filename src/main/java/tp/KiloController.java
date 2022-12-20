package tp;

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
import utils.Fail;
import utils.Message;
import utils.Success;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
@CrossOrigin
public class KiloController {

    @GetMapping("/vehicules/{id}/kilometrages")
    String all(@PathVariable int id) throws Exception {
        Kilometrage am = new Kilometrage();
        am.setVehiculeId(id);
        Gson gson = new Gson();
        return gson.toJson(am.select(null));
    }

    @GetMapping("/vehicules/{id}/kilometrages/{idKilo}")
    String getOne(@PathVariable int idKilo) throws Exception {
        Kilometrage am = new Kilometrage();
        am = ((Kilometrage) (am.select(null)).get(0));
        Gson gson = new Gson();
        String texte = gson.toJson(am);
        return texte;
    }

    @PutMapping("/vehicules/{id}/kilometrages/{idKilo}")
    String upKilo(@PathVariable int id, @PathVariable int idKilo, @RequestParam double debut, @RequestParam double fin,
            @RequestParam String daty) throws Exception {
        Gson gson = new Gson();
        String texte = "";
        try {
            Kilometrage one = new Kilometrage();
            one.setVehiculeId(id);
            one.setId(idKilo);
            one.setFin(fin);
            one.setDebut(debut);
            one.setDaty(daty);
            one.update("id", null);
            texte = gson.toJson(new Message(new Success(idKilo, "Success")));
        } catch (Exception ex) {
            texte = gson.toJson(new Message(new Fail("500", "Error")));
        }
        return texte;
//        one.
    }

    @PostMapping("/vehicules/{id}/kilometrages")
    String newKilo(@PathVariable int id, @RequestParam double debut, @RequestParam double fin,
            @RequestParam String daty) throws Exception {
        Gson gson = new Gson();
        String texte = "";
        try {
            Kilometrage one = new Kilometrage();
            one.setVehiculeId(id);
            one.setFin(fin);
            one.setDebut(debut);
            one.setDaty(daty);
            one.insert(null);
            texte = gson.toJson(new Message(new Success(((Kilometrage) one.getLastObject()).getId(), "Success")));
        } catch (Exception ex) {
            texte = gson.toJson(new Message(new Fail("500", "Error")));
        }
        return texte;
    }

    @DeleteMapping("/vehicules/{id}/kilometrages/{idkilo}")
    void delKilo(@PathVariable int idkilo, @PathVariable int id) throws Exception {
        Kilometrage one = new Kilometrage();
        one.setId(idkilo);
        one.setVehiculeId(id);
        one.delete("id", null);
    }

}
