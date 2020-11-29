package mobilesdkdemo.rbbn.itswinter.recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This is for the listview page you get when you click on the Favorites button
 */
public class activity_recipe_favorites_list extends AppCompatActivity {

    /**
     * This method creates the Recipe results list
     * we use favorites_list_adapter to connect recipe list to ListView
     * and use JSON to get favorites from SharedPreferences.
     * We open a detail page when clicking on a favorite.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_favorites_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.r_title2);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ListView favoritesListView = findViewById(R.id.favorites_list_view);

        //we use favorites_list_adapter to connect recipe list to ListView
        FavoriteListAdapter favoriteListAdapter = new FavoriteListAdapter();
        favoritesListView.setAdapter(favoriteListAdapter);

        // use JSON to get favorites
        SharedPreferences prefs = getSharedPreferences("FavoriteFile", Context.MODE_PRIVATE);
        ;
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();

        String json = prefs.getString("FAVORITES", "");
        Type type = new TypeToken<ArrayList<RecipeHomeActivity.Recipe>>() {}.getType();
        favoriteListAdapter.favorites = gson.fromJson(json, type);
        favoriteListAdapter.notifyDataSetChanged();

        // open detail page when clicking on favorite
        favoritesListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent nextPage = new Intent(activity_recipe_favorites_list.this, activity_recipe_page.class);
            nextPage.putExtra("recipe_name", favoriteListAdapter.favorites.get(position).getRecipe_title());
            nextPage.putExtra("ingredients", favoriteListAdapter.favorites.get(position).getRecipe_ingredients());
            nextPage.putExtra("url", favoriteListAdapter.favorites.get(position).getRecipe_url());
            startActivity(nextPage);
        });

        //Help button
        Button help1 = findViewById(R.id.recipe_help2);
        help1.setOnClickListener(bt -> {            //show alert
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.help);
            alertDialogBuilder.setMessage(R.string.r_help_message2);
            alertDialogBuilder.create().show();
        });
    }
    /**
     * This class connects the ListView to the Java code using the BaseAdapter class
     */
    class FavoriteListAdapter extends BaseAdapter {
        ArrayList<RecipeHomeActivity.Recipe> favorites = new ArrayList<RecipeHomeActivity.Recipe>();

        @Override
        public int getCount() {
            return favorites.size();
        }

        @Override
        public Object getItem(int position) {
            return favorites.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.activity_recipe_listview_item, parent, false);
            TextView tv = newView.findViewById(R.id.recipe_TextView);
            tv.setText(favorites.get(position).getRecipe_title());
            return newView;
        }
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

