/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.demosearch.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import leo.demo.demosearch.model.*;
import leo.demo.demosearch.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class CrudLobElasticLService implements DemoCrudRepository<Lob> {

    @Autowired
    private CrudLobRepository entityDao;

    @Override
    public Lob create(Lob entity) {
        return entityDao.save(entity);
    }

    @Override
    public boolean delete(Lob entity) {
        if (entity == null) {
            return false;
        }
        if (entityDao.findOne(entity.getId()) == null) {
            return false;
        }
        entityDao.delete(entity);
        return true;
    }

    @Override
    public boolean exists(Lob entity) {
        if (entity == null) {
            return false;
        }
        return entityDao.exists(entity.getId());
    }

    @Override
    public List<Lob> getAll() {
        Iterable<Lob> itr = entityDao.findAll();
        List<Lob> entityList = new ArrayList<>();
        Lob entity = null;
        for (Iterator<Lob> it = itr.iterator(); it.hasNext();) {
            entity = it.next();
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    @Override
    public Lob read(String id) {
        return entityDao.findOne(id);
    }

    @Override
    public Lob update(Lob entity) {
        return entityDao.save(entity);
    }

}
