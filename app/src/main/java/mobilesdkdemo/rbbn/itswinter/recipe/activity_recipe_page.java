package mobilesdkdemo.rbbn.itswinter.recipe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import mobilesdkdemo.rbbn.itswinter.R;

public class activity_recipe_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        TextView recipe_name_page=findViewById(R.id.recipe_name_page);
        TextView recipe_ingredients_page=findViewById(R.id.recipe_ingredients_page);
        TextView recipe_url_page=findViewById(R.id.recipe_url_page);
        Intent intent=getIntent();

        recipe_name_page.setText(intent.getStringExtra("recipe_name"));
        recipe_ingredients_page.setText(intent.getStringExtra("ingredients"));
        recipe_url_page.setText(intent.getStringExtra("url"));

        Button favorite_button=findViewById(R.id.favorite_button);

        favorite_button.setOnClickListener(bt -> {
            SharedPreferences prefs=getSharedPreferences("Favorites", Context.MODE_PRIVATE);;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("favorite", recipe_name_page.getText().toString());
            editor.commit();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            //alertDialogBuilder.setTitle("");
            alertDialogBuilder.setMessage("Favorite Saved");
            alertDialogBuilder.create().show();
        });

    }
}

