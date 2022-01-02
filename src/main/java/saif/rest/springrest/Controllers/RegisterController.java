package saif.rest.springrest.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import saif.rest.springrest.DAO.Students;
import saif.rest.springrest.Services.CRUD;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final CRUD crud;

    public RegisterController(CRUD crud) {
        this.crud = crud;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newUser(@RequestBody Students student) throws Exception {
        Students newStudent = new Students(student.getEmail(),student.getPassword());

        crud.add(newStudent);
    }

}
