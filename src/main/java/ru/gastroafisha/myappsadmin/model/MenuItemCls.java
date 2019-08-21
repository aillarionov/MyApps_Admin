package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 30.12.2017.
 */

public enum MenuItemCls {
    // Common
    @SerializedName("about")
    About(1),

    @SerializedName("map")
    Map(2),

    @SerializedName("search")
    Search(3),

    @SerializedName("favorites")
    Favorites(4),

    @SerializedName("plan")
    Plan(5),

    @SerializedName("ticket")
    Ticket(6),

    @SerializedName("url")
    Url(7);

    private int code;

    MenuItemCls(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
    public static MenuItemCls getByName(String name) {
        for (MenuItemCls item : values()) {
            if (item.name().toLowerCase().equals(name.toLowerCase())) {
                return item;
            }
        }

        return null;
    }

    public static MenuItemCls getByCode(int code) {

        for (MenuItemCls item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        return null;
    }
}
