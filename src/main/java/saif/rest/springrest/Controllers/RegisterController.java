package saif.rest.springrest.Controllers;


import org.springframework.web.bind.annotation.*;
import saif.rest.springrest.DAO.Students;
import saif.rest.springrest.Log.Logger;
import saif.rest.springrest.Response.Conflict;
import saif.rest.springrest.Response.Created;
import saif.rest.springrest.Response.General;
import saif.rest.springrest.Response.ServerError;
import saif.rest.springrest.Services.StudentsService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final StudentsService service;

    public RegisterController( StudentsService service) {
        this.service = service;
    }

    @PostMapping
    public General newUser(@RequestBody Students student, HttpServletResponse response) {
        try{
            Students newStudent = new Students(student.getEmail(),student.getPassword());
            if(service.checkUser(newStudent.getEmail()) == 0){
                service.add(newStudent);
                response.setStatus(HttpServletResponse.SC_CREATED);
                Logger.getLogger().addLog("New User is created" + " " + newStudent.getId());
                return new Created("Please Login with your account");
            }else{
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                Logger.getLogger().addLog("Email Already in use" + " " + student.getEmail());
                return new Conflict("Email Already in use");
            }
        }catch (Exception e){
            Logger.getLogger().addLog("Something went Bad!!" + " " + student.getId());
            return new ServerError("Something went bad!! with the server");
        }
    }

}
