package iteso.com.rentstudio.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String email;
    private String lastname;
    private String name;
    private String password;
    private String phone;
    private int type;

    public User(String email, String lastname, String name, String password, String phone, int type) {
        this.email = email;
        this.lastname = lastname;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.type = type;
    }

    public User(){

    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", lastname='" + lastname + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                '}';
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        dest.writeString(this.password);
        dest.writeString(this.phone);
        dest.writeInt(this.type);
    }

    protected User(Parcel in) {
        this.email = in.readString();
        this.lastname = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
