package edu.gatech.group16.watersourcingproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;

import edu.gatech.group16.watersourcingproject.model.AccountType;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Edwin Zhao on 2017/02/21.
 */

public class User implements Serializable {
    public static List<AccountType> legalClass = Arrays.asList(AccountType.values());
    public String name;
    public String email;
    public String password;
    public AccountType accountType;

    public User() {
    }
    public User(String email, String password, String name, AccountType accountType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    private User(Parcel in) {
//        accountType = (AccountType) in.readSerializable();
//        name = in.readString();
//        email =  in.readString();
//        password = in.readString();
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeSerializable(accountType);
//        dest.writeString(name);
//        dest.writeString(email);
//        dest.writeString(password);
//    }

//    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };
}

