package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by alex on 22.12.2017.
 */

public class FormProxy {

    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("name") @Expose // GSON
    private String name;

    @SerializedName("items") @Expose // GSON
    private List<FormItemProxy> formItems;

    @SerializedName("dataEmail") @Expose // GSON
    private String dataEmail;
    
            
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

    public List<FormItemProxy> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<FormItemProxy> formItems) {
        this.formItems = formItems;
    }

    public String getDataEmail() {
        return dataEmail;
    }

    public void setDataEmail(String dataEmail) {
        this.dataEmail = dataEmail;
    }
    
}
