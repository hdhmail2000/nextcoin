package com.ifenduo.mahattan_x.tools;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * Created by ll on 2018/3/6.
 */

public class NumberTool {
    public static String double2String(double param) {
        return String.format("%.2f", param);
    }

    public static String double2String(String param) {
        if (TextUtils.isEmpty(param)) {
            return "0.00";
        }
        double value = 0.00;
        try {
            value = Double.parseDouble(param);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
        return String.format("%.2f", value);
    }

    public static Double double2double(double param) {
        return Double.parseDouble(String.format("%.2f", param));
    }

    public static String charDouble4(double param) {
        return String.format("%.4f", param);
    }

    public static String charDouble4(String param) {
        if (TextUtils.isEmpty(param)) {
            return "0.0000";
        } else {
            try {
                double d = Double.parseDouble(param);
                return String.format("%.4f", d);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return "0.0000";
            }
        }

    }


    /**
     * 计算涨幅
     *
     * @return
     */
    public static double calculateRise(double open, double close) {
        BigDecimal riseTmp = MathUtils.div(MathUtils.sub(BigDecimal.valueOf(close),
                BigDecimal.valueOf(open)), BigDecimal.valueOf(open));
        BigDecimal rise = MathUtils.toScaleNumHalfUp(MathUtils.mul(riseTmp, BigDecimal.valueOf(100)), 2);
        return rise.doubleValue();
    }

}
