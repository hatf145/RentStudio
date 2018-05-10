package iteso.com.rentstudio.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

public class Property implements Parcelable {
    private String address;
    private Integer cost;
    private String lessor;
    private String name;
    private Integer payday;
    private String state;
    private String town;


    public Property(){

    }

    public Property(String address, Integer cost, String lessor, String name, Integer payday, String state, String town) {
        this.address = address;
        this.cost = cost;
        this.lessor = lessor;
        this.name = name;
        this.payday = payday;
        this.state = state;
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getLessor() {
        return lessor;
    }

    public void setLessor(String lessor) {
        this.lessor = lessor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPayday() {
        return payday;
    }

    public void setPayday(Integer payday) {
        this.payday = payday;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeValue(this.cost);
        dest.writeString(this.lessor);
        dest.writeString(this.name);
        dest.writeValue(this.payday);
        dest.writeString(this.state);
        dest.writeString(this.town);
    }

    protected Property(Parcel in) {
        this.address = in.readString();
        this.cost = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lessor = in.readString();
        this.name = in.readString();
        this.payday = (Integer) in.readValue(Integer.class.getClassLoader());
        this.state = in.readString();
        this.town = in.readString();
    }

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel source) {
            return new Property(source);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };
}
