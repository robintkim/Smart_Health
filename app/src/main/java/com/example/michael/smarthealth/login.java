package com.example.michael.smarthealth;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.*;

/**
 * Created by Michael on 2/14/2017.
 */
public class login extends AppCompatActivity {
    //xml ids
    public Button buttonLogin;

    public void functionsUI(){
        buttonLogin = (Button)findViewById(R.id.buttonLogin); //reference to button from xml

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), main_screen.class); //intent is the link between pages
                startActivity(intent); //when button is pressed, move from activity1 to activity2
                //finish(); //closes the activity, removes it from stack
            }
        }); //end line of buttonCreateNewAccount
    }

    //creates the initial screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        functionsUI();
    }
}
