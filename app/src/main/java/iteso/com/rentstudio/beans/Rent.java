package iteso.com.rentstudio.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by hecto on 31/03/2018.
 */

public class Rent implements Parcelable {
    private String address;
    private String lessor;
    private String date;
    private String phone;
    private Integer cost;
    private String mail;



    public Rent(String address, String lessor, String date, String phone, Integer cost, String mail) {
        this.address = address;
        this.lessor = lessor;
        this.date = date;
        this.phone = phone;
        this.cost = cost;
        this.mail = mail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.lessor);
        dest.writeString(this.date);
        dest.writeString(this.phone);
        dest.writeValue(this.cost);
        dest.writeString(this.mail);
    }

    public Rent() {
    }

    protected Rent(Parcel in) {
        this.address = in.readString();
        this.lessor = in.readString();
        this.date = in.readString();
        this.phone = in.readString();
        this.cost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mail = in.readString();
    }

    public static final Creator<Rent> CREATOR = new Creator<Rent>() {
        @Override
        public Rent createFromParcel(Parcel source) {
            return new Rent(source);
        }

        @Override
        public Rent[] newArray(int size) {
            return new Rent[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLessor() {
        return lessor;
    }

    public void setLessor(String lessor) {
        this.lessor = lessor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public static Creator<Rent> getCREATOR() {
        return CREATOR;
    }
}