package saif.rest.springrest.Response;

public class ServerError extends General{
    public ServerError(String message) {
        super(500, message);
    }
}
