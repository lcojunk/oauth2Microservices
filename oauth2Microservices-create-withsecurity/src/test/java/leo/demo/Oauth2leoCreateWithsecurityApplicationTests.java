package leo.demo;

import java.util.List;
import leo.demo.democreate.model.Branch;
import leo.demo.democreate.model.EntityModel;
import leo.demo.democreate.services.CrudBranchMySQLService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Oauth2leoCreateWithsecurityApplication.class)
@WebAppConfiguration
public class Oauth2leoCreateWithsecurityApplicationTests {

    @Autowired
    private CrudBranchMySQLService repository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSomething() {
        List<Branch> entityList = repository.getAll();
        int size = entityList.size();
    }

}
