package com.example.guessgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText name, min, max, guess;
    TextView highestScorer, help, result;
    Button button;

    //    variable count is used for generating random number along with user details to the text file only 1 time
//    variable attempts is used for calculating number of attempts to guess the number
//    global variables
    int count = 0, attempts = 0, compareInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        highest scorer name with number of attempts
        highestScorer = findViewById(R.id.highestScorer);

//        user inputs
        name = findViewById(R.id.name);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
        guess = findViewById(R.id.guess);

//        setting helping tag and final result
        help = findViewById(R.id.help);
        result = findViewById(R.id.result);

//        enter button
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equalsIgnoreCase(""))
                    name.setError("required");
                else if (min.getText().toString().equalsIgnoreCase(""))
                    min.setError("required");
                else if (max.getText().toString().equalsIgnoreCase(""))
                    max.setError("required");
                else if (guess.getText().toString().equalsIgnoreCase(""))
                    guess.setError("required");
                else {
                    attempts++;
                    String userName = name.getText().toString();
                    int userMin = Integer.parseInt(min.getText().toString());
                    int userMax = Integer.parseInt(max.getText().toString());
                    int userGuess = Integer.parseInt(guess.getText().toString());

//                if minimum number is greater or equal to maximum number
                    if (userMin >= userMax) {
                        help.setText("Please, provide a valid Range!!");
                    }
                    else if(!(userGuess>=userMin && userGuess<=userMax))
                        help.setText("Please, enter a valid Guess!!");
                    else {
                        count++;
                        if (count == 1) {
                            int comp = (int) ((Math.random() * userMax) + userMin);
                            SharedPreferences sp1 = getSharedPreferences("file", MODE_PRIVATE);
                            SharedPreferences.Editor ed1 = sp1.edit();
                            ed1.putString("randomNumber", String.valueOf(comp));
                            ed1.apply();
                        }
                        SharedPreferences sp2 = getSharedPreferences("file", MODE_PRIVATE);
                        String randomNumber = sp2.getString("randomNumber", "default string");
                        int n2 = Integer.parseInt(randomNumber);

//                    setting the helping tag which shows that you are going in a right way or not
                        if (userGuess < n2) {
                            help.setText("Higher Number Please!!");
                            guess.getText().clear();
                        } else if (userGuess > n2) {
                            help.setText("Lower Number Please!!");
                            guess.getText().clear();
                        } else {
                            help.setText("Congratulation!!");
                            result.setText(userName + " has guessed the number " + n2 + " in " + attempts + " attempts. ");
                            if (attempts == 1) {
                                compareInt = Integer.MAX_VALUE;
                            } else {
                                compareInt = Integer.parseInt(sp2.getString("target", Integer.toString(attempts)));
                            }
                            if (attempts <= compareInt) {
                                SharedPreferences.Editor ed2 = sp2.edit();
                                ed2.putString("target", Integer.toString(attempts));
                                ed2.putString("highestName", userName);
                                ed2.apply();

                                String topper = sp2.getString("highestName", "default string");
                                String topValue = sp2.getString("target", "default value");

//                            setting the highest score on highest scorer tag
                                highestScorer.setText(topper + " is our new top scorer with " + topValue + " attempts.");
                            }
                        }
                    }
                }
            }
        });

//        when app is launched, it shows the last highest scorer details on highest tag if any
        SharedPreferences global = getSharedPreferences("file", MODE_PRIVATE);
        String str = global.getString("highestName", "default");
        String str2 = global.getString("target", "default");

//        there is no last scorer or nobody has played with this app till now, then it just leave the highest tag with an empty string
        if (str.equalsIgnoreCase("default")) {
            highestScorer.setText("");
        }

//        it shows the highest scored player details
        else {

            highestScorer.setText(str + " is our new top scorer with " + str2 + " attempts.");
        }
    }
}