package saif.rest.springrest.Controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import saif.rest.springrest.DAO.Course;
import saif.rest.springrest.Services.CourseService;
import saif.rest.springrest.Services.StudentsService;

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
    public List<Course> getCourses(HttpServletResponse response, HttpServletRequest request) {
        try {
            String token = request.getHeader("Token");
            if(token == null){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return new ArrayList<>();
            }
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return service.getCourses(jwt.getClaim("id").asString());
        } catch (JWTVerificationException exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ArrayList<>();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ArrayList<>();
        }
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable String id,HttpServletResponse response,HttpServletRequest request) {
        try {
            String token = request.getHeader("Token");
            if(token == null){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            verifier.verify(token);
            return (Course) service.get(id);
        } catch (JWTVerificationException exception){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }

    }

}
