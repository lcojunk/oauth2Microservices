/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.rest;

import com.google.gson.Gson;
import leo.demo.democreate.services.*;
import java.util.ArrayList;
import java.util.List;
import leo.demo.MicroserviceConfig;
import leo.demo.democreate.dto.*;
import leo.demo.democreate.model.*;
import leo.demo.democreate.repository.*;
import leo.demo.democreate.rest.RestCrudInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
//@Service
@RestController
@RequestMapping(value = {"/api/lobs", "/${spring.application.name}" + "/api/lobs", "oauth2_create" + "/api/lobs"})
public class RestCrudLobController implements RestCrudInterface<EntityResponseDTO, Long, EntityRequestDTO> {

    @Autowired
    private CrudLobMySQLService repository;
    private EntityFactory entityFactory = EntityFactory.getInstance();
    private Lob entity;
    static Logger log = Logger.getLogger(RestCrudLobController.class.getName());
    private Gson gson = MicroserviceConfig.gsonPretty;

    private void createEntity(Long id) {
        if (id == null) {
            this.entity = null;
        }
        this.entity = new Lob();
        this.entity.setId(id);
    }

    private void createEntity(EntityRequestDTO entity) {
        if (entity == null) {
            this.entity = null;
            return;
        }
        this.entity = new Lob();
        this.entity.setId(entity.getId());
        this.entity.setName(entity.getName());
        this.entity.setDescription(entity.getDescription());
    }

    private List<EntityResponseDTO> createEntityList(List<Lob> entityList) {
        if (entityList == null) {
            return null;
        }
        List<EntityResponseDTO> result = new ArrayList<>();
        for (EntityModel entity : entityList) {
            result.add(entityFactory.createDTO(entity));
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Override
    public RestResponse<List<EntityResponseDTO>, EntityRequestDTO> create(@RequestBody EntityRequestDTO request) {
        log.info("creating entity:" + gson.toJson(request));
        if (request != null) {
            request.setId(null);
        }
        return update(request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Override
    public RestResponse<EntityResponseDTO, Long> read(@PathVariable Long id) {
        RestResponse<EntityResponseDTO, Long> response = new RestResponse<>(id);
        try {
            response.setResult(entityFactory.createDTO(repository.read(id)));
            response.setSuccess(response.getResult() != null);
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @Override
    public RestResponse<List<EntityResponseDTO>, EntityRequestDTO> readAll() {
        log.info("reading all entities...");
        RestResponse<List<EntityResponseDTO>, EntityRequestDTO> response = new RestResponse<>();
        try {
            response.setResult(createEntityList(repository.getAll()));
            response.setSuccess(response.getResult() != null);
            log.info("found:" + gson.toJson(response.getResult()));
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @Override
    public RestResponse<List<EntityResponseDTO>, EntityRequestDTO> update(@RequestBody EntityRequestDTO request) {
        log.info("updating entity:" + gson.toJson(request));
        RestResponse<List<EntityResponseDTO>, EntityRequestDTO> response = new RestResponse<>(request);
        try {
            createEntity(request);
            if (entity != null) {
                response.setSuccess(repository.create(entity) != null);
            }
            response.setResult(createEntityList(repository.getAll()));
            response.setError("no Error");
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Override
    public RestResponse<List<EntityResponseDTO>, Long> delete(@PathVariable Long id) {
        RestResponse<List<EntityResponseDTO>, Long> response = new RestResponse<>(id);
        try {
            createEntity(id);
            if (entity != null) {
                response.setSuccess(repository.delete(entity));
            }
            response.setResult(createEntityList(repository.getAll()));
            return response;
        } catch (Exception e) {
            response.setError(e.toString());
            return response;
        }
    }

}