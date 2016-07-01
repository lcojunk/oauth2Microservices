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
import leo.demo.democreate.model.Reference;
import leo.demo.democreate.repository.CrudReferenceRepository;
import leo.demo.democreate.repository.DemoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class CrudReferenceMySQLService implements DemoCrudRepository<Reference> {

    @Autowired
    private CrudReferenceRepository referenceDao;

    @Override
    public Reference create(Reference entity) {
        return referenceDao.save(entity);
    }

    @Override
    public boolean delete(Reference entity) {
        if (entity == null || entity.getId() < 0) {
            return false;
        }
        if (referenceDao.findOne(entity.getId()) == null) {
            return false;
        }
        referenceDao.delete(entity);
        return true;
    }

    @Override
    public boolean exists(Reference entity) {
        if (entity == null || entity.getId() < 0) {
            return false;
        }
        return referenceDao.exists(entity.getId());
    }

    @Override
    public List<Reference> getAll() {
        Iterable<Reference> itr = referenceDao.findAll();
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
    public Reference read(long id) {
        return referenceDao.findOne(id);
    }

    @Override
    public Reference update(Reference entity) {
        return referenceDao.save(entity);
    }

}
