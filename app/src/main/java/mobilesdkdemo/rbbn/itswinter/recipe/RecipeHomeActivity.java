package mobilesdkdemo.rbbn.itswinter.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import mobilesdkdemo.rbbn.itswinter.R;

/**
 * This is the main search activity page for the Recipe app.
 */
public class RecipeHomeActivity extends AppCompatActivity {
    // we store the recipes here in this list
    public static ArrayList<Recipe> recipe_list = new ArrayList<Recipe>();

    //we use recipe_list_adapter to connect recipe list to ListView
    Recipe_List_Adapter recipe_list_adapter= new Recipe_List_Adapter();
    /**
     * Creates the main recipe home activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_home);

        String title = getString(R.string.recipe_title1);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button recipe_Button = findViewById(R.id.recipe_Button);
        EditText recipe_EditText = findViewById(R.id.recipe_EditText);
        ListView recipe_ListView = findViewById(R.id.recipe_ListView);
        ProgressBar progressBar=findViewById(R.id.recipe_ProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        boolean isTablet = findViewById(R.id.recipe_frame) != null; //check if the FrameLayout is loaded

        recipe_ListView.setAdapter(recipe_list_adapter);
        // refresh the listview
        recipe_list_adapter.notifyDataSetChanged();

        //Load search string saved from before in LastSearch.XML
        SharedPreferences prefs = getSharedPreferences("LastSearch", Context.MODE_PRIVATE);
        String savedString = prefs.getString("LASTSEARCH", "");
        // Get the search results from the last search
        if (savedString!="") {
            recipe_EditText.setText(savedString);
            RecipeQuery req= new RecipeQuery();
            recipe_list.clear();
            // do the search in the background and the results go into recipe_list
            req.execute("http://www.recipepuppy.com/api/?q="+recipe_EditText.getText().toString()+"&p=3&format=xml");
        }

        // when you click the recipe search button, do the recipe search
        recipe_Button.setOnClickListener((View v) -> {
            RecipeQuery req= new RecipeQuery();
            String toast1 = getString(R.string.toast1);

            // if the search string is empty, show a Toast message
            if (recipe_EditText.getText().toString().contentEquals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), toast1, Toast.LENGTH_SHORT);
                toast.show();
            } else {    //do the search
                recipe_list.clear();
                // do the search in the background and the results go into recipe_list
                req.execute("http://www.recipepuppy.com/api/?q=" + recipe_EditText.getText().toString() + "&p=3&format=xml");

                // wait a couple of seconds until the search is done, while updating the progress bar
                try {
                    Thread.sleep(1000); // wait 1000 milliseconds
                    progressBar.setMax(100);
                    progressBar.setProgress(50);
                    Thread.sleep(1000);
                    progressBar.setProgress(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                recipe_list_adapter.notifyDataSetChanged(); //refresh the ListView results

                String snackbar1 = getString(R.string.snackbar1);

                // if there are no results, show Snackbar message
                if (recipe_list.size() == 0) {
                    Snackbar snackbar = Snackbar.make(v, snackbar1, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

        // When we click on a recipe name, show the recipe contents on activity_recipe_page
        recipe_ListView.setOnItemClickListener(( parent, view, position,id) -> {
            Log.i("LAB-G", String.valueOf(isTablet));

            if (isTablet) {
                DetailFragment aFragment = new DetailFragment(  );
                Bundle b = new Bundle();
                b.putString("recipe_name", recipe_list.get(position).getRecipe_title());
                b.putString("ingredients", recipe_list.get(position).getRecipe_ingredients());
                b.putString("url", recipe_list.get(position).getRecipe_url());
                aFragment.setArguments(b);

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.recipe_frame, aFragment)
                        . commit();
            } else {
                Intent nextPage = new Intent(RecipeHomeActivity.this, activity_recipe_page.class);
                nextPage.putExtra("recipe_name", recipe_list.get(position).getRecipe_title());
                nextPage.putExtra("ingredients", recipe_list.get(position).getRecipe_ingredients());
                nextPage.putExtra("url", recipe_list.get(position).getRecipe_url());
                startActivity(nextPage);
            }
        });

        // Favorites button opens activity_recipe_favorites_list
        ImageButton favoritesListButton=findViewById(R.id.favoritesListButton);
        favoritesListButton.setOnClickListener(bt -> {
            Intent nextPage=new Intent(RecipeHomeActivity.this, activity_recipe_favorites_list.class);
            startActivity(nextPage);
        });

        //Help button
        Button help1=findViewById(R.id.recipe_help1);
        help1.setOnClickListener(bt -> {            //show alert
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.help);
            alertDialogBuilder.setMessage(R.string.r_help_message3);
            alertDialogBuilder.create().show();
        });
    }

    /**
     * When the app pauses, this method saves the Last Recipe search term into the file LastSearch.XML
     * with the keyword LASTSEARCH. It takes what is in the search EditText box and saves it there.
     */
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("LastSearch", Context.MODE_PRIVATE);
        EditText typeField = findViewById(R.id.recipe_EditText);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LASTSEARCH", typeField.getText().toString());
        editor.commit();
    }

    /**
     *  this method performs the query to the recipepuppy.com API in the background
     *  it is an AsyncTask method.
     */
    private class RecipeQuery extends AsyncTask<String, Integer, String>{
        Recipe recipe=new Recipe();

        @Override
        protected String doInBackground(String... strings) {
            //create a URL object of what server to contact (from example code)
            URL url = null;
            try {
                url = new URL(strings[0]);

                //open the connection for the XML API
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //wait for data:
                InputStream response = urlConnection.getInputStream();
                // Code to search through  XML data from API
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8"); //response is data from the server
                publishProgress(50); // update progress bar
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                // while loop that goes through the XML code searching for
                // Recipe TITLE, HREF & INGREDIENTS tags
                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        // Search for TITLE of recipe
                        if(xpp.getName().equals("title"))
                        {
                            xpp.next(); //move the pointer from the opening tag to the TEXT event
                            recipe.setRecipe_title(xpp.getText());
                            //Log.i("TITLE", recipe.getRecipe_title());
                        } else
                            //If you get here, then you are pointing at a start tag
                            if(xpp.getName().equals("href"))
                            {
                                xpp.next(); //move the pointer from the opening tag to the TEXT event
                                recipe.setRecipe_url(xpp.getText()); // this will return  20
                                //Log.i("URL",  recipe.getRecipe_url());
                            } else
                                //If you get here, then you are pointing at a start tag
                                if(xpp.getName().equals("ingredients"))
                                {
                                    xpp.next(); //move the pointer from the opening tag to the TEXT event
                                    recipe.setRecipe_ingredients(xpp.getText()); // this will return  20
                                    //Log.i("INGREDIENTS", recipe.getRecipe_ingredients());
                                    // add recipe object to recipe list ArrayList<Recipe>
                                    recipe_list.add(recipe);
                                    recipe=new Recipe(); // Start with a new Recipe to add
                                }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public void onPostExecute(String s)
        {
            recipe_list_adapter.notifyDataSetChanged();
        }
    }

    /**
     * This method is necessary for the back button to work.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                RecipeHomeActivity.this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This class connects the recipe_list to ListView
     * and extends BaseAdapter
     */
    public class Recipe_List_Adapter extends BaseAdapter {

        /**
         * This method returns the size of the recipe list
         * @return
         */
        @Override
        public int getCount() {
            return recipe_list.size();
        }

        /**
         * This method returns an object when given a position
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return recipe_list.get(position);
        }

        /**
         * We use the position as the ID
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * This method returns each TextView we want to put in the ListView
         * @param position is the number of the row of the ListView
         * @param convertView
         * @param parent
         * @return the TextView of the Recipe Title at the position requested
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.activity_recipe_listview_item, parent, false);
            TextView tv = newView.findViewById(R.id.recipe_TextView);
            tv.setText(recipe_list.get(position).getRecipe_title());
            return newView;
        }
    }

    /**
     *  this class/type holds a single recipe
     *  A recipe has 3 strings: a title, ingredients & URL.
     */
    public static class Recipe {
        String recipe_title;
        String recipe_url;
        String recipe_ingredients;

        public String getRecipe_title() {
            return recipe_title;
        }

        public void setRecipe_title(String recipe_title) {
            this.recipe_title = recipe_title;
        }

        public String getRecipe_url() {
            return recipe_url;
        }

        public void setRecipe_url(String recipe_url) {
            this.recipe_url = recipe_url;
        }

        public String getRecipe_ingredients() {
            return recipe_ingredients;
        }

        public void setRecipe_ingredients(String recipe_ingredients) {
            this.recipe_ingredients = recipe_ingredients;
        }
    }
}


