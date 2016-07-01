package leo.demo.democreate.dto;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
public class EntityRequestDTO {

    private Long id;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public EntityRequestDTO() {
    }

    public EntityRequestDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
