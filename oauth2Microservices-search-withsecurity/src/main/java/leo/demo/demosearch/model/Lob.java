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
@Document(indexName = MicroserviceConfig.INDEX_NAME, type = MicroserviceConfig.INDEX_LOB_NAME)
public class Lob implements EntityModel {

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;
    @Field(type = FieldType.String)
    private String name;
    @Field(type = FieldType.String)
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Lob(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
