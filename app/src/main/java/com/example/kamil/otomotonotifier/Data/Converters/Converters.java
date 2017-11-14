package com.example.kamil.otomotonotifier.Data.Converters;

import android.arch.persistence.room.TypeConverter;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value.longValue());
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : Long.valueOf(date.getTime());
    }
}
