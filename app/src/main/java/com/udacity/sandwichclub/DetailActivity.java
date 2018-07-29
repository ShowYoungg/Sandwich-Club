package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

//import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        ImageView ingredientsIv = findViewById(R.id.image_iv);
        ImageView scrollImage;
        ScrollView scrollView = findViewById(R.id.scroll_view_show);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);



        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView displayAlsoKnownAs = findViewById(R.id.also_known_tv);
        TextView alsoKnownAsLabel = findViewById(R.id.also_known_as);

        TextView displayIngredients = findViewById(R.id.ingredients_tv);
        TextView ingredientsLabel = findViewById(R.id.ingredients);

        TextView displayPlaceOfOrigin = findViewById(R.id.origin_tv);
        TextView placeOfOriginLabel = findViewById(R.id.place);

        TextView displayDescription = findViewById(R.id.description_tv);
        TextView descriptionLabel = findViewById(R.id.textView2);



        //this populates also known as
        StringBuilder stringBuilder = new StringBuilder();

        for( String s : sandwich.getAlsoKnownAs()) {
            stringBuilder.append(s + ", ");
        }

        int index = stringBuilder.length();
        Log.i("index length", "" + index);
        if (stringBuilder.length() > 0){

            stringBuilder.deleteCharAt(index-2);
            String alsoKnownAs = stringBuilder.toString();

            displayAlsoKnownAs.setText(alsoKnownAs);
        } else if (stringBuilder.length() == 0) {
            displayAlsoKnownAs.setVisibility(View.GONE);
            alsoKnownAsLabel.setVisibility(View.GONE);
        }

        //displays ingredients
        StringBuilder strBuilder = new StringBuilder();

        for( String s : sandwich.getIngredients()) {
            strBuilder.append( "\u2022" + s + "\n");
        }

        int ind = strBuilder.length();
        if (strBuilder.length()>0){
            strBuilder.deleteCharAt(ind-2);
            String ingredients = strBuilder.toString();
            displayIngredients.setText(ingredients);

            Log.i("testing", "" + displayIngredients);
        } else if (strBuilder.length() == 0) {
            displayAlsoKnownAs.setVisibility(View.GONE);
            alsoKnownAsLabel.setVisibility(View.GONE);
        }

        //displays place of origin
        displayPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        if (sandwich.getPlaceOfOrigin().length() == 0) {
            displayPlaceOfOrigin.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
        }

        //displays description
        displayDescription.setText(sandwich.getDescription());
        if (sandwich.getDescription().length() == 0) {
            displayDescription.setVisibility(View.GONE);
            descriptionLabel.setVisibility(View.GONE);
        }
    }
}
