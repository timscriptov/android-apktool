package com.mcal.androlib.meta;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class VersionInfo {
    public String versionCode;
    public String versionName;

    public static void save(JSONObject json, VersionInfo versionInfo) throws JSONException {
        if (versionInfo == null) {
            json.put("VersionInfo", JSONObject.NULL);
            return;
        }
        versionInfo.save(json);
    }

    @NonNull
    public static VersionInfo load(@NonNull JSONObject json) throws JSONException {
        VersionInfo v = new VersionInfo();
        if (!json.isNull("VersionInfo")) {
            JSONObject ver = json.getJSONObject("VersionInfo");
            v.versionCode = MetaInfo.getString(ver, "versionCode");
            v.versionName = MetaInfo.getString(ver, "versionName");
        }
        return v;
    }


    public void save(@NonNull JSONObject json) throws JSONException {
        JSONObject ver = new JSONObject();
        MetaInfo.putString(ver, "versionCode", versionCode);
        MetaInfo.putString(ver, "versionName", versionName);
        json.put("VersionInfo", ver);
    }
}