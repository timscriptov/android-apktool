package com.mcal.androlib.meta;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class UsesFramework {
    public List<Integer> ids;
    public String tag;

    public static void save(JSONObject json, UsesFramework usesFramework) throws JSONException {
        if (usesFramework == null) {
            json.put("UsesFramework", JSONObject.NULL);
            return;
        }
        usesFramework.save(json);
    }

    @NonNull
    public static UsesFramework load(@NonNull JSONObject json) throws JSONException {
        UsesFramework f = new UsesFramework();
        if (!json.isNull("UsesFramework")) {
            JSONObject framework = json.getJSONObject("UsesFramework");
            f.tag = framework.getString("tag");
            JSONArray arr = framework.getJSONArray("ids");
            List<Integer> ids = new LinkedList<>();
            int s = arr.length();
            for (int i = 0; i < s; i++)
                ids.add(arr.getInt(i));
            f.ids = ids;
        }
        return f;
    }


    public void save(@NonNull JSONObject json) throws JSONException {
        JSONObject framework = new JSONObject();
        MetaInfo.putList(framework, ids, "ids");
        MetaInfo.putString(framework, "tag", tag);
        json.put("UsesFramework", framework);
    }
}