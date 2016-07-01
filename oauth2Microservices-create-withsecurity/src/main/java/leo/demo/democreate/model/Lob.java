package leo.demo.democreate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import leo.demo.democreate.MySQLConfig;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
@Entity
@Table(name = MySQLConfig.lobTableName)
public class Lob implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Lob() {
    }

    public Lob(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
