package ru.gastroafisha.myappsadmin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alex on 30.12.2017.
 */

public enum TicketType {
    @SerializedName("url")
    Url(1),

    @SerializedName("simple")
    Simple(2);

    private int code;

    TicketType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TicketType getByCode(int code) {

        for (TicketType item : values()) {
            if (item.getCode() == code) {
                return item;
            }
        }

        return null;
    }
}
