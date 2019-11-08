package com.tech.agape4charity.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class Category {

    @SerializedName("category_id")
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
