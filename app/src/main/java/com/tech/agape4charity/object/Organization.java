package com.tech.agape4charity.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Charitha Ratnayake on 6/5/2018.
 */

public class Organization implements Parcelable {
    @Override
    public String toString() {
        return "Organization{" +
                "catId=" + catId +
                ", orgId=" + orgId +
                ", eventId=" + eventId +
                ", categoryName='" + categoryName + '\'' +
                ", note='" + note + '\'' +
                ", eventName='" + eventName + '\'' +
                ", dateTime=" + dateTime +
                ", eventStatus='" + eventStatus + '\'' +
                ", eventDate=" + eventDate +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgContactNo='" + orgContactNo + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                ", orgAddress='" + orgAddress + '\'' +
                ", orgUrl='" + orgUrl + '\'' +
                ", orgWeb='" + orgWeb + '\'' +
                ", screen='" + screen + '\'' +
                '}';
    }

    @SerializedName("cat_id")
    private long catId;
    @SerializedName("org_id")
    private long orgId;
    @SerializedName("event_id")
    private long eventId;
    @SerializedName("cat_name")
    private String categoryName;
    @SerializedName("event_note")
    private String note;
    @SerializedName("event_name")
    private String eventName;
    @SerializedName("date_time")
    private long dateTime;
    @SerializedName("event_status")
    private String eventStatus;
    @SerializedName("event_date")
    private long eventDate;
    @SerializedName("event_location")
    private String eventLocation;
    @SerializedName("event_description")
    private String eventDescription;
    @SerializedName("org_description")
    private String orgDescription;
    @SerializedName("org_name")
    private String orgName;
    @SerializedName("org_contact_no")
    private String orgContactNo;
    @SerializedName("org_email")
    private String orgEmail;
    @SerializedName("org_address")
    private String orgAddress;
    @SerializedName("org_url")
    private String orgUrl;
    @SerializedName("org_web")
    private String orgWeb;
    private String screen;


    public String getOrgWeb() {
        return orgWeb;
    }

    public void setOrgWeb(String orgWeb) {
        this.orgWeb = orgWeb;
    }



    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }



    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgContactNo() {
        return orgContactNo;
    }

    public void setOrgContactNo(String orgContactNo) {
        this.orgContactNo = orgContactNo;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.catId);
        dest.writeLong(this.orgId);
        dest.writeLong(this.eventId);
        dest.writeString(this.categoryName);
        dest.writeString(this.note);
        dest.writeString(this.eventName);
        dest.writeLong(this.eventDate);
        dest.writeString(this.eventLocation);
        dest.writeString(this.eventDescription);
        dest.writeString(this.orgName);
        dest.writeString(this.orgContactNo);
        dest.writeString(this.orgEmail);
        dest.writeString(this.orgAddress);
        dest.writeString(this.orgUrl);
        dest.writeLong(this.dateTime);
        dest.writeString(this.eventStatus);
        dest.writeString(this.orgDescription);
        dest.writeString(this.orgWeb);
    }

    public Organization() {
    }

    protected Organization(Parcel in) {
        this.catId = in.readLong();
        this.orgId = in.readLong();
        this.eventId = in.readLong();
        this.categoryName = in.readString();
        this.note = in.readString();
        this.eventName = in.readString();
        this.eventDate = in.readLong();
        this.eventLocation = in.readString();
        this.eventDescription = in.readString();
        this.orgName = in.readString();
        this.orgContactNo = in.readString();
        this.orgEmail = in.readString();
        this.orgAddress = in.readString();
        this.orgUrl = in.readString();
        this.dateTime = in.readLong();
        this.eventStatus = in.readString();
        this.orgDescription = in.readString();
        this.orgWeb = in.readString();
    }

    public static final Parcelable.Creator<Organization> CREATOR = new Parcelable.Creator<Organization>() {
        @Override
        public Organization createFromParcel(Parcel source) {
            return new Organization(source);
        }

        @Override
        public Organization[] newArray(int size) {
            return new Organization[size];
        }
    };

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

}
