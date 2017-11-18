package com.example.kamil.otomotonotifier.AdEngine.Blockers;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kamil on 28/10/2017.
 */

public class ByDateBlocker {
    static public boolean isBlocked(Date blockDay) {
        Date now = Calendar.getInstance().getTime();
        return now.after(blockDay);
    }
}
