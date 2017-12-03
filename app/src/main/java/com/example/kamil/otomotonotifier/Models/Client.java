package com.example.kamil.otomotonotifier.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by kamil on 14/11/2017.
 */
@Entity
public class Client {
    @PrimaryKey
    String phoneNumber;
    Date payDeadline;
    Date registrationDate;
    boolean active;

    @Ignore
    public Client(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        active = true;
        registrationDate = new Date();
        initPayDeadline();
    }

    private void initPayDeadline() {
        setPayDeadlineToInDateOneMonth();
    }

    private void movePayDeadline() {
        Date today = new Date();
        if(today.before(payDeadline)) {
            addOneMonthToDeadlineDate();
        } else {
            setPayDeadlineToInDateOneMonth();
        }
    }

    private void setPayDeadlineToInDateOneMonth() {
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.add(Calendar.MONTH, 1);
        payDeadline = calendarToday.getTime();
    }

    private void addOneMonthToDeadlineDate() {
        Calendar payDeadlineCalendar = Calendar.getInstance();
        payDeadlineCalendar.setTime(payDeadline);
        payDeadlineCalendar.add(Calendar.MONTH, 1);
        payDeadline = payDeadlineCalendar.getTime();
    }

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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
