package tp;

import antlr.Token;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import utils.Data;
import utils.Fail;
import utils.Message;
import utils.Success;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
@CrossOrigin
public class UsersController {
    
    @GetMapping("/login_")
    String test() {
        Gson gson = new Gson();
        String texte = gson.toJson(new Message(new Fail("404", "Not Found")));
        return texte;
    }
    
    boolean haveToken(int id) throws Exception {
        Tokens montoken = new Tokens();
        montoken.setUtilisateurId(id);
        ArrayList tokenS = montoken.select(null);
        if (tokenS.size() > 0) {
            return true;
        }
        return false;
    }
    
    boolean lastTokenDepasser(int id) throws Exception {
        Tokens montoken = new Tokens();
        montoken.setUtilisateurId(id);
        ArrayList<Tokens> tokenS = montoken.selectBySQL("select *from Tokens where utilisateurid=" + id + " order by id desc", null);
        return Tokens.isDepasse((tokenS.get(0).getDateExp()));
    }
//
//    String checkToken(int id) throws Exception {
//        Gson gson = new Gson();
//        String json = "";
//        if (!haveToken(id)) {
//            String temp = Tokens.getCurrentTimestamp().split("\\.")[0];
//            String daty = temp.split(" ")[0];
//            String hh = Tokens.getCurrentTimestamp().split("\\.")[0];
//            hh = Tokens.addMinute(hh, 5);
//            String finaly = daty + " " + hh.split(" ")[1];
//            Tokens vao = new Tokens();
//            String ff = Tokens.sha1(" " + Long.toString(System.currentTimeMillis()));
//            vao.setToken(ff);
//            vao.setDateExp(finaly);
//            vao.setUtilisateurId(id);
//            vao.insert(null);
//            json = gson.toJson(new Message(new Success(ff, id)));
////            return "truesss";
//            return json;
//        }
//        if (haveToken(id)) {
//            Tokens montoken = new Tokens();
//            montoken.setUtilisateurId(id);
//            ArrayList<Tokens> tokenS = montoken.select(null);
//
//            String temp = Tokens.getCurrentTimestamp().split("\\.")[0];
//            String daty = temp.split(" ")[0];
//
//            String hh = Tokens.getCurrentTimestamp().split("\\.")[0];
//            hh = Tokens.addMinute(hh, 5);
//            if (lastTokenDepasser(id)) {
//                String finaly = daty + " " + hh.split(" ")[1];
//                Tokens vao = new Tokens();
//                String ff = Tokens.sha1(" " + Long.toString(System.currentTimeMillis()));
//                vao.setToken(ff);
//                vao.setDateExp(finaly);
//                System.out.println("Efa manana");
//                vao.setUtilisateurId(id);
//                vao.insert(null);
//                   ArrayList list = new ArrayList();
//            list.add((new Success( ff,id)));
//            _val_.put("datas",list);
//                return json;
////            json = "";
////            json += "'data':{'token':'" + ff + "'}" + "','id':'" + id + "'}";
//
//            } else {
//                json = gson.toJson(new Message(new Success(tokenS.get(0).getToken(), id)));
//                return json;
//            }
//        }
//
//        return json;
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    @CrossOrigin
    HashMap<String, Object> login(@RequestHeader String logins, @RequestHeader String pwds) throws Exception {
        HashMap _val_ = new HashMap<String, Object>();
        Utilisateur zateur = new Utilisateur();
        zateur.setLogin(logins);
        zateur.setPwd(pwds);
        Gson gson = new Gson();
        int id = zateur.getLoginId();
        System.err.println(id);
        String json = "";
        if (id == -1) {
            _val_.put("datas", (new Fail("Login Error", "500")));
            return _val_;
        }
        zateur.setId(id);
        try {
            String oi = new TokenUtil().generateToken(zateur);
            _val_.put("datas", new Success(id, oi));
        } catch (Exception xc) {
            throw xc;
        }
        return _val_;
    }
    @RequestMapping(value = "/checkTokens", method = RequestMethod.GET, produces = "application/json")
    HashMap<String, Object> logins(@RequestHeader String login) throws Exception {
        HashMap _val_ = new HashMap<String, Object>();
        try {
            if (new TokenUtil().isTokenExpired(login)) {
                _val_.put("datas",(new Fail("Exp", "404")));
                return _val_;
            }
        } catch (Exception xc) {
            throw xc;
        }
        _val_.put("datas", new Success(200,"Ok"));
      
        return _val_;
    }

    /*
    public void doFilter(ServletRequest r equest, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        final String requestTokenHeader = req.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = new TokenUtil().getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            System.out.println("JWT Token does not begin with Bearer String");
        }

        //Once we get the token validate it.
        if (username != null) {
            String userDetails = "Dina";
//			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set authentication
            if (new TokenUtil().validateToken(jwtToken, userDetails)) {

//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				usernamePasswordAuthenticationToken
//						.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                // After setting the Authentication in the context, we specify
//				 that the current user is authenticated. So it passes the Spring Security Configurations successfully.
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(req, res);
    }*/
//    @GetMapping("/home")
//    String baerer(@RequestParam int id) throws Exception {
//
//        Tokens montoken = new Tokens();
//
//        Gson gson = new Gson();
//        montoken.setUtilisateurId(id);
//        ArrayList tokenS = montoken.select(null);
//        Tokens currents = (Tokens) tokenS.get(0);
//        String hash1 = Tokens.sha1(currents.getToken() + "GET /home");
//        String json = "";
//        try {
//            json = "";
//            json = gson.toJson(new Message(new Success(hash1, id)));
//
//        } catch (Exception ex) {
//            json = gson.toJson(new Message(new Fail("not Found", "404")));
//        }
//        return json;
//
//    }
    @GetMapping("/deconnexion")
    String decon(@RequestParam int id) throws Exception {
        Tokens montoken = new Tokens();
        Gson gson = new Gson();
        
        montoken.setUtilisateurId(id);
        montoken.setDateExp(Tokens.getCurrentTimestamp().split("\\.")[0]);
        Tokens montokens = new Tokens();
        montokens.setUtilisateurId(id);
        montokens.setUtilisateurId(id);
        montokens.update("utilisateurId", null);
        String json = "";
        try {
            montoken.update("utilisateurId", null);
            json = gson.toJson(new Message(new Success(0, "Success")));
        } catch (Exception ex) {
            json = gson.toJson(new Message(new Fail("not Found", "404")));
            
        }
        return json;
        
    }
}
