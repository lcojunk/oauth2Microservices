package leo.demo.democreate.dto;

import leo.demo.democreate.model.Reference;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
public class CreateReferenceRequest {

    public static CreateReferenceRequest create(Reference reference) {
        if (reference == null) {
            return null;
        }
        CreateReferenceRequest result = new CreateReferenceRequest();
        result.setId(new Long(reference.getId()));
        result.setName(reference.getName());
        result.setDescription(reference.getDescription());
        return result;
    }

    private Long id;
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
