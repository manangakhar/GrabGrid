package com.example.grabgrid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.grabgrid.Constants.Constants;
import com.example.grabgrid.Model.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.grab);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Constants.initialize(getApplicationContext());

        final Button button = (Button) findViewById(R.id.submitBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText)findViewById(R.id.usernameText)).getText().toString();
                String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
                final User user = new User();


                if(username==null || "".equalsIgnoreCase(username)){
                    Toast.makeText(getApplicationContext(),"Username Cannot be Empty!", Toast. LENGTH_SHORT).show();
                    return;
                }else{
                    user.setUsername(username);
                }

                if(password==null || "".equalsIgnoreCase(password)){
                    Toast.makeText(getApplicationContext(),"Password Cannot be Empty!", Toast. LENGTH_SHORT).show();
                    return;
                }else{
                    user.setPassword(password);
                }


                if(Constants.db.validateUser(user)){
                    final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "",
                            "Loading. Please wait...", true);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }
                    }, 2000);


                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Credentials!", Toast. LENGTH_SHORT).show();
                    return;
                }
            }
        });



        //get data from database pass to next activity



    }

}
