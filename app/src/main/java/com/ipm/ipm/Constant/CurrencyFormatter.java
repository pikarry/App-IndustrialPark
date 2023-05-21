package com.ipm.ipm.Constant;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    public static String formatVND(long amount) {
        Locale vnLocale = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(vnLocale);
        return vnFormat.format(amount);
    }
}
