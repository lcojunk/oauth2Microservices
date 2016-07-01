/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.dto;

import java.util.ArrayList;
import java.util.List;
import leo.demo.democreate.model.*;

/**
 *
 * @author odzhara-ongom
 */
public class EntityFactory {

    private static EntityFactory instance;

    public EntityResponseDTO createDTO(EntityModel entity) {
        if (entity == null) {
            return null;
        }
        EntityResponseDTO result = new EntityResponseDTO();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setDescription(entity.getDescription());
        return result;
    }

    public List<EntityResponseDTO> createDTOList(List<EntityModel> entityList) {
        if (entityList == null) {
            return null;
        }
        List<EntityResponseDTO> result = new ArrayList<>();
        for (EntityModel model : entityList) {
            result.add(createDTO(model));
        }
        return result;
    }

    private EntityFactory() {

    }

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }
        return instance;
    }

//    public EntityResponseDTO createDTO(Branch entity) {
//        if (entity == null) {
//            return null;
//        }
//        EntityResponseDTO result = new EntityResponseDTO();
//        result.setId(entity.getId());
//        result.setName(entity.getName());
//        result.setDescription(entity.getDescription());
//        return result;
//    }
}
