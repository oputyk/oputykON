package com.example.kamil.otomotonotifier.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by kamil on 14/11/2017.
 */
@Entity
public class Client {
    @PrimaryKey
    String phoneNumber;
    Date payDeadline;
    boolean active;

    public Client(String phoneNumber, Date payDeadline, boolean active) {
        this.phoneNumber = phoneNumber;
        this.payDeadline = payDeadline;
        this.active = active;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getPayDeadline() {
        return payDeadline;
    }

    public void setPayDeadline(Date payDeadline) {
        this.payDeadline = payDeadline;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
