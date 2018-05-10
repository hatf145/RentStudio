package iteso.com.rentstudio.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Lessor implements Parcelable {
    private String email;
    private String lastname;
    private String name;
    private String phone;

    public Lessor(){

    }

    public Lessor(String email, String lastname, String name, String phone) {
        this.email = email;
        this.lastname = lastname;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.lastname);
        dest.writeString(this.name);
        dest.writeString(this.phone);
    }

    protected Lessor(Parcel in) {
        this.email = in.readString();
        this.lastname = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<Lessor> CREATOR = new Creator<Lessor>() {
        @Override
        public Lessor createFromParcel(Parcel source) {
            return new Lessor(source);
        }

        @Override
        public Lessor[] newArray(int size) {
            return new Lessor[size];
        }
    };
}

