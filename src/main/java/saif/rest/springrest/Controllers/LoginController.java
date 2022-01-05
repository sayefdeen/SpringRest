package saif.rest.springrest.Controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.web.bind.annotation.*;
import saif.rest.springrest.DAO.Students;
import saif.rest.springrest.Log.Logger;
import saif.rest.springrest.Services.StudentsService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/login")
public class LoginController {


    private final StudentsService service;

    public LoginController(StudentsService service) {
        this.service = service;
    }

    @PostMapping
    public String newUser(@RequestBody Students student, HttpServletResponse response) {
        Students newStudent = service.getUser(student.getEmail());

        if(newStudent != null){
            if(service.checkPass(student.getPassword(),newStudent.getPassword())){
                try {
                    Algorithm algorithm = Algorithm.HMAC256("secret");
                    String token = JWT.create()
                            .withIssuer("auth0")
                            .withClaim("id",newStudent.getId())
                            .withClaim("name",newStudent.getEmail().split("@")[0])
                            .sign(algorithm);
                    response.setStatus(HttpServletResponse.SC_OK);
                    Logger.getLogger().addLog("User has logged" + " " + newStudent.getId());
                    return "toke: " + token;
                } catch (JWTCreationException exception){
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    Logger.getLogger().addLog("Something wend Bad!!" + " " + exception.getMessage());
                    return "Something wend Bad!!";
                }
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Logger.getLogger().addLog("Invalid Credentials" + " " + newStudent.getId() );
                return "Invalid Credentials";
            }
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Logger.getLogger().addLog("Invalid Credentials");
            return "Invalid Credentials";
        }
    }

}
