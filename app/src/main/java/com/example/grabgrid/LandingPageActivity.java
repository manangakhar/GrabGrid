package com.example.grabgrid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.grabgrid.Constants.Constants;
import com.example.grabgrid.model.Transaction;
import com.example.grabgrid.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class LandingPageActivity extends AppCompatActivity {

    private EditText amount;
    private MaterialSpinner serviceSpinner;
    private MaterialSpinner coutrySpinner;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.grab);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        amount = (EditText)findViewById(R.id.amount);

        user = (User) getIntent().getSerializableExtra("user");
        Toast.makeText(getApplicationContext(), user.getUsername() + " logged in", Toast.LENGTH_SHORT).show();

        coutrySpinner = (MaterialSpinner) findViewById(R.id.coutrySpinner);
        coutrySpinner.setItems("Malaysia", "Cambodia", "Indonesia", "Myanmar", "the Philippines", "Singapore", "Thailand", "Vietnam");
        coutrySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        serviceSpinner = (MaterialSpinner) findViewById(R.id.serviceSpinner);
        serviceSpinner.setItems("Transport", "Food", "Pay", "Tickets");
        serviceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        final Button button = (Button) findViewById(R.id.payBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = amount.getText().toString();

                if(amountStr==null || "".equalsIgnoreCase(amountStr)){
                    Toast.makeText(getApplicationContext(), "Amount Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer amountInt = Integer.parseInt(amountStr);
                if(amountInt<2){
                    Toast.makeText(getApplicationContext(), "Amount Cannot be less than 2", Toast.LENGTH_SHORT).show();
                    return;
                }

                String service = serviceSpinner.getItems().get(serviceSpinner.getSelectedIndex()).toString();
                Transaction txn = new Transaction();
                txn.setUserId(user.getUserId());
                txn.setService(service);
                txn.setAmount(Integer.parseInt(amountStr));
                Constants.db.addTransaction(txn);

                user.setStepsRemaining(user.getStepsRemaining()+1);
                Constants.db.updateUser(user);

                Intent intent = new Intent(getApplicationContext(), GrabGridActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("transaction", txn);
                startActivity(intent);
            }
        });


    }
}
