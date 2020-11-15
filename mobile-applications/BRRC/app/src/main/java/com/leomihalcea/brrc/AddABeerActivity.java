package com.leomihalcea.brrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.leomihalcea.brrc.model.BeerLocation;

public class AddABeerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //todo add private
    EditText beerNameEditText;
    Spinner beerTypeSpinner;
    Spinner pubNameSpinner;
    EditText priceEditText;

    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_beer);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        beerNameEditText = findViewById(R.id.editBeerName);

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

        priceEditText = findViewById(R.id.editPrice);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.getInstance().addBeerLocation(new BeerLocation(
                        Repository.getCurrentId(),
                        beerNameEditText.getText().toString(),
                        beerTypeSpinner.getSelectedItem().toString(),
                        pubNameSpinner.getSelectedItem().toString(),
                        Integer.parseInt(priceEditText.getText().toString())
                ));
                Toast.makeText(getApplicationContext(), "Beer added", Toast.LENGTH_LONG).show();
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