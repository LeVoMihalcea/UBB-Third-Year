package com.leomihalcea.brrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leomihalcea.brrc.model.BeerLocation;

public class DeleteBeerLocationActivity extends AppCompatActivity {

    BeerLocation beerLocation;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_beer_location);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey("beerLocation")) {
            beerLocation = intent.getParcelableExtra("beerLocation");
        }

        TextView beerName = findViewById(R.id.beerLocationName);
        TextView beerType = findViewById(R.id.beerType);
        TextView pubName = findViewById(R.id.pubName);
        TextView price = findViewById(R.id.beerPrice);

        beerName.setText(beerLocation.getBeerName());
        beerType.setText(beerLocation.getBeerType());
        pubName.setText(beerLocation.getPubName());
        price.setText(String.valueOf(beerLocation.getPrice()) + " Ron");


        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.getInstance().deleteBeerLocation(beerLocation);
                Toast.makeText(getApplicationContext(), "Beer deleted", Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}