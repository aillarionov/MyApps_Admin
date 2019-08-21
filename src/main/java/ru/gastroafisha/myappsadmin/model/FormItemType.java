package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 30.12.2017.
 */

public enum FormItemType {
    @SerializedName("string")
    String(1),

    @SerializedName("phone")
    Phone(2),

    @SerializedName("email")
    Email(3),

    @SerializedName("text")
    Text(4);


    private int code;

    FormItemType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static FormItemType getByCode(int code) {

        for (FormItemType item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        return null;
    }
}
