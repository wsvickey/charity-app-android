package com.tech.agape4charity.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class OrganizationResponseEvent extends AppResponse{
    @SerializedName("data")
    private ArrayList<Organization> organizations;

    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(ArrayList<Organization> organizations) {
        this.organizations = organizations;
    }
}
