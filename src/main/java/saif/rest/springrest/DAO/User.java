package saif.rest.springrest.DAO;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.io.Serializable;
import java.util.UUID;

public abstract class User implements Serializable {

    private  String id;
    private  String name;
    private  String password;

    public User() {}

    public User(String name, String password,boolean isNew) throws Exception {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        if(isNew){
            this.password = bcryptPass(password);
        }else{
            this.password = password;
        }
    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String bcryptPass(String password) {
        return BCrypt.withDefaults().hashToString(5,password.toCharArray());
    }

}
