package saif.rest.springrest.Services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import saif.rest.springrest.DAO.Students;
import saif.rest.springrest.Log.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
@Profile("database")
public class StudentsService implements CRUD{

    private final JdbcTemplate jdbcTemplate;

    public StudentsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(Object object) {

        try{
                GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
                String insertQuery = "INSERT INTO uni.users (uuid,email,password) VALUES (?,?,?)";
                jdbcTemplate.update((Connection conn) -> {

                    PreparedStatement statement = conn.prepareStatement(
                            insertQuery,
                            Statement.RETURN_GENERATED_KEYS);

                    statement.setString(1, ((Students) object).getId());
                    statement.setString(2, ((Students) object).getEmail());
                    statement.setString(3, ((Students) object).getPassword());

                    return statement;

                }, keyHolder);
            } catch (Exception e){
            Logger.getLogger().addLog("Something went bad!! " + e.getMessage());
        }
    }

    @Override
    public void update(int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Object get(String id) {
        return new Object();
    }

    public Students getUser(String userName) {
        String selectQuery = "SELECT uuid,email,password FROM uni.users WHERE email = ? ";
        return jdbcTemplate.queryForObject(selectQuery,new StudentMapper(),userName);
    }

    public boolean checkPass(String reqPass, String dbPass){
        return BCrypt.verifyer().verify(reqPass.toCharArray(), dbPass).verified;
    }

    public int checkUser(String email) {
        String selectQuery = "SELECT EXISTS(SELECT email FROM uni.users WHERE email = ?) as truth;";
        return jdbcTemplate.queryForObject(selectQuery, new Object[]{email},Integer.class);
    }

    private static final class StudentMapper implements RowMapper<Students> {

        @Override
        public Students mapRow(ResultSet rs, int rowNum) {
            try{
                Students student = new  Students();
                student.setId(rs.getString("uuid"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("password"));
                return student;
            }catch (Exception e){
                Logger.getLogger().addLog("Something went bad!! " + e.getMessage());
            }
           return null;
        }
    }

}
