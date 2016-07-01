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
import leo.demo.democreate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class CrudLobMySQLService implements DemoCrudRepository<Lob> {

    @Autowired
    private CrudLobRepository entityDao;

    @Override
    public Lob create(Lob entity) {
        return entityDao.save(entity);
    }

    @Override
    public boolean delete(Lob entity) {
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
    public boolean exists(Lob entity) {
        if (entity == null || entity.getId() < 0) {
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
    public Lob read(long id) {
        return entityDao.findOne(id);
    }

    @Override
    public Lob update(Lob entity) {
        return entityDao.save(entity);
    }

}
