package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.SerializedName;

public enum CatalogSource {
    @SerializedName("vk")
    Vkontakte(1),

    @SerializedName("fb")
    Facebook(2);

    private int code;

    CatalogSource(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CatalogSource getByCode(int code) {

        for (CatalogSource item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        return null;
    }
}
