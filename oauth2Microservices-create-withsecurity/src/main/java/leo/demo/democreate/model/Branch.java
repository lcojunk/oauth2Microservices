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
@Table(name = MySQLConfig.branchTableName)
public class Branch implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public Branch() {
    }

    public Branch(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
