package com.example.animation;

import android.graphics.Point;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
  DatabaseManager dbManager;

  public void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    dbManager = new DatabaseManager( this );
    updateView( );
  }

  // Build a View dynamically with all the candies
  public void updateView( ) {
    ArrayList<Email> candies = dbManager.selectAll( );
    if( candies.size( ) > 0 ) {
      // create ScrollView and GridLayout
      ScrollView scrollView = new ScrollView( this );
      GridLayout grid = new GridLayout( this );
      grid.setRowCount( candies.size( ) );
      grid.setColumnCount( 4 );

      // create arrays of components
      TextView [] ids = new TextView[candies.size( )];
      EditText [] namesAndPrices = new EditText[candies.size( )];
      Button [] buttons = new Button[candies.size( )];
      ButtonHandler bh = new ButtonHandler( );

      // retrieve width of screen
      Point size = new Point( );
      getWindowManager( ).getDefaultDisplay( ).getSize( size );
      int width = size.x;

      int i = 0;

      for ( Email candy : candies ) {
        // create the TextView for the candy's id
        ids[i] = new TextView( this );
        ids[i].setGravity( Gravity.CENTER );
        ids[i].setText( "" + candy.getId( ) );

        // create the two EditTexts for the candy's name and price
        namesAndPrices[i] = new EditText( this );

        namesAndPrices[i].setText( candy.getName( ) );


        namesAndPrices[i].setId( 10 * candy.getId( ) );


        // create the button
        buttons[i] = new Button( this );
        buttons[i].setText( "Update" );
        buttons[i].setId( candy.getId( ) );

        // set up event handling
        buttons[i].setOnClickListener( bh );

        TextView blank = new TextView(this);
        // add the elements to grid
        grid.addView( ids[i], width / 10,
                      ViewGroup.LayoutParams.WRAP_CONTENT );
        grid.addView( namesAndPrices[i], ( int ) ( width * .4 ),
                      ViewGroup.LayoutParams.WRAP_CONTENT );
        grid.addView( blank , ( int ) ( width * .15 ),
                ViewGroup.LayoutParams.WRAP_CONTENT );
        grid.addView( buttons[i], ( int ) ( width * .35 ),
                      ViewGroup.LayoutParams.WRAP_CONTENT );

        i++;
      }
      scrollView.addView( grid );
      setContentView( scrollView );
    }
  }

  private class ButtonHandler implements View.OnClickListener {
    public void onClick( View v ) {
      // retrieve name and price of the candy
      int candyId = v.getId( );
      EditText nameET = ( EditText ) findViewById( 10 * candyId );
      String name = nameET.getText( ).toString( );


      // update candy in database
      try {

        dbManager.updateById( candyId, name );
        Toast.makeText( UpdateActivity.this, "Email updated",
          Toast.LENGTH_SHORT ).show( );

        // update screen
        updateView( );
      } catch( NumberFormatException nfe ) {
        Toast.makeText( UpdateActivity.this,
                        "Price error", Toast.LENGTH_LONG ).show( );
      }
    }
  }
}
