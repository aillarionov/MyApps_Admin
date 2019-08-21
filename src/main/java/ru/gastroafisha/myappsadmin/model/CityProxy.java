package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 22.12.2017.
 */

public class CityProxy {

    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("name") @Expose // GSON
    private String name;

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
 
}
