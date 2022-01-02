package saif.rest.springrest.DAO;

public class Students extends User {


    public Students() {
        super();
    }

    public Students(String email, String password) throws Exception {
        super(email, password);
    }

    public Students(String uuid,String email, String password) {
        super(uuid,email, password);
    }

}
