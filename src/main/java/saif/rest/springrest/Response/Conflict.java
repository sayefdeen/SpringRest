package saif.rest.springrest.Response;

public class Conflict extends General{
    public Conflict(String message) {
        super(409, message);
    }
}
