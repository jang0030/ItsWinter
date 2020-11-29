package mobilesdkdemo.rbbn.itswinter.recipe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This is the recipe details page when you click on a recipe after searching
 * We open the URL web page if you click the URL
 * When clicking on the "Save" button, we load the FAVORITES into an Arraylist of Recipes,
 * Add a new recipe, and then save the new list to FAVORITES. Delete works in a similar way.
 * We use JSON since we have an ArrayList<Recipe> and SharedPreferences only can save Strings.
 */
public class activity_recipe_page extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Recipe Detail Page");
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
            alertDialogBuilder.setTitle("Recipe");
            alertDialogBuilder.setMessage("Favorite Saved");
            alertDialogBuilder.create().show();
        });

        Button delete_button=findViewById(R.id.recipe_delete_button);
        delete_button.setOnClickListener(bt -> {
            SharedPreferences prefs=getSharedPreferences("FavoriteFile", Context.MODE_PRIVATE);;
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String json = prefs.getString("FAVORITES", "");
            ArrayList <RecipeHomeActivity.Recipe> favoritesList = gson.fromJson(json,  new TypeToken<ArrayList<RecipeHomeActivity.Recipe>>() {}.getType());

            for (int i=0; i<favoritesList.size(); i++)
                if (favoritesList.get(i).getRecipe_title().equals(intent.getStringExtra("recipe_name"))) {
                    favoritesList.remove(i);
                    Log.i("LAB-G", "REMOVING "+i);
                    i--;
                }
            json = gson.toJson(favoritesList);
            editor.remove("FAVORITES");
            editor.putString("FAVORITES", json);
            editor.commit();

            Toast toast = Toast.makeText(getApplicationContext(), "Recipe deleted from Favorites.", Toast.LENGTH_SHORT);
            toast.show();
        });

        //Help button
        Button help=findViewById(R.id.recipe_help3);
        help.setOnClickListener(bt -> {            //show alert
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Help");
            alertDialogBuilder.setMessage("Click on Save to Favorites to add to the Saved Favorites list and Delete from Favorites to remove.");
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

