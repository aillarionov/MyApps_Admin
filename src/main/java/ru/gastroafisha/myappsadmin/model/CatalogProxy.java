package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Map;


/**
 * Created by alex on 22.12.2017.
 */

public class CatalogProxy {

    @SerializedName("id") @Expose // GSON
    private Integer id;

    @SerializedName("type") @Expose // GSON
    private CatalogType type;

    @SerializedName("visitorVisible") @Expose // GSON
    private Boolean visitorVisible;
    
    @SerializedName("presenterVisible") @Expose // GSON
    private Boolean presenterVisible;
    
    @SerializedName("source") @Expose // GSON
    private CatalogSource source;

    @SerializedName("sourceOwner") @Expose // GSON
    private String sourceOwner;

    @SerializedName("sourceAlbum") @Expose // GSON
    private String sourceAlbum;

    @SerializedName("visitorNotificationFilter") @Expose // GSON
    private String visitorNotificationFilter;

    @SerializedName("presenterNotificationFilter") @Expose // GSON
    private String presenterNotificationFilter;

    @SerializedName("params") @Expose // GSON
    private Map<String, String> params;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CatalogType getType() {
        return type;
    }

    public void setType(CatalogType type) {
        this.type = type;
    }

    public Boolean getVisitorVisible() {
        return visitorVisible;
    }

    public void setVisitorVisible(Boolean visitorVisible) {
        this.visitorVisible = visitorVisible;
    }

    public Boolean getPresenterVisible() {
        return presenterVisible;
    }

    public void setPresenterVisible(Boolean presenterVisible) {
        this.presenterVisible = presenterVisible;
    }

    public CatalogSource getSource() {
        return source;
    }

    public void setSource(CatalogSource source) {
        this.source = source;
    }

    public String getSourceOwner() {
        return sourceOwner;
    }

    public void setSourceOwner(String sourceOwner) {
        this.sourceOwner = sourceOwner;
    }

    public String getSourceAlbum() {
        return sourceAlbum;
    }

    public void setSourceAlbum(String sourceAlbum) {
        this.sourceAlbum = sourceAlbum;
    }

    public String getVisitorNotificationFilter() {
        return visitorNotificationFilter;
    }

    public void setVisitorNotificationFilter(String visitorNotificationFilter) {
        this.visitorNotificationFilter = visitorNotificationFilter;
    }

    public String getPresenterNotificationFilter() {
        return presenterNotificationFilter;
    }

    public void setPresenterNotificationFilter(String presenterNotificationFilter) {
        this.presenterNotificationFilter = presenterNotificationFilter;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
