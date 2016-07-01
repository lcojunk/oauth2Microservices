package leo.demo.democreate.repository;

import leo.demo.democreate.dummydatabase.ReferenceDatabase;
import leo.demo.democreate.model.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
public class ReferenceRepository implements DemoCrudRepository<Reference> {

    private List<Reference> references = ReferenceDatabase.getAllReference();

    @Override
    public boolean exists(Reference reference) {
        if (reference.getId() < 0) {
            return false;
        }
        for (Reference r : references) {
            if (r.getId() >= 0 && reference.getId() == r.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Reference read(long id) {
        if (id < 0) {
            return null;
        }
        for (Reference r : references) {
            if (r.getId() >= 0 && r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    @Override
    public Reference create(Reference reference) {
        if (reference == null) {
            return null;
        }
        if (reference.getId() < 0) {
            reference.setId(Long.parseLong(ReferenceDatabase.randomNumericString(15)));
            references.add(reference);
            return reference;
        }
        Reference result = read(reference.getId());
        if (result == null) {
            references.add(reference);
            return reference;
        }
        return null;
    }

    @Override
    public Reference update(Reference reference) {
        if (reference == null || reference.getId() < 0) {
            return null;
        }
        Reference result = read(reference.getId());
        if (result == null) {
            return null;
        }
        result.setName(reference.getName());
        result.setDescription(reference.getDescription());
        return result;
    }

    @Override
    public boolean delete(Reference reference) {
        if (reference == null || reference.getId() < 0) {
            return false;
        }
        Reference r = null;
        for (int i = 0; i < references.size(); i++) {
            r = references.get(i);
            if (r != null && r.getId() == reference.getId()) {
                references.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Reference> getAll() {
        return references;
    }

}
