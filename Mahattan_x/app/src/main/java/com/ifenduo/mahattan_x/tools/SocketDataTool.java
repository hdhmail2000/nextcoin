package com.ifenduo.mahattan_x.tools;

import com.ifenduo.mahattan_x.Constant;
import com.ifenduo.mahattan_x.entity.KLineChartTick;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by ll on 2018/3/12.
 */

public class SocketDataTool {

    public static EntrySet parseKLineData(List<KLineChartTick> data, String timeInterval) {
        final EntrySet entrySet = new EntrySet();
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                KLineChartTick tick = data.get(i);
                if (tick != null) {
                    entrySet.addEntry(new Entry((float) tick.getOpen(), (float) tick.getHigh(), (float) tick.getLow(),
                            (float) tick.getClose(), (int) tick.getVol(), SocketDataTool.getXAxisIndex(timeInterval, tick.getTime())[0],
                            SocketDataTool.getXAxisIndex(timeInterval, tick.getTime())[1]));
                }
            }
        }
        return entrySet;
    }

    public static String[] getXAxisIndex(String timeInterval, long time) {
        String[] strIndex = new String[2];
        String pattern = DateTool.PATTERN_YYYY_MM_DD_HH_MM;
        String pattern_ = DateTool.PATTERN_YYYY_MM_DD_HH_MM;
        if (Constant.K_LINE_TIME_INTERVAL_1_MIN.equals(timeInterval)) {
            pattern = DateTool.PATTERN_HH_MM;
            pattern_ = DateTool.PATTERN_YYYY_MM_DD_HH_MM;
        } else if (Constant.K_LINE_TIME_INTERVAL_5_MIN.equals(timeInterval)) {
            pattern = DateTool.PATTERN_HH_MM;
            pattern_ = DateTool.PATTERN_YYYY_MM_DD_HH_MM;
        } else if (Constant.K_LINE_TIME_INTERVAL_15_MIN.equals(timeInterval)) {
            pattern = DateTool.PATTERN_HH_MM;
            pattern_ = DateTool.PATTERN_YYYY_MM_DD_HH_MM;
        } else if (Constant.K_LINE_TIME_INTERVAL_30_MIN.equals(timeInterval)) {
            pattern = DateTool.PATTERN_HH_MM;
            pattern_ = DateTool.PATTERN_YYYY_MM_DD_HH_MM;
        } else if (Constant.K_LINE_TIME_INTERVAL_60_MIN.equals(timeInterval)) {
            pattern = DateTool.PATTERN_HH_MM;
            pattern_ = DateTool.PATTERN_YYYY_MM_DD_HH_MM;
        } else if (Constant.K_LINE_TIME_INTERVAL_1_DAY.equals(timeInterval)) {
            pattern = DateTool.PATTERN_MM_DD;
            pattern_ = DateTool.PATTERN_YYYY_MM_DD;
        } else if (Constant.K_LINE_TIME_INTERVAL_1_WEEK.equals(timeInterval)) {
            pattern = DateTool.PATTERN_MM_DD;
            pattern_ = DateTool.PATTERN_YYYY_MM_DD;
        } else if (Constant.K_LINE_TIME_INTERVAL_1_MON.equals(timeInterval)) {
            pattern = DateTool.PATTERN_YYYY_MM;
            pattern_ = DateTool.PATTERN_YYYY_MM;
        }
        strIndex[0] = DateTool.formatTimeWithPattern(time, pattern_);
        strIndex[1] = DateTool.formatTimeWithPattern(time, pattern);
        return strIndex;
    }

    /**
     * 字符串解压缩
     *
     * @param data
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decompressStr(String data) throws IOException {
        return new String(decompress(HexStringToBinary(data)), "utf-8");
    }

    /**
     * 数据解压缩
     *
     * @param is
     * @param os
     * @throws IOException
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os) throws IOException {
        GZIPInputStream gis = new GZIPInputStream(is);
        int count;
        byte data[] = new byte[1024];
        while ((count = gis.read(data, 0, 1024)) != -1) {
            os.write(data, 0, count);
        }
        gis.close();
    }

    /**
     * 数据解压缩
     *
     * @param data
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static byte[] decompress(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 解压缩
        decompress(bais, baos);
        data = baos.toByteArray();
        baos.flush();
        baos.close();
        bais.close();
        return data;
    }

    /**
     * @param hexString
     * @return 将十六进制转换为字节数组
     */
    public static byte[] HexStringToBinary(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
