package com.mcal.androlib.meta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class PackageInfo {
    public String forcedPackageId;
    public String renameManifestPackage;

    @Nullable
    public static PackageInfo load(@NonNull JSONObject json) throws JSONException {
        if (json.isNull("PackageInfo"))
            return null;
        JSONObject pkg = json.getJSONObject("PackageInfo");
        PackageInfo p = new PackageInfo();
        p.forcedPackageId = MetaInfo.getString(pkg, "forcedPackageId");
        p.renameManifestPackage = MetaInfo.getString(pkg, "renameManifestPackage");
        return p;
    }


    public void save(@NonNull JSONObject json) throws JSONException {
        JSONObject pkg = new JSONObject();
        MetaInfo.putString(pkg, "forcedPackageId", forcedPackageId);
        MetaInfo.putString(pkg, "renameManifestPackage", renameManifestPackage);
        json.put("PackageInfo", pkg);
    }
}