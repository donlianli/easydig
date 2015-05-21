package dig.unitils;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;

import com.donlian.test.unitils.User;
import com.donlian.test.unitils.UserDAO;
import com.donlian.test.unitils.UserDAOImpl;

import static org.unitils.reflectionassert.ReflectionAssert.*;

@DataSet
public class UserDAOTest extends UnitilsJUnit4 {
	UserDAO userDao;
	@TestDataSource
    private DataSource dataSource;
        
    @Before
    public void setUp() {
        userDao = new UserDAOImpl();
        userDao.setDataSource(dataSource);
    }
    
    @Test
    public void testFindByName() {
        User result = userDao.findByName("doe", "john");
        assertPropertyLenientEquals("userName", "jdoe", result);
    }

//    @Test
//    @DataSet("UserDAOTest.testFindByMinimalAge.xml")
//    public void testFindByMinimalAge() {
//        List<User> result = userDao.findByMinimalAge(18);        
//        assertPropertyLenientEquals("firstName", Arrays.asList("jack"), result);
//    }
}