package com.example.memoryofagoldfish600096_2223;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class Activity_Game extends AppCompatActivity implements View.OnClickListener {

    private int amountOfOptions;  //amount of buttons

    private MemoryButton[] buttons; //array of buttons to be used

    private int[] buttonGraphicsLocations; //arrays for both the graphics used for buttons and the location of each on the grid
    private int[] buttonGraphics;

    private MemoryButton selectedFirst; //button variables for storing the selected buttons by the user
    private MemoryButton selectedSecond;

    private boolean isBusy = false; //checks the current status of whether the program has buttons selected to avoid crashing

    private int scoreTotal; //sets base score value



    @Override
    protected void onCreate(Bundle savedInstanceState) { //on creation of game screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){ //checks current orientation and changes view depending
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.grid_layout); //creates grid layout

        int numColumns = gridLayout.getColumnCount(); //gets the values for both total number of columns and rows
        int numRows = gridLayout.getRowCount();

        amountOfOptions = numColumns * numRows; //gets amount of options from number of rows and columns
        scoreTotal = 0;

        buttons = new MemoryButton[amountOfOptions]; //creates array the size of the amount of buttons needed

        buttonGraphics = new int[amountOfOptions / 2];

        buttonGraphics[0] = R.drawable.goldfish1_94; //loads all graphics needed
        buttonGraphics[1] = R.drawable.goldfish2_94;
        buttonGraphics[2] = R.drawable.goldfish3_94;
        buttonGraphics[3] = R.drawable.goldfish4_94;
        buttonGraphics[4] = R.drawable.goldfish5_94;
        buttonGraphics[5] = R.drawable.goldfish6_94;
        buttonGraphics[6] = R.drawable.goldfish7_94;
        buttonGraphics[7] = R.drawable.goldfish8_94;
        buttonGraphics[8] = R.drawable.goldfish9_94;
        buttonGraphics[9] = R.drawable.goldfish10_94;

        buttonGraphicsLocations = new int[amountOfOptions]; //creates array for locations of each graphic

        shuffleButtonGraphics(); //shuffles locations

        for(int r = 0; r < numRows; r++) //creates grid and sets all shuffled graphics in each location
        {
            for(int c = 0; c < numColumns; c++)
            {
                MemoryButton tempButton = new MemoryButton(this, r, c, buttonGraphics[buttonGraphicsLocations[r * numColumns + c]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[r * numColumns + c] = tempButton;
                gridLayout.addView(tempButton);
            }
        }

    }



    protected void shuffleButtonGraphics()
    {
        Random rand = new Random();

        for(int i = 0; i < amountOfOptions; i++) //sets each graphic int location in order
        {
            buttonGraphicsLocations[i] = i % (amountOfOptions / 2);

        }
        for(int i = 0; i < amountOfOptions; i++) //lists each option and shuffles it into another place using temp variable
        {
            int temp = buttonGraphicsLocations[i];
            int swapLocation = rand.nextInt(16);

            buttonGraphicsLocations[i] = buttonGraphicsLocations[swapLocation];

            buttonGraphicsLocations[swapLocation] = temp;
        }

    }

    @Override
    public void onClick(View view) {

        if(isBusy){
            return;
        }
        MemoryButton button = (MemoryButton) view;

        if(button.isMatched()){ //do nothing if matched already
            return;
        }

        if(selectedFirst == null) { //flips selected
            selectedFirst = button;
            selectedFirst.flip();
            return;
        }

        if(selectedFirst.getId() == button.getId()){ //returns if selected matches already selected button
            return;
        }

        if(selectedFirst.getFrontImageID() == button.getFrontImageID()){ //if match
            button.flip();

            button.setMatched(true); //sets both to matched and disables them
            selectedFirst.setMatched(true);

            selectedFirst.setEnabled(false);
            button.setEnabled(false);

            scoreTotal+=100; // adds to score

            selectedFirst = null; //resets selected to a null value for reselection
            return;
        }

        else{
            selectedSecond = button; //flips second choice over
            selectedSecond.flip();
            isBusy = true;

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedSecond.flip(); //flips back over both selected and makes the choice null for reselection
                    selectedFirst.flip();
                    selectedFirst = null;
                    selectedSecond = null;
                    scoreTotal-=10; //takes away from score for wrong match
                }
                }, 500);
        }
        TextView tv = (TextView)findViewById(R.id.scoreText); //changes text for set score to updated score
        tv.setText(scoreTotal);
    }
}