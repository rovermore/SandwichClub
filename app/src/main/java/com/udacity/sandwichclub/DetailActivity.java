package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView originTv;
    TextView descriptionTv;
    TextView ingredientsTv;
    TextView alsoKnownTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);

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

    private void populateUI(Sandwich sw) {
        if(!sw.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(sw.getPlaceOfOrigin());
        }else {
            originTv.setText("Unknown");
        }
        descriptionTv.setText(sw.getDescription());
        List<String> ingredients = new ArrayList<>();
        ingredients = sw.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            if (i == ingredients.size() - 1) {
                ingredientsTv.append(ingredients.get(i));
            } else {
                ingredientsTv.append(ingredients.get(i) + ", ");
            }
        }
        List<String> alsoKnown = new ArrayList<>();
        alsoKnown = sw.getAlsoKnownAs();

        for (int i = 0; i < alsoKnown.size(); i++) {
            if (i == alsoKnown.size() - 1) {
                alsoKnownTv.append(alsoKnown.get(i));
            } else {
                alsoKnownTv.append(alsoKnown.get(i) + ", ");
            }
        }

    }
}
