package com.ifenduo.mahattan_x.tools;


import android.text.TextUtils;

import java.math.BigDecimal;

public class NumberUtil {
    public static final int MONEY_DECIMAL_LENGTH = 4;//金额小数点后保留长度

    public static String setNumberW(int number) {
        if (number > 10000) {
            double d = number / 10000f;
            return String.format("%.1f", d) + "w";
        }
        return number + "";
    }

    /**
     * 格式化人民币
     *
     * @param money
     * @return
     */
    public static String formatRMB(String money) {
        if (TextUtils.isEmpty(money)) {
            return "0.00";
        }
        try {
            double money_ = Double.parseDouble(money);
            BigDecimal bigDecimal = new BigDecimal(money_);
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }
    /**
     * 格式化人民币
     *
     * @param money
     * @return
     */
    public static String formatRMB(double money) {
        BigDecimal bigDecimal = new BigDecimal(money);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }
    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    public static String formatMoney(double money) {
        BigDecimal bigDecimal = new BigDecimal(money);
        return bigDecimal.setScale(MONEY_DECIMAL_LENGTH, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    public static String formatMoney(String money) {
        if (TextUtils.isEmpty(money)) {
            return "0.000000";
        }
        try {
            double money_ = Double.parseDouble(money);
            BigDecimal bigDecimal = new BigDecimal(money_);
            return bigDecimal.setScale(MONEY_DECIMAL_LENGTH, BigDecimal.ROUND_HALF_UP).toPlainString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.000000";
        }
    }

    /**
     * 保留两位小数
     *
     * @return
     */
    public static String double2String2(String param) {
        if (TextUtils.isEmpty(param)) {
            return "0.00";
        }
        try {
            double param_ = Double.parseDouble(param);
            BigDecimal bigDecimal = new BigDecimal(param_);
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * 保留两位小数
     *
     * @return
     */
    public static String double2String2(double param) {
        BigDecimal bigDecimal = new BigDecimal(param);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
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
