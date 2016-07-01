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
import leo.demo.democreate.model.Reference;
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
public class ReferenceServiceTest {

    @Autowired
    private CrudReferenceMySQLService repository;

    public ReferenceServiceTest() {
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

    @Test
    public void testReadAll() {
        System.out.println("readAll");
        List<Reference> references = repository.getAll();
        Reference reference = new Reference("test", "test");
        Assert.assertNotNull(references);
        int size = references.size();
        Reference savedReference = repository.create(reference);
        references = repository.getAll();
        Assert.assertEquals(1, references.size() - size);
        repository.delete(savedReference);
        references = repository.getAll();
        Assert.assertEquals(size, references.size());
    }

}
