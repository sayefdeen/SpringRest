package saif.rest.springrest.Services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;


import java.sql.*;


public class Students implements CRUD{

    private final JdbcTemplate jdbcTemplate;

    public Students(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(Object object) {


        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String insertQuery = "INSERT INTO uni.users (uuid,email,password) VALUES (?,?,?)";
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    insertQuery,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, (( saif.rest.springrest.DAO.Students) object).getId());
            statement.setString(2, ((saif.rest.springrest.DAO.Students) object).getName());
            statement.setString(3, (( saif.rest.springrest.DAO.Students) object).getPassword());

            return statement;

        }, keyHolder);
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

    public  saif.rest.springrest.DAO.Students getUser(String userName) {
        String selectQuery = "SELECT uuid,email,password FROM uni.users WHERE email = ? ";
        return jdbcTemplate.queryForObject(selectQuery,new StudentMapper(),userName);
    }

    public boolean checkPass(String reqPass, String dbPass){
        return BCrypt.verifyer().verify(reqPass.toCharArray(), dbPass).verified;
    }

    private static final class StudentMapper implements RowMapper<saif.rest.springrest.DAO.Students> {

        @Override
        public saif.rest.springrest.DAO.Students mapRow(ResultSet rs, int rowNum) throws SQLException {
            saif.rest.springrest.DAO.Students student = new  saif.rest.springrest.DAO.Students();
            student.setId(rs.getString("uuid"));
            student.setName(rs.getString("email"));
            student.setPassword(rs.getString("password"));
            return student;
        }
    }
}
