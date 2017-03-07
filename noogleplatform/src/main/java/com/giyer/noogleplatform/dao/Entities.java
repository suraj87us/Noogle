package com.giyer.noogleplatform.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by giyer7 on 3/6/17.
 */

public class Entities {

    @SerializedName("persons")
    @Expose
    private List<Object> persons = null;
    @SerializedName("organizations")
    @Expose
    private List<Object> organizations = null;
    @SerializedName("locations")
    @Expose
    private List<Object> locations = null;

    public List<Object> getPersons() {
        return persons;
    }

    public void setPersons(List<Object> persons) {
        this.persons = persons;
    }

    public List<Object> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Object> organizations) {
        this.organizations = organizations;
    }

    public List<Object> getLocations() {
        return locations;
    }

    public void setLocations(List<Object> locations) {
        this.locations = locations;
    }

}
