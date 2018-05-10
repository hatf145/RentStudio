package iteso.com.rentstudio.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by hecto on 31/03/2018.
 */

public class Rent implements Parcelable {
    private String property;
    private String lessor;
    private String date;

    public Rent(){
    }

    public Rent(String property, String lessor, String date) {
        this.property = property;
        this.lessor = lessor;
        this.date = date;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.property);
        dest.writeString(this.lessor);
        dest.writeString(this.date);
    }

    protected Rent(Parcel in) {
        this.property = in.readString();
        this.lessor = in.readString();
        this.date = in.readString();
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
}
