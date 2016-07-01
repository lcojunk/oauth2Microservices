/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.model;

/**
 *
 * @author odzhara-ongom
 */
public interface EntityModel {

    String getDescription();

    Long getId();

    String getName();

    void setDescription(String description);

    void setId(Long id);

    void setName(String name);

}
