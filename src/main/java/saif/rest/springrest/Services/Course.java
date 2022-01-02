package saif.rest.springrest.Services;




import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Course implements CRUD{

    private final JdbcTemplate jdbcTemplate;

    public Course(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void add(Object object) {

    }

    @Override
    public void update(int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Object get(String id) throws Exception {

        String selectQuery = "select uuid,name,section,SUM(uni.stu_cou.`result`) as sum,avg(uni.stu_cou.`result`) as avg,min(uni.stu_cou.`result`) as min,max(uni.stu_cou.`result`) as max from uni.courses inner join uni.stu_cou on uni.courses.id  = uni.stu_cou.c_id where uni.courses.uuid  = ?";
        return jdbcTemplate.queryForObject(selectQuery,new CourseMapper(),id);
    }

    public List<saif.rest.springrest.DAO.Course> getCourses(String userId) throws Exception{

        String selectQuery = "select uuid,name,section,result from uni.courses inner join uni.stu_cou on uni.courses.id = uni.stu_cou.c_id where uni.stu_cou.s_id = (select uni.students.id from uni.students inner join uni.users on uni.students.u_id = uni.users.id where uni.users.uuid = ? )";
        return jdbcTemplate.query(selectQuery,new CoursesMapper(),userId);

    }

    private static final class CoursesMapper implements RowMapper<saif.rest.springrest.DAO.Course> {

        @Override
        public saif.rest.springrest.DAO.Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            saif.rest.springrest.DAO.Course course = new saif.rest.springrest.DAO.Course();
            course.setId(rs.getString("uuid"));
            course.setName(rs.getString("name"));
            course.setSection(rs.getString("section"));
            course.setResult(rs.getInt("result"));
            return course;
        }
    }

    private static final class CourseMapper implements RowMapper<saif.rest.springrest.DAO.Course> {

        @Override
        public saif.rest.springrest.DAO.Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            saif.rest.springrest.DAO.Course course = new saif.rest.springrest.DAO.Course();
            course.setId(rs.getString("uuid"));
            course.setName(rs.getString("name"));
            course.setSection(rs.getString("section"));
            course.setSum(rs.getDouble("sum"));
            course.setAvg(rs.getDouble("avg"));
            course.setMax(rs.getDouble("max"));
            course.setMin(rs.getDouble("min"));
            return course;
        }
    }

}
