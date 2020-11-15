package com.leomihalcea.brrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.leomihalcea.brrc.model.BeerLocation;

public class EditBeerLocationActivity extends AppCompatActivity {

    BeerLocation beerLocation;
    private TextView beerName;
    private TextView beerType;
    private TextView pubName;
    private TextView price;

    EditText beerNameEditText;
    Spinner beerTypeSpinner;
    Spinner pubNameSpinner;
    EditText priceEditText;

    Button updateButton;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_beer_location);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey("beerLocation")) {
            beerLocation = intent.getParcelableExtra("beerLocation");
        }

        beerName = findViewById(R.id.beerLocationName);
        beerType = findViewById(R.id.beerType);
        pubName = findViewById(R.id.pubName);
        price = findViewById(R.id.beerPrice);

        beerNameEditText = findViewById(R.id.editBeerName);
        beerNameEditText.setText(beerLocation.getBeerName());

        beerTypeSpinner = findViewById(R.id.beerTypeSpinner);
        ArrayAdapter<String> spinnerBeerTypeAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.beerTypes));
        beerTypeSpinner.setAdapter(spinnerBeerTypeAdapter);

        pubNameSpinner = findViewById(R.id.pubNameSpinner);
        ArrayAdapter<String> pubNameAdapter = new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.pubNames));
        pubNameSpinner.setAdapter(pubNameAdapter);

        beerName.setText(beerLocation.getBeerName());
        beerType.setText(beerLocation.getBeerType());
        pubName.setText(beerLocation.getPubName());
        price.setText(String.valueOf(beerLocation.getPrice()) + " Ron");

        priceEditText = findViewById(R.id.editPrice);
        priceEditText.setText(String.valueOf(beerLocation.getPrice()));

        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.getInstance().updateBeerLocation(new BeerLocation(
                        beerLocation.getId(),
                        beerNameEditText.getText().toString(),
                        beerTypeSpinner.getSelectedItem().toString(),
                        pubNameSpinner.getSelectedItem().toString(),
                        Integer.parseInt(priceEditText.getText().toString())
                ));
                Toast.makeText(getApplicationContext(), "Beer updated", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBeerLocationActivity.this, DeleteBeerLocationActivity.class);
                intent.putExtra("beerLocation", beerLocation);
                EditBeerLocationActivity.this.startActivity(intent);
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