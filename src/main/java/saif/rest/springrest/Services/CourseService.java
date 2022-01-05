package saif.rest.springrest.Services;




import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import saif.rest.springrest.DAO.Course;
import saif.rest.springrest.Log.Logger;

import java.sql.ResultSet;
import java.util.List;

@Repository
@Profile("database")
public class CourseService implements CRUD{

    private final JdbcTemplate jdbcTemplate;

    public CourseService(JdbcTemplate jdbcTemplate) {
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
    public Object get(String id) {

        String selectQuery = "select uuid,name,section,SUM(uni.stu_cou.`result`) as sum,avg(uni.stu_cou.`result`) as avg,min(uni.stu_cou.`result`) as min,max(uni.stu_cou.`result`) as max from uni.courses inner join uni.stu_cou on uni.courses.id  = uni.stu_cou.c_id where uni.courses.uuid  = ?";
        return jdbcTemplate.queryForObject(selectQuery,new CourseMapper(),id);
    }

    public List<Course> getCourses(String userId) {

        String selectQuery = "select uuid,name,section,result from uni.courses inner join uni.stu_cou on uni.courses.id = uni.stu_cou.c_id where uni.stu_cou.s_id = (select uni.students.id from uni.students inner join uni.users on uni.students.u_id = uni.users.id where uni.users.uuid = ? )";
        return jdbcTemplate.query(selectQuery,new CoursesMapper(),userId);

    }


    private static final class CoursesMapper implements RowMapper<Course> {

        @Override
        public Course mapRow(ResultSet rs, int rowNum) {
            try{
                Course course = new Course();
                course.setId(rs.getString("uuid"));
                course.setName(rs.getString("name"));
                course.setSection(rs.getString("section"));
                course.setResult(rs.getInt("result"));
                return course;
            }catch (Exception e){
                Logger.getLogger().addLog("Something went bad!! " + e.getMessage());
            }
           return null;
        }
    }

    private static final class CourseMapper implements RowMapper<Course> {

        @Override
        public Course mapRow(ResultSet rs, int rowNum) {
            try{
                Course course = new Course();
                course.setId(rs.getString("uuid"));
                course.setName(rs.getString("name"));
                course.setSection(rs.getString("section"));
                course.setSum(rs.getDouble("sum"));
                course.setAvg(rs.getDouble("avg"));
                course.setMax(rs.getDouble("max"));
                course.setMin(rs.getDouble("min"));
                return course;
            }catch (Exception e){
                Logger.getLogger().addLog("Something went bad!! " + e.getMessage());
            }
            return null;
        }
    }

}
