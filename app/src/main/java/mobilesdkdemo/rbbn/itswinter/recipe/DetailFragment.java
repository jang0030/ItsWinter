package mobilesdkdemo.rbbn.itswinter.recipe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This is a helper class for dealing with the Fragment frame in the tablet view
 */
public class DetailFragment extends Fragment {

    private AppCompatActivity parentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView=inflater.inflate(R.layout.activity_recipe_page, container, false);
        TextView recipe_name_page=inflatedView.findViewById(R.id.recipe_name_page);
        TextView recipe_ingredients_page=inflatedView.findViewById(R.id.recipe_ingredients_page);
        TextView recipe_url_page=inflatedView.findViewById(R.id.recipe_url_page);
        Bundle b=getArguments();
        recipe_name_page.setText(b.getString("recipe_name"));
        recipe_ingredients_page.setText("Ingredients: " + b.getString( "ingredients"));
        recipe_url_page.setText(b.getString("url"));

        // open web page if you click on url
        recipe_url_page.setOnClickListener(bt -> {
            Uri uriUrl = Uri.parse(b.getString("url"));
            Intent launchBrowserIntent = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowserIntent);
        });
        Button delete_button=inflatedView.findViewById(R.id.recipe_delete_button);
        delete_button.setOnClickListener(bt -> {
            SharedPreferences prefs=getContext().getSharedPreferences("FavoriteFile", Context.MODE_PRIVATE);;
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String json = prefs.getString("FAVORITES", "");
            ArrayList <RecipeHomeActivity.Recipe> favoritesList = gson.fromJson(json,  new TypeToken<ArrayList<RecipeHomeActivity.Recipe>>() {}.getType());

            for (int i=0; i<favoritesList.size(); i++)
                if (favoritesList.get(i).getRecipe_title().equals(b.getString( "recipe_name"))) {
                    favoritesList.remove(i);
                    Log.i("LAB-G", "REMOVING "+i);
                    i--;
                }
            json = gson.toJson(favoritesList);
            editor.remove("FAVORITES");
            editor.putString("FAVORITES", json);
            editor.commit();

            Toast toast = Toast.makeText(getContext().getApplicationContext(), "Recipe deleted from Favorites.", Toast.LENGTH_SHORT);
            toast.show();
        });

        Button favorite_button=inflatedView.findViewById(R.id.favorite_button);
        favorite_button.setOnClickListener(bt -> {
            // use JSON to save favorites
            SharedPreferences prefs=getContext().getSharedPreferences("FavoriteFile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String json = prefs.getString("FAVORITES", "");
            ArrayList<RecipeHomeActivity.Recipe> favoritesList = gson.fromJson(json, ArrayList.class);
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Recipe");
            alertDialogBuilder.setMessage("Favorite Saved");
            alertDialogBuilder.create().show();
        });

        return inflatedView;
    }

}

