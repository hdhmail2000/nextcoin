package com.ifenduo.mahattan_x.net;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class UploadFiles {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";
    HashMap<String, File> files;
    HashMap<String, String> params;
    String requestURL;
    String requestTag;
    UploadListener uploadListener;

    public UploadFiles(HashMap<String, File> files, HashMap<String, String> params, String requestURL, String requestTag, UploadListener uploadListener) {
        this.files = files;
        this.params = params;
        this.requestURL = requestURL;
        this.requestTag = requestTag;
        this.uploadListener = uploadListener;
    }

    public void uploadFile() {
        new UploadAsyncTask(this).execute();
    }

    static class UploadAsyncTask extends AsyncTask {
        WeakReference<UploadFiles> uploadFilesWeakReference;

        public UploadAsyncTask(UploadFiles uploadFiles) {
            super();
            this.uploadFilesWeakReference = new WeakReference<UploadFiles>(uploadFiles);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            UploadFiles uploadFiles = uploadFilesWeakReference.get();
            if (uploadFiles != null) {
                HashMap<String, String> param = uploadFiles.params;
                HashMap<String, File> fileMap = uploadFiles.files;
                String url = uploadFiles.requestURL;
                String tag = uploadFiles.requestTag;
                UploadListener uploadListener = uploadFiles.uploadListener;
                startUploadFile(fileMap, param, url, tag, uploadListener);
            }
            return null;
        }

        /**
         * * android上传文件到服务器
         *
         * @param files      需要上传的文件
         * @param requestURL 请求的rul
         */
        private void startUploadFile(HashMap<String, File> files, HashMap<String, String> params,
                                     String requestURL, String requestTag, UploadListener uploadListener) {
            String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
            String PREFIX = "--", LINE_END = "\r\n";
            String CONTENT_TYPE = "multipart/form-data"; // 内容类型
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            Message msg = new Message();

            try {
                URL url = new URL(requestURL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TIME_OUT);
                conn.setConnectTimeout(TIME_OUT);
                conn.setDoInput(true); // 允许输入流
                conn.setDoOutput(true); // 允许输出流
                conn.setUseCaches(false); // 不允许使用缓存
                conn.setRequestMethod("POST"); // 请求方式
                conn.setRequestProperty("Charset", CHARSET);
                // 设置编码
                conn.setRequestProperty("connection", "keep-alive");
                conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
                if (params != null) {
                    StringBuilder sb2 = new StringBuilder();
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        sb2.append(PREFIX);
                        sb2.append(BOUNDARY);
                        sb2.append(LINE_END);
                        sb2.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
                        sb2.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
                        sb2.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                        sb2.append(LINE_END);
                        sb2.append(entry.getValue());
                        sb2.append(LINE_END);
                    }
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.write(sb2.toString().getBytes());
                } else {
                    dos = new DataOutputStream(conn.getOutputStream());
                }

                if (files != null && files.size() > 0) {
                    for (Map.Entry<String, File> file : files.entrySet()) {
                        if (file != null) {
                            /** * 当文件不为空，把文件包装并且上传 */
                            StringBuffer sb = new StringBuffer();
                            sb.append(PREFIX);
                            sb.append(BOUNDARY);
                            sb.append(LINE_END);
                            /**
                             * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                             * filename是文件的名字，包含后缀名的 比如:abc.png
                             */
                            sb.append("Content-Disposition: form-data; name=\"" + file.getKey() + "\"; filename=\"" + file.getValue().getName() + "\""
                                    + LINE_END);
                            sb.append("Content-Type: image/jpeg; charset=" + CHARSET + LINE_END);
                            sb.append(LINE_END);
                            dos.write(sb.toString().getBytes());
                            InputStream is = new FileInputStream(file.getValue());
                            byte[] bytes = new byte[1024];
                            int len = 0;
                            while ((len = is.read(bytes)) != -1) {
                                dos.write(bytes, 0, len);
                            }
                            is.close();
                            dos.write("\r\n".getBytes());
                        }
                    }
                    dos.write(LINE_END.getBytes());
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                    dos.write(end_data);
                    dos.flush();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream is1 = null;
            if (conn != null) {
                try {
                    is1 = conn.getInputStream();
                    String ch;
                    BufferedReader read = new BufferedReader(new InputStreamReader(is1));
                    StringBuffer b = new StringBuffer();
                    String line;
                    while ((line = read.readLine()) != null) {
                        b.append(line);
                    }
                    String str = new String(b.toString().getBytes(), "utf-8");
                    is1.close();

                    /**
                     * 获取响应码 200=成功 当响应成功，获取响应的流
                     */
                    int res = conn.getResponseCode();
                    Log.d("TAG","uploadListener != null  ="+(uploadListener != null));
                    if (uploadListener != null) {
                        uploadListener.uploadCallBack(res, b.toString(), requestTag);
                    }

                    dos.close();
                    conn = null;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            conn = null;
            if (uploadListener != null) {
                uploadListener.uploadCallBack(-1, "", requestTag);
            }
        }
    }


    public interface UploadListener {
         void uploadCallBack(int statusCode, String resultJson, String requestTag);
    }

}

