/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import leo.demo.demosearch.model.Reference;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrudReferenceElasitcImpl implements CrudReferenceInterface {

    @Autowired
    private ElasticReferenceRepository repository;
    static Logger log = Logger.getLogger(CrudReferenceElasitcImpl.class.getName());

    @Override
    public Reference create(Reference entity) {
        if (entity == null) {
            return null;
        }
        log.info("creating entity=" + entity);
        return repository.save(entity);
    }

    @Override
    public boolean delete(Reference entity) {
        if (entity == null) {
            return false;
        }
        try {
            repository.delete(entity);
        } catch (Exception e) {
//            log.error("Could not delete entity" + entity);
//            log.error(e);
            System.out.println(e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean exists(Reference entity) {
        if (entity == null || entity.getId() == null) {
            return false;
        }
        return repository.findOne(entity.getId()) != null;
    }

    @Override
    public List<Reference> getAll() {
        Iterable<Reference> itr = repository.findAll();
        List<Reference> entityList = new ArrayList<>();
        Reference entity = null;
        for (Iterator<Reference> it = itr.iterator(); it.hasNext();) {
            entity = it.next();
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    @Override
    public Reference read(String id) {
        if (id == null) {
            return null;
        }
        return repository.findOne(id);
    }

    @Override
    public Reference update(Reference entity) {
        if (entity == null) {
            return null;
        }
        return repository.save(entity);
    }

}
