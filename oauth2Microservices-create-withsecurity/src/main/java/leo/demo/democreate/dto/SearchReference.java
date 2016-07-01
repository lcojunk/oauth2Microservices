/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.dto;

import leo.demo.democreate.model.Reference;

/**
 *
 * @author odzhara-ongom
 */
public class SearchReference {

    private String id;
    private String name;
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

    public SearchReference() {
    }

    public static SearchReference buildSearchReference(Reference reference) {
        if (reference == null) {
            return null;
        }
        SearchReference result = new SearchReference();
        result.id = reference.getId() + "";
        result.name = reference.getName();
        result.description = reference.getDescription();
        return result;
    }

}
