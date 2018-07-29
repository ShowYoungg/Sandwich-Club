package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject mainJsonObject = new JSONObject(json);

            JSONObject name = mainJsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>(alsoKnownAsArray.length());

            for ( int i = 0; i < alsoKnownAsArray.length(); i++ ) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));

                Log.i("alsoKnownAs", "I am here" + alsoKnownAs);
            }

            String placeOfOrigin = mainJsonObject.optString("placeOfOrigin");

            String description = mainJsonObject.getString("description");

            String image = mainJsonObject.getString("image");

            JSONArray ingredientsArray = mainJsonObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>(ingredientsArray.length());

            for ( int i = 0; i < ingredientsArray.length(); i++ ) {
                Log.i("ingredients", "These are the ingredients" + ingredients);
                ingredients.add(ingredientsArray.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
