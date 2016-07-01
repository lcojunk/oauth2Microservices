/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.services;

import leo.demo.democreate.rest.RestCrudBranchController;
import java.util.List;
import leo.demo.Oauth2leoCreateWithsecurityApplication;
import leo.demo.democreate.dto.EntityRequestDTO;
import leo.demo.democreate.dto.EntityResponseDTO;
import leo.demo.democreate.dto.RestResponse;
import leo.demo.democreate.model.Branch;
import leo.demo.democreate.repository.CrudBranchRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author odzhara-ongom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Oauth2leoCreateWithsecurityApplication.class)
@WebAppConfiguration
public class BranchServiceTest {

    @Autowired
    private CrudBranchRepository entityDao;

    @Autowired
    private CrudBranchMySQLService repository;

    @Autowired
    private RestCrudBranchController service;

    public BranchServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
//
//    @Test
//    public void testCreate() {
//        System.out.println("create");
//        Assert.assertTrue("The test case is a prototype.", true);
//    }
//
//    @Test
//    public void testUpdate() {
//        System.out.println("update");
//        Assert.assertTrue("The test case is a prototype.", true);
//    }
//
//    @Test
//    public void testRead() {
//        System.out.println("read");
//        Assert.assertTrue("The test case is a prototype.", true);
//    }
//

    @Test
    public void testReadAll() {
        System.out.println("readAll");
        RestResponse<List<EntityResponseDTO>, EntityRequestDTO> response1 = service.readAll();
        Assert.assertNotNull(response1);
        Assert.assertNotNull(response1.getResult());
        Assert.assertTrue(response1.getResult().size() >= 0);
        int size1 = response1.getResult().size();
        String testname = "sometestname";
        String testdescription = "sometestdescription";
        Branch testBranch1 = new Branch(testname, testdescription);
        entityDao.save(testBranch1);
        RestResponse<List<EntityResponseDTO>, EntityRequestDTO> response2 = service.readAll();
        Assert.assertNotNull(response2);
        Assert.assertNotNull(response2.getResult());
        Assert.assertTrue(response2.getResult().size() - size1 == 1);
        //repository.
        entityDao.delete(testBranch1);
        RestResponse<List<EntityResponseDTO>, EntityRequestDTO> response3 = service.readAll();
        Assert.assertNotNull(response2);
        Assert.assertNotNull(response2.getResult());
        Assert.assertTrue(response3.getResult().size() == size1);
        Assert.assertTrue("The test case is a prototype.", true);
    }

//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        Assert.assertTrue("The test case is a prototype.", true);
//    }
}
