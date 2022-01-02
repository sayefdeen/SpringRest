package saif.rest.springrest.Services;

public interface CRUD<T> {

    public void add(T object) throws Exception;

    public void update(int id) ;

    public void delete(int id) ;

    public T get(String id) throws Exception;
}
