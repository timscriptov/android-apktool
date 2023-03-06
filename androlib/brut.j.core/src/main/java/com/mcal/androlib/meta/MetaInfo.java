package com.mcal.androlib.meta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mcal.androlib.utils.FileHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetaInfo {
    public String version;
    public String apkFileName;
    public boolean isFrameworkApk;
    public UsesFramework usesFramework;
    public Map<String, String> sdkInfo;
    public PackageInfo packageInfo;
    public VersionInfo versionInfo;
    public boolean compressionType;
    public boolean sharedLibrary;
    public boolean sparseResources;
    public Map<String, String> unknownFiles;
    public Collection<String> doNotCompress;

    public static void putString(@NonNull JSONObject json, String name, String val) throws JSONException {
        json.put(name, val == null ? JSONObject.NULL : val);
    }

    public static String getString(@NonNull JSONObject json, String key) throws JSONException {
        String s;
        if (json.isNull(key))
            s = null;
        else
            s = json.getString(key);
        return s;
    }

    @Nullable
    public static Map<String, String> readMap(@NonNull JSONObject json, String name) throws JSONException {
        if (json.isNull(name))
            return null;
        JSONObject map = json.getJSONObject(name);
        Map<String, String> val = new HashMap<>();
        Iterator<String> keys = map.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            val.put(key, map.getString(key));
        }
        return val;
    }

    public static void putList(JSONObject json, Collection list, String name) throws JSONException {
        if (list == null) {
            json.put(name, JSONObject.NULL);
            return;
        }
        JSONArray array = new JSONArray(list);
        json.put(name, array);
    }

    private static void putMap(JSONObject json, Map<String, String> map, String name) throws JSONException {
        if (map == null) {
            json.put(name, JSONObject.NULL);
            return;
        }
        JSONObject sdk = new JSONObject(map);
        json.put(name, sdk);
    }

    @NonNull
    public static MetaInfo load(InputStream is) throws IOException, JSONException {
        String content = FileHelper.readInputStream(is);
        JSONObject json = new JSONObject(content);
        MetaInfo meta = new MetaInfo();
        meta.load(json);
        return meta;
    }

    @NonNull
    public static MetaInfo load(File file) throws IOException, JSONException {
        try (InputStream fis = new FileInputStream(file)) {
            return load(fis);
        }
    }

    public static MetaInfo loadYaml(InputStream in) {
        return getYaml().loadAs(in, MetaInfo.class);
    }

    private static Yaml getYaml() {
        DumperOptions dumpOptions = new DumperOptions();
        dumpOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        EscapedStringRepresenter representer = new EscapedStringRepresenter();
        PropertyUtils propertyUtils = representer.getPropertyUtils();
        propertyUtils.setSkipMissingProperties(true);

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setCodePointLimit(10 * 1024 * 1024); // 10mb

        return new Yaml(new ClassSafeConstructor(), representer, dumpOptions, loaderOptions);
    }

    public void save(Writer output) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        putString(json, "version", version);
        putString(json, "apkFileName", apkFileName);
        json.put("isFrameworkApk", isFrameworkApk);
        json.put("compressionType", compressionType);
        json.put("sharedLibrary", sharedLibrary);
        json.put("sparseResources", sparseResources);
        putMap(json, sdkInfo, "sdkInfo");
        putMap(json, unknownFiles, "unknownFiles");
        putList(json, doNotCompress, "doNotCompress");
        UsesFramework.save(json, usesFramework);
        if (packageInfo == null) {
            json.put("PackageInfo", JSONObject.NULL);
        } else {
            packageInfo.save(json);
        }
        VersionInfo.save(json, versionInfo);
        output.write(json.toString(2));
    }

    private void load(JSONObject json) throws JSONException {
        version = getString(json, "version");
        apkFileName = getString(json, "apkFileName");
        isFrameworkApk = json.getBoolean("isFrameworkApk");
        compressionType = json.getBoolean("compressionType");
        sparseResources = json.getBoolean("sparseResources");
        sdkInfo = readMap(json, "sdkInfo");
        unknownFiles = readMap(json, "unknownFiles");
        doNotCompress = readList(json, "doNotCompress");
        usesFramework = UsesFramework.load(json);
        packageInfo = PackageInfo.load(json);
        versionInfo = VersionInfo.load(json);
    }

    @Nullable
    private List<String> readList(@NonNull JSONObject json, String name) throws JSONException {
        if (json.isNull(name))
            return null;
        JSONArray arr = json.getJSONArray(name);
        List<String> list = new LinkedList<>();
        int l = arr.length();
        for (int i = 0; i < l; i++) {
            list.add(arr.getString(i));
        }
        return list;
    }

    public void save(File file) throws IOException, JSONException {
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        Writer writer = new BufferedWriter(outputStreamWriter);
        save(writer);
        writer.close();
        outputStreamWriter.close();
        fos.close();
    }

    public void saveYaml(File file) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                Writer writer = new BufferedWriter(outputStreamWriter)
        ) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            getYaml().dump(this, writer);
        }
    }
}