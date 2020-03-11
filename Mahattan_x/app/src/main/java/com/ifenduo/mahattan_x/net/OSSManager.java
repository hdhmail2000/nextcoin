package com.ifenduo.mahattan_x.net;

import android.content.Context;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

public class OSSManager {

    private static OSS oss;
    public static String OSS_BUCKET_NAME = "mhdcoinapp";
    public static final String OSS_END_POINT = "https://oss-cn-beijing.aliyuncs.com";
    public static final String OSS_BUCKET = "https://mhdcoinapp.oss-cn-beijing.aliyuncs.com/";

    public static void init(Context context) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI0CvJNUvVdHKA", "kHpZ6TGCTYM9RuBnfj5gljmfWGP4P9");
        oss = new OSSClient(context.getApplicationContext(), OSS_END_POINT, credentialProvider);
    }

    public static OSS getOss() {
        return oss;
    }
}
