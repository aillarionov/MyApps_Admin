package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author alex
 */
public class SimpleOrgProxy {
    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("name") @Expose // GSON
    private String name;

    @SerializedName("logo") @Expose // GSON
    private String logo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
