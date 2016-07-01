package leo.demo.demosearch.model;

import leo.demo.MicroserviceConfig;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
@Document(indexName = MicroserviceConfig.INDEX_NAME, type = MicroserviceConfig.INDEX_BRANCH_NAME)
public class Branch implements EntityModel {

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;
    @Field(type = FieldType.String)
    private String name;
    @Field(type = FieldType.String)
    private String description;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
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

    public Branch(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
