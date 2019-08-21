package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 23.12.2017.
 */

public class TicketProxy {

    @SerializedName("id") @Expose // GSON
    private Integer id;
    
    @SerializedName("type") @Expose
    private TicketType type;

    @SerializedName("source") @Expose
    private String source;

    @SerializedName("text") @Expose
    private String text;

    @SerializedName("button") @Expose
    private String button;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
}
