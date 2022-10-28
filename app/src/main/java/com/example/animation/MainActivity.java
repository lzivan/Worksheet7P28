package com.example.animation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    CustomAutoCompleteView myAutoComplete;

    // adapter for auto-complete
    ArrayAdapter<String> myAdapter;

    // for database operations
    DatabaseManager databaseH;

    // just to add some initial value
    String[] item = new String[] {"Please search..."};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







            try{

                // instantiate database handler
                databaseH = new DatabaseManager(MainActivity.this);

                // put sample data to database
                insertSampleData();

                // autocompletetextview is in activity_main.xml
                myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.myautocomplete);

                // add the listener so it will tries to suggest while the user types
                myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));

                // set our adapter
                myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
                myAutoComplete.setAdapter(myAdapter);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }




        }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.animation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_delete:

                Toast.makeText(this, "item1 selected",Toast.LENGTH_SHORT).show();
                Log.w("MainActivity", "宝天意sb");

                Intent deleteIntent
                        = new Intent( this, DeleteActivity.class );
                this.startActivity( deleteIntent );
                return true;

            case R.id.action_update:


                Intent updateIntent
                        = new Intent( this, UpdateActivity.class );
                this.startActivity( updateIntent );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertSampleData(){



        databaseH.create( new Email(1,"tianyi@bu.edu") );
        databaseH.create( new Email(2,"lzivan@bu.edu") );
        databaseH.create( new Email(3,"gcj@bu.edu") );
        databaseH.create( new Email(4,"lycc@bu.edu") );
        databaseH.create( new Email(5,"rec@bu.edu") );

    }

    public String[] getItemsFromDb(String searchTerm){

        // add items on the array dynamically
        ArrayList<Email> emails = databaseH.read(searchTerm);
        int rowCount = emails.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (Email email : emails) {

            item[x] = email.objectName;
            Log.w("MainActivity", "Item is: " + item);
            x++;
        }

        return item;
    }


}