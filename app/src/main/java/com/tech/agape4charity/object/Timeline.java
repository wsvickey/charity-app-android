package com.tech.agape4charity.object;

/**
 * Created by SmasH on 6/6/2018.
 */

public class Timeline {
    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDonatedAmount() {
        return donatedAmount;
    }

    public void setDonatedAmount(double donatedAmount) {
        this.donatedAmount = donatedAmount;
    }

    private String charityName;
    private String date;
    private double donatedAmount;
}
