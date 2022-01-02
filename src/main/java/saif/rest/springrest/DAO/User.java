package saif.rest.springrest.DAO;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.UUID;

public abstract class User {

    private  String id;
    private  String email;
    private  String password;

    public User() {}

    public User(String email, String password){
        this.email = email;
        this.id = UUID.randomUUID().toString();
        this.password = bcryptPass(password);
    }

    public User(String id, String name, String password) {
        this.id = id;
        this.email = name;
        this.password = password;
    }

    public String getId() {
        return id.toString();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String bcryptPass(String password) {
        return BCrypt.withDefaults().hashToString(5,password.toCharArray());
    }

}
