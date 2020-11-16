package mobilesdkdemo.rbbn.itswinter.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
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

public class RecipeHomeActivity extends AppCompatActivity {
    public static ArrayList<Recipe> recipe_list = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_home);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Recipe Search");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button recipe_Button = findViewById(R.id.recipe_Button);
        EditText recipe_EditText = findViewById(R.id.recipe_EditText);
        ListView recipe_ListView = findViewById(R.id.recipe_ListView);
        ProgressBar progressBar=findViewById(R.id.recipe_ProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        Recipe_List_Adapter recipe_list_adapter= new Recipe_List_Adapter();
        recipe_ListView.setAdapter(recipe_list_adapter);
        recipe_list_adapter.notifyDataSetChanged();

        recipe_Button.setOnClickListener((View v) -> {
            RecipeQuery req= new RecipeQuery();
            if (recipe_EditText.getText().toString().contentEquals("")) {
                Context context = getApplicationContext();
                CharSequence text = "Please enter recipe keyword.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }
            recipe_list.clear();
            req.execute("http://www.recipepuppy.com/api/?q="+recipe_EditText.getText().toString()+"&p=3&format=xml");
            try {
                Thread.sleep(1000);
                progressBar.setMax(100);
                progressBar.setProgress(50);
                recipe_list_adapter.notifyDataSetChanged();
                Thread.sleep(1000);
                progressBar.setProgress(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recipe_list_adapter.notifyDataSetChanged();

            if (recipe_list.size()==0) {
                Snackbar snackbar = Snackbar.make(v,"No results",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        recipe_ListView.setOnItemClickListener(( parent, view, position,id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
           alertDialogBuilder.setTitle(recipe_list.get(position).getRecipe_title());
            alertDialogBuilder.setMessage("Ingredients: " + recipe_list.get(position).getRecipe_ingredients()
                    + "\n URL: " + recipe_list.get(position).getRecipe_url());
            alertDialogBuilder.create().show();
        });
    }

    private class RecipeQuery extends AsyncTask<String, Integer, String>{
        Recipe recipe=new Recipe();

        @Override
        protected String doInBackground(String... strings) {
                //create a URL object of what server to contact:
            URL url = null;
            try {
                url = new URL(strings[0]);

            //open the connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //wait for data:
            InputStream response = urlConnection.getInputStream();
            //From part 3: slide 19
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( response  , "UTF-8"); //response is data from the server
            publishProgress(50);
            //From part 3, slide 20
            String parameter = null;

            int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                if(eventType == XmlPullParser.START_TAG)
                {
                    //If you get here, then you are pointing at a start tag
                    if(xpp.getName().equals("title"))
                    {
                        xpp.next(); //move the pointer from the opening tag to the TEXT event
                        recipe.setRecipe_title(xpp.getText()); // this will return  20
                        Log.i("TITLE", recipe.getRecipe_title());
                    } else
                    //If you get here, then you are pointing at a start tag
                    if(xpp.getName().equals("href"))
                    {
                        xpp.next(); //move the pointer from the opening tag to the TEXT event
                        recipe.setRecipe_url(xpp.getText()); // this will return  20
                        Log.i("URL",  recipe.getRecipe_url());
                    } else
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("ingredients"))
                        {
                            xpp.next(); //move the pointer from the opening tag to the TEXT event
                            recipe.setRecipe_ingredients(xpp.getText()); // this will return  20
                            Log.i("INGREDIENTS", recipe.getRecipe_ingredients());
                            // add recipe to list
                            recipe_list.add(recipe);
                            recipe=new Recipe();
                        }
                }
                eventType = xpp.next(); //move to the next xml event and store it in a variable
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

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

    public class Recipe_List_Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return recipe_list.size();
        }

        @Override
        public Object getItem(int position) {
            return recipe_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            for (int i=0;i<10;i++) {
                Log.i("ARRAY", recipe_list.get(i).getRecipe_title());
            }
            LayoutInflater inflater = getLayoutInflater();
                View newView = inflater.inflate(R.layout.activity_recipe_listview_item, parent, false);
                TextView tv = newView.findViewById(R.id.recipe_TextView);
                tv.setText(recipe_list.get(position).getRecipe_title());
                return newView;
         }
    }

    private class Recipe {
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