package saif.rest.springrest.Response;

import java.util.ArrayList;
import java.util.List;

public class DataResponse<T> {
    private General general;
    List<T> data = new ArrayList<>();

    public DataResponse(General general, List<T> array) {
        this.general = general;
        this.data = array;
    }
}
