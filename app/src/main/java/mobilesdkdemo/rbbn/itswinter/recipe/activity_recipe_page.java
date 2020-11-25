package mobilesdkdemo.rbbn.itswinter.recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This is the recipe details page when you click on a recipe after searching
 * We open the URL web page if you click the URL
 * When clicking on the "Save" button, we load the FAVORITES into an arraylist of Recipes,
 * Add a new recipe, and then save the new list to FAVORITES,
 * We use JSON since it's an ArrayList<Recipe> and JSON only can save Strings.
 */
public class activity_recipe_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        ActionBar actionBar=getSupportActionBar();
        String detailPageTitle = getString(R.string.detailPageTitle);
        actionBar.setTitle(detailPageTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView recipe_name_page=findViewById(R.id.recipe_name_page);
        TextView recipe_ingredients_page=findViewById(R.id.recipe_ingredients_page);
        TextView recipe_url_page=findViewById(R.id.recipe_url_page);
        Intent intent=getIntent();

        recipe_name_page.setText(intent.getStringExtra("recipe_name"));
        recipe_ingredients_page.setText("Ingredients: " + intent.getStringExtra( "ingredients"));
        recipe_url_page.setText(intent.getStringExtra("url"));

        // open web page if you click on url
        recipe_url_page.setOnClickListener(bt -> {
            Uri uriUrl = Uri.parse(intent.getStringExtra("url"));
            Intent launchBrowserIntent = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowserIntent);
        });

        Button favorite_button=findViewById(R.id.favorite_button);
        // When clicking on the "Save" button, we save to SharedPreferences using JSON, since it's an ArrayList of Recipes and JSON only can save Strings.
        favorite_button.setOnClickListener(bt -> {
            // use JSON to save favorites
            SharedPreferences prefs=getSharedPreferences("FavoriteFile", Context.MODE_PRIVATE);;
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String json = prefs.getString("FAVORITES", "");
            ArrayList <RecipeHomeActivity.Recipe> favoritesList = gson.fromJson(json, ArrayList.class);
            if (favoritesList==null) favoritesList= new ArrayList<RecipeHomeActivity.Recipe>();
            RecipeHomeActivity.Recipe n=new RecipeHomeActivity.Recipe();
            n.setRecipe_title(recipe_name_page.getText().toString());
            n.setRecipe_ingredients(recipe_ingredients_page.getText().toString());
            n.setRecipe_url(recipe_url_page.getText().toString());
            favoritesList.add(n);
            json = gson.toJson(favoritesList);
            editor.putString("FAVORITES", json);
            editor.commit();

            //show alert
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            String alert1 = getString(R.string.alert1);
            alertDialogBuilder.setTitle(alert1);
            String alert2 = getString(R.string.alert2);
            alertDialogBuilder.setMessage(alert2);
            alertDialogBuilder.create().show();
        });
    }

    /**
     * This makes the return/back button function
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

