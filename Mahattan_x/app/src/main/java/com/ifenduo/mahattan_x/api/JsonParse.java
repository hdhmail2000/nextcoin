package com.ifenduo.mahattan_x.api;

import com.google.gson.Gson;
import com.ifenduo.common.log.XCLOG;
import com.ifenduo.mahattan_x.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xuefengyang on 2016/2/26.
 */
public class JsonParse {

    public static Gson gson = new Gson();


    public static BaseEntity getBaseEntityForJsonArray(String json) {
        XCLOG.d(json);
        BaseEntity entity = new BaseEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.setCode(jsonObject.optInt("code"));
            entity.setMsg(jsonObject.optString("msg"));
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            if (jsonArray != null) {
                entity.setJsonData(jsonArray.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            entity.setCode(0);
            entity.setMsg("");
            entity.setJsonData("");
        }
        return entity;
    }



    public static BaseEntity getCommonBaseEntityForJsonObject(String json) {
        XCLOG.d(json);
        BaseEntity entity = new BaseEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.setCode(jsonObject.optInt("code"));
            entity.setMsg(jsonObject.optString("msg"));
            JSONObject jsonObj = jsonObject.optJSONObject("data");
            if (jsonObj != null) {
                entity.setJsonData(jsonObj.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            entity.setCode(0);
            entity.setMsg("");
            entity.setJsonData("");
        }
        return entity;
    }

    public static BaseEntity getCommonBaseEntityForSting(String json) {
        XCLOG.d(json);
        BaseEntity entity = new BaseEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.setCode(jsonObject.optInt("code"));
            entity.setMsg(jsonObject.optString("msg"));
            entity.setJsonData(jsonObject.optString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
            entity.setCode(0);
            entity.setMsg("");
            entity.setJsonData("");
        }
        return entity;
    }

    public static BaseEntity getCommonBaseEntityForInteger(String json) {
        XCLOG.d(json);
        BaseEntity entity = new BaseEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.setCode(jsonObject.optInt("code"));
            entity.setMsg(jsonObject.optString("msg"));
            entity.setJsonData(String.valueOf(jsonObject.optInt("data")));
        } catch (JSONException e) {
            e.printStackTrace();
            entity.setCode(0);
            entity.setMsg("");
            entity.setJsonData("");
        }
        return entity;
    }


    public static BaseEntity getBaseEntityForJsonObject(String json, String key) {
        BaseEntity entity = new BaseEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.setCode(jsonObject.optInt("code"));
            entity.setMsg(jsonObject.optString("msg"));
            JSONObject jsonObj = jsonObject.optJSONObject(key);
            if (jsonObj != null) {
                entity.setJsonData(jsonObj.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            entity.setCode(0);
            entity.setMsg("");
            entity.setJsonData("");
        }
        return entity;
    }

    public static String getReturnDataJsonObjectString(String json) {
        String result = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.optJSONObject("data");
            if (jsonObject1 != null) {
                result = jsonObject1.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getReturnDataJsonObjectString(String key, String json) {
        String result = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonObject1 = jsonObject.optJSONObject(key);
            if (jsonObject1 != null) {
                result = jsonObject1.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getReturnDataJsonArrayString(String json) {
        String result = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            if (jsonArray != null) {
                result = jsonArray.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static BaseEntity getReturnForJsonArray(String json) {
        BaseEntity entity = new BaseEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.setCode(jsonObject.optInt("code"));
            entity.setMsg(jsonObject.optString("msg"));
            JSONArray jsonArray = jsonObject.optJSONArray("return");
            if (jsonArray != null) {
                entity.setJsonData(jsonArray.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            entity.setCode(0);
            entity.setMsg("");
            entity.setJsonData("");
        }
        return entity;
    }

    public static BaseEntity getReturnForJsonObject(String json) {
        XCLOG.d(json);
        BaseEntity entity = new BaseEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.setCode(jsonObject.optInt("code"));
            entity.setMsg(jsonObject.optString("msg"));
            JSONObject jsonObj = jsonObject.optJSONObject("return");
            if (jsonObj != null) {
                entity.setJsonData(jsonObj.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            entity.setCode(0);
            entity.setMsg("");
            entity.setJsonData("");
        }
        return entity;
    }

    public static BaseEntity getReturnForSting(String json) {
        XCLOG.d(json);
        BaseEntity entity = new BaseEntity();
        try {
            JSONObject jsonObject = new JSONObject(json);
            entity.setCode(jsonObject.optInt("code"));
            entity.setMsg(jsonObject.optString("msg"));
            entity.setJsonData(jsonObject.optString("return"));
        } catch (JSONException e) {
            e.printStackTrace();
            entity.setCode(0);
            entity.setMsg("");
            entity.setJsonData("");
        }
        return entity;
    }



}
