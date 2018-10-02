package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        JSONObject jsonObject = null;

        if (json != null) {
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject name = jsonObject.optJSONObject("name");
            String mainName = name.optString("mainName");
            JSONArray alsoKnownAsJsonArray = name.optJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                String str = String.valueOf(alsoKnownAsJsonArray.optString(i));
                if (str != null) {
                    alsoKnownAs.add(str);
                }
            }
            String placeOfOrigin = jsonObject.optString("placeOfOrigin");
            String description = jsonObject.optString("description");
            String image = jsonObject.optString("image");
            JSONArray ingredientsJsonArray = jsonObject.optJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                String str = String.valueOf(ingredientsJsonArray.opt(i));
                if (str != null) {
                    ingredients.add(str);
                }
            }

            Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

            return sandwich;
        } else {
            return null;
        }

    }
}
