package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    private TextView KnownValue;
    private TextView KnownLabel;
    private TextView placeofOriginValue;
    private TextView placeofOriginLabel;
    private TextView DescriptionValue;
    private TextView IngredientsValue;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.imageView);
        KnownValue = findViewById(R.id.KnownValue);
        KnownLabel = findViewById(R.id.KnownLabel);
        placeofOriginValue = findViewById(R.id.placeofOriginValue);
        placeofOriginLabel = findViewById(R.id.placeofOriginLabel);
        DescriptionValue = findViewById(R.id.DescriptionValue);
        IngredientsValue = findViewById(R.id.IngredientsValue);


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
        Sandwich sandwich = JsonUtils.getJsonData(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }




        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        // set Text to alsoKnownTv
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));
            }
            KnownValue.setText(stringBuilder.toString());
        } else {
            KnownValue.setVisibility(View.GONE);
            KnownLabel.setVisibility(View.GONE);
        }

        // set Text to originTv
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeofOriginValue.setVisibility(View.GONE);
            placeofOriginLabel.setVisibility(View.GONE);
        } else {
            placeofOriginValue.setText(sandwich.getPlaceOfOrigin());
        }

        // set Text to descriptionTv
        DescriptionValue.setText(sandwich.getDescription());

        // set Text to ingredientTv
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u2022");
            stringBuilder.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                stringBuilder.append("\n");
                stringBuilder.append("\u2022");
                stringBuilder.append(sandwich.getIngredients().get(i));
            }
            IngredientsValue.setText(stringBuilder.toString());
        }

        // display the image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);
    }
}
