package com.example.grabgrid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.grabgrid.Constants.Constants;
import com.example.grabgrid.Entities.Coordinate;
import com.example.grabgrid.Model.Transaction;
import com.example.grabgrid.Model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

public class LandingPageActivity extends AppCompatActivity {

    private EditText amount;
    private MaterialSpinner serviceSpinner;
    private MaterialSpinner coutrySpinner;
    public static User user;
    private TextView chiLvl;
    private TextView welcomeUser;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.grab);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        builder = new AlertDialog.Builder(this);

        user = (User) getIntent().getSerializableExtra("user");
        Toast.makeText(getApplicationContext(), user.getUsername() + " logged in", Toast.LENGTH_SHORT).show();

        welcomeUser = (TextView) findViewById(R.id.welcome);
        welcomeUser.setText(welcomeUser.getText()+" "+user.getUsername());

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        chiLvl = (TextView) findViewById(R.id.chiLvl);
        chiLvl.setText(chiLvl.getText()+" "+(int)user.getChiLvl()/100);
        progressStatus = user.getChiLvl()%100;
        progressBar.setProgress(user.getChiLvl()%100);


        amount = (EditText)findViewById(R.id.amount);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), GrabGridActivity.class);
                startActivity(intent);

            }
        });


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
                int chiYearned = 0;

                if(amountStr==null || "".equalsIgnoreCase(amountStr)){
                    Toast.makeText(getApplicationContext(), "Amount Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer amountInt = Integer.parseInt(amountStr);
                if(amountInt<2){
                    Toast.makeText(getApplicationContext(), "Amount Cannot be less than 2", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(amountInt==300){
                    chiYearned=50;
                    dialogbox(amountInt, chiYearned, amountStr);
                }else {
                    if(amountInt>200){
                        chiYearned=20;
                    }else{
                        chiYearned=10;
                    }
                    int currentLvlPoints = user.getChiLvl() % 100;
                    startProgressChange(currentLvlPoints, chiYearned);
                    chiLvl.setText("Chi Lvl-" + String.valueOf(((int) (user.getChiLvl() + chiYearned) / 100)));

                    String service = serviceSpinner.getItems().get(serviceSpinner.getSelectedIndex()).toString();
                    Transaction txn = new Transaction();
                    txn.setUserId(user.getUserId());
                    txn.setService(service);
                    txn.setAmount(Integer.parseInt(amountStr));
                    Constants.db.addTransaction(txn);

                    user.setStepsRemaining(user.getStepsRemaining() + 1);
                    user.setChiLvl(user.getChiLvl() + chiYearned);
                    Constants.db.updateUser(user);

                    Toast.makeText(getApplicationContext(), "Payment Made", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), GrabGridActivity.class);
                    intent.putExtra("transaction", txn);
                    intent.putExtra("chiYearned", chiYearned);
                    startActivity(intent);
                }
            }
        });


    }

    public void startProgressChange(final int chiLvl, final int chiYearned){

        new Thread(new Runnable() {
            public void run() {
                int sum = chiLvl+chiYearned;
                while (progressStatus <= sum) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            //textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });

                    if(progressBar.getProgress()>=100){
                        progressBar.setProgress(0);
                        progressStatus = 0;
                        sum=sum%100;
                    }

                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void dialogbox(final int amountInt, final int chiYearned, final String amountStr){

        //builder.setMessage("Yay! You will receive more points due to consecutive transactions!") .setTitle("Rewards");

        //Setting message manually and performing action on button click
        builder.setMessage("Yay! You will receive more points due to consecutive transactions!")
                .setTitle("Rewards")
                .setCancelable(true)
                .setPositiveButton("Wooohhoo!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int currentLvlPoints = user.getChiLvl() % 100;
                        startProgressChange(currentLvlPoints, chiYearned);
                        chiLvl.setText("Chi Lvl-" + String.valueOf(((int) (user.getChiLvl() + chiYearned) / 100)));

                        String service = serviceSpinner.getItems().get(serviceSpinner.getSelectedIndex()).toString();
                        Transaction txn = new Transaction();
                        txn.setUserId(user.getUserId());
                        txn.setService(service);
                        txn.setAmount(Integer.parseInt(amountStr));
                        Constants.db.addTransaction(txn);

                        user.setStepsRemaining(user.getStepsRemaining() + 1);
                        user.setChiLvl(user.getChiLvl() + chiYearned);
                        Constants.db.updateUser(user);

                        Toast.makeText(getApplicationContext(), "Payment Made", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), GrabGridActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("transaction", txn);
                        intent.putExtra("chiYearned", chiYearned);
                        startActivity(intent);
                    }
                }).show();

    }
}
