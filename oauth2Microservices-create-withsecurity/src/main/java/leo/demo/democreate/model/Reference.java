package leo.demo.democreate.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import leo.demo.democreate.MySQLConfig;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
@Entity
@Table(name = MySQLConfig.referenceTableName)
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String clientname;
    private String clientdescription;
    private Integer persondays;
    private Double volume;
    private Date projectstart;
    private Date projectend;

    @ManyToOne
    @JoinColumn(name = "branchID")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "lobID")
    private Lob lob;

    @ManyToOne
    @JoinColumn(name = "techID")
    private Tech tech;

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

    public Reference() {
    }

    public Reference(String name, String description) {
        this.name = name;
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

}
