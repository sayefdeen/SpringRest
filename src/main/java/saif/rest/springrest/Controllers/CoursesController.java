package saif.rest.springrest.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.*;
import saif.rest.springrest.DAO.Course;
import saif.rest.springrest.Log.Logger;
import saif.rest.springrest.Response.DataResponse;
import saif.rest.springrest.Response.Forbidden;
import saif.rest.springrest.Services.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CourseService service;

    public CoursesController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public DataResponse<Course> getCourses(HttpServletResponse response, HttpServletRequest request) {
        try {
            String token = request.getHeader("Token");
            if(token == null){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Logger.getLogger().addLog("Trying to access without token");
                return new DataResponse<>(new Forbidden("Trying to access without token"), new ArrayList<>());
            }
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Logger.getLogger().addLog("User has requested courses " + jwt.getClaim("id").asString() );
            return new DataResponse<>(new Forbidden("User has requested courses"),service.getCourses(jwt.getClaim("id").asString()));
        } catch (JWTVerificationException exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Logger.getLogger().addLog("Trying to access with wrong token");
            return new DataResponse<>(new Forbidden("Trying to access with wrong token"),new ArrayList<>());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Logger.getLogger().addLog("Something went Bad!! " + e.getMessage());
            return new DataResponse<>(new Forbidden("Something went Bad!!"),new ArrayList<>());
        }
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable String id,HttpServletResponse response,HttpServletRequest request) {
        try {
            String token = request.getHeader("Token");
            if(token == null){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Logger.getLogger().addLog("Trying to access without token");
                return null;
            }
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            verifier.verify(token);
            Logger.getLogger().addLog("This Course is being called " + id);
            return (Course) service.get(id);
        } catch (JWTVerificationException exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Logger.getLogger().addLog("Trying to access with wrong token");
            return null;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Logger.getLogger().addLog("Trying to access without token");
            return null;
        }

    }

}
