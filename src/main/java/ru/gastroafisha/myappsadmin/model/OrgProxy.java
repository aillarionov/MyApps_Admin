package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 *
 * @author alex
 */
public class OrgProxy {
    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("name") @Expose // GSON
    private String name;

    @SerializedName("logo") @Expose // GSON
    private String logo;

    @SerializedName("menuItems") @Expose // GSON
    private List<MenuItemProxy> menuItems;

    @SerializedName("forms") @Expose // GSON
    private List<FormProxy> forms;

    @SerializedName("catalogs") @Expose // GSON
    private List<CatalogProxy> catalogs;

    @SerializedName("ticket") @Expose // GSON
    private TicketProxy ticket;
    
    @SerializedName("city") @Expose // GSON
    private CityProxy city;
    
    @SerializedName("suspend") @Expose // GSON
    private Boolean suspend;

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

    public List<MenuItemProxy> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemProxy> menuItems) {
        this.menuItems = menuItems;
    }

    public List<FormProxy> getForms() {
        return forms;
    }

    public void setForms(List<FormProxy> forms) {
        this.forms = forms;
    }

    public List<CatalogProxy> getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(List<CatalogProxy> catalogs) {
        this.catalogs = catalogs;
    }

    public TicketProxy getTicket() {
        return ticket;
    }

    public void setTicket(TicketProxy ticket) {
        this.ticket = ticket;
    }

    public CityProxy getCity() {
        return city;
    }

    public void setCity(CityProxy city) {
        this.city = city;
    }

    public Boolean getSuspend() {
        return suspend;
    }

    public void setSuspend(Boolean suspend) {
        this.suspend = suspend;
    }
    
}
