package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    private final static String NAME = "name",MAINNAME = "mainName",ALSOKNOWNAS = "alsoKnownAs",PLACEOFORIGIN = "placeOfOrigin";
    private final static String DESCRIPTION = "description",IMAGE = "image",INGREDIENTS = "ingredients";


    public static Sandwich getJsonData(String json) {
        try {
            JSONObject getJsonMain = new JSONObject(json);
            JSONArray arrayIngredients = getJsonMain.getJSONArray(INGREDIENTS);
            List<String> ingredients = convertToListFromJsonArray(arrayIngredients);
            JSONObject name = getJsonMain.getJSONObject(NAME);
            String placeOfOrigin = getJsonMain.optString(PLACEOFORIGIN);
            String description = getJsonMain.getString(DESCRIPTION);
            String image = getJsonMain.getString(IMAGE);
            String mainName = name.getString(MAINNAME);
            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray(ALSOKNOWNAS);
            List<String> alsoKnownAs = convertToListFromJsonArray(JSONArrayAlsoKnownAs);
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> convertToListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
