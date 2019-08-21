package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.SerializedName;

public enum CatalogType {
    @SerializedName("item")
    Item(1),

    @SerializedName("member")
    Member(2),

    @SerializedName("image")
    Image(3),

    @SerializedName("news")
    News(4);


    private int code;

    CatalogType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CatalogType getByCode(int code) {

        for (CatalogType item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        return null;
    }
}
