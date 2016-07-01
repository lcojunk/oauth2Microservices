package leo.demo.demosearch.model;

import java.util.Date;
import leo.demo.MicroserviceConfig;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
@Document(indexName = MicroserviceConfig.INDEX_NAME, type = MicroserviceConfig.INDEX_REFERENCE_NAME)
public class Reference {

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;

    @Field(type = FieldType.String)
    private String name;
    @Field(type = FieldType.String)
    private String description;
    @Field(type = FieldType.String)
    private String clientname;
    @Field(type = FieldType.String)
    private String clientdescription;
    @Field(type = FieldType.Integer)
    private Integer persondays;
    @Field(type = FieldType.Double)
    private Double volume;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date projectstart;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private Date projectend;
    @Field(type = FieldType.Nested)
    private Branch branch;
    @Field(type = FieldType.Nested)
    private Lob lob;
    @Field(type = FieldType.Nested)
    private Tech tech;

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

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientdescription() {
        return clientdescription;
    }

    public void setClientdescription(String clientdescription) {
        this.clientdescription = clientdescription;
    }

    public Integer getPersondays() {
        return persondays;
    }

    public void setPersondays(Integer persondays) {
        this.persondays = persondays;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Date getProjectstart() {
        return projectstart;
    }

    public void setProjectstart(Date projectstart) {
        this.projectstart = projectstart;
    }

    public Date getProjectend() {
        return projectend;
    }

    public void setProjectend(Date projectend) {
        this.projectend = projectend;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Lob getLob() {
        return lob;
    }

    public void setLob(Lob lob) {
        this.lob = lob;
    }

    public Tech getTech() {
        return tech;
    }

    public void setTech(Tech tech) {
        this.tech = tech;
    }

    public Reference() {
    }

    public Reference(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Reference(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
