/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.rest;

import java.util.List;
import leo.demo.demosearch.model.Reference;
import leo.demo.demosearch.services.ReferenceIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
@RestController
@RequestMapping(value = {"/api/admin", "/${spring.application.name}" + "/api/admin"})
public class RestAdminController {

    @Autowired
    private ReferenceIndexService referenceIndexService;

    @RequestMapping(value = "/recreate_index", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Reference> recreateIndex() {
        List<Reference> references = referenceIndexService.recreateIndexWithData();
        return references;
    }

}
