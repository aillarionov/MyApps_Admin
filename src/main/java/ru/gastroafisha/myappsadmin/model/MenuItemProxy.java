package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Map;

public class MenuItemProxy implements Serializable {

    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("name") @Expose // GSON
    private String name;

    @SerializedName("type") @Expose // GSON
    private MenuItemType type;

    @SerializedName("icon") @Expose // GSON
    private IconsEnum icon;

    @SerializedName("catalog") @Expose // GSON
    private Integer catalog;
    
    @SerializedName("form") @Expose // GSON
    private Integer form;

    @SerializedName("params") @Expose // GSON
    private Map<String, String> params;

    @SerializedName("order") @Expose // GSON
    private Integer order;

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

    public MenuItemType getType() {
        return type;
    }

    public void setType(MenuItemType type) {
        this.type = type;
    }

    public IconsEnum getIcon() {
        return icon;
    }

    public void setIcon(IconsEnum icon) {
        this.icon = icon;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Integer getCatalog() {
        return catalog;
    }

    public void setCatalog(Integer catalog) {
        this.catalog = catalog;
    }

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
