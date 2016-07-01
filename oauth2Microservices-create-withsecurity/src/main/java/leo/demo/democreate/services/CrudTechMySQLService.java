/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import leo.demo.democreate.model.*;
import leo.demo.democreate.repository.CrudTechRepository;
import leo.demo.democreate.repository.DemoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class CrudTechMySQLService implements DemoCrudRepository<Tech> {

    @Autowired
    private CrudTechRepository entityDao;

    @Override
    public Tech create(Tech entity) {
        return entityDao.save(entity);
    }

    @Override
    public boolean delete(Tech entity) {
        if (entity == null || entity.getId() < 0) {
            return false;
        }
        if (entityDao.findOne(entity.getId()) == null) {
            return false;
        }
        entityDao.delete(entity);
        return true;
    }

    @Override
    public boolean exists(Tech entity) {
        if (entity == null || entity.getId() < 0) {
            return false;
        }
        return entityDao.exists(entity.getId());
    }

    @Override
    public List<Tech> getAll() {
        Iterable<Tech> itr = entityDao.findAll();
        List<Tech> entityList = new ArrayList<>();
        Tech entity = null;
        for (Iterator<Tech> it = itr.iterator(); it.hasNext();) {
            entity = it.next();
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    @Override
    public Tech read(long id) {
        return entityDao.findOne(id);
    }

    @Override
    public Tech update(Tech entity) {
        return entityDao.save(entity);
    }

}
