package saif.rest.springrest.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import saif.rest.springrest.DAO.Students;
import saif.rest.springrest.Services.CRUD;
import saif.rest.springrest.Services.StudentsService;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final StudentsService service;

    public RegisterController( StudentsService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newUser(@RequestBody Students student) throws Exception {
        Students newStudent = new Students(student.getEmail(),student.getPassword());

        service.add(newStudent);
    }

}
