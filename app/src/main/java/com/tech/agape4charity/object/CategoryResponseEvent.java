package com.tech.agape4charity.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class CategoryResponseEvent extends AppResponse{
    @SerializedName("data")
    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
