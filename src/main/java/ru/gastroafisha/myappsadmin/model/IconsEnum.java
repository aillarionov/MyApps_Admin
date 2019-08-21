package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author alex
 */
public enum IconsEnum {
    
    // Common
    @SerializedName("address")
    Address("address"),

    @SerializedName("discounts")
    Discounts("discounts"),

    @SerializedName("entry")
    Entry("entry"),

    @SerializedName("exhibition")
    Exhibition("exhibition"),

    @SerializedName("exhibitions")
    Exhibitions("exhibitions"),

    @SerializedName("exhibitor")
    Exhibitor("exhibitor"),

    @SerializedName("favorites")
    Favorites("favorites"),

    @SerializedName("news")
    News("news"),

    @SerializedName("partners")
    Partners("partners"),

    @SerializedName("photogallery")
    Photogallery("photogallery"),

    @SerializedName("plan")
    Plan("plan"),

    @SerializedName("question")
    Question("question"),

    @SerializedName("search")
    Search("search"),

    @SerializedName("setting")
    Setting("setting"),

    @SerializedName("tickets")
    Tickets("tickets");

    private String code;

    IconsEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static IconsEnum getByCode(String code) {

        for (IconsEnum item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }

        return null;
    }
    
}
