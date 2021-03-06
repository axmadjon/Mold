package uz.mold.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static final ThreadLocal<SimpleDateFormat> FORMAT_AS_NUMBER = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd", Locale.US);
        }
    };

    public static final ThreadLocal<SimpleDateFormat> FORMAT_AS_DATE = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        }
    };

    public static final ThreadLocal<SimpleDateFormat> FORMAT_AS_DATETIME = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        }
    };

    public static final ThreadLocal<SimpleDateFormat> FORMAT_AS_TIME = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss", Locale.US);
        }
    };

    public static final ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        }
    };

    public static final ThreadLocal<SimpleDateFormat> FORMAT_AS_WEEK_DATE = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("EEEE, dd.MM.yyyy", Locale.getDefault());
        }
    };

    public static Date parse(String s) {
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        try {
            switch (s.length()) {
                case 8:
                    return FORMAT_AS_NUMBER.get().parse(s);
                case 10:
                    return FORMAT_AS_DATE.get().parse(s);
                case 14:
                    return YYYYMMDDHHMMSS.get().parse(s);
                default:
                    return FORMAT_AS_DATETIME.get().parse(s);
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date time format error:" + s);
        }
    }

    public static String format(Date date, SimpleDateFormat fmt) {
        try {
            return fmt.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String format(Date date, ThreadLocal<SimpleDateFormat> fmt) {
        return format(date, fmt.get());
    }

    public static String convert(String s, SimpleDateFormat fmt) {
        return format(parse(s), fmt);
    }

    public static String convert(String s, ThreadLocal<SimpleDateFormat> fmt) {
        return convert(s, fmt.get());
    }

}
