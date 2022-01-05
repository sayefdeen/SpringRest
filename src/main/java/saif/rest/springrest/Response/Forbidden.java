package saif.rest.springrest.Response;

public class Forbidden extends General{

    public Forbidden(String message) {
        super(403, message);
    }
}
