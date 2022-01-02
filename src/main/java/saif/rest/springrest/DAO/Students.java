package saif.rest.springrest.DAO;

public class Students extends User {


    public Students() {
        super();
    }

    public Students(String name, String password,boolean isNew) throws Exception {
        super(name, password,isNew);
    }

    public Students(String uuid,String name, String password) {
        super(uuid,name, password);
    }

}
