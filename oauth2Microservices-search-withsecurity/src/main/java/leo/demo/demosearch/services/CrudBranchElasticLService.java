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
public class CrudBranchElasticLService implements DemoCrudRepository<Branch> {

    @Autowired
    private CrudBranchRepository entityDao;

    @Override
    public Branch create(Branch entity) {
        return entityDao.save(entity);
    }

    @Override
    public boolean delete(Branch entity) {
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
    public boolean exists(Branch entity) {
        if (entity == null) {
            return false;
        }
        return entityDao.exists(entity.getId());
    }

    @Override
    public List<Branch> getAll() {
        Iterable<Branch> itr = entityDao.findAll();
        List<Branch> entityList = new ArrayList<>();
        Branch entity = null;
        for (Iterator<Branch> it = itr.iterator(); it.hasNext();) {
            entity = it.next();
            if (entity != null) {
                entityList.add(entity);
            }
        }
        return entityList;
    }

    @Override
    public Branch read(String id) {
        return entityDao.findOne(id);
    }

    @Override
    public Branch update(Branch entity) {
        return entityDao.save(entity);
    }

}
