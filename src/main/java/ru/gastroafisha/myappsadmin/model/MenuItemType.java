package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by alex on 30.12.2017.
 */

public enum MenuItemType implements Serializable {
    @SerializedName("standart")
    Standart(1),

    @SerializedName("form")
    Form(2),

    @SerializedName("catalog")
    Catalog(3);

    private int code;

    MenuItemType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MenuItemType getByCode(int code) {

        for (MenuItemType item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        return null;
    }
}
