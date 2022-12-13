package com.example.memoryofagoldfish600096_2223;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.memoryofagoldfish600096_2223.R;
import androidx.appcompat.widget.AppCompatDrawableManager;

@SuppressLint("AppCompatCustomView")
public class MemoryButton extends Button {

    protected int row; //creates variables for each row column and ID for each front facing graphic
    protected int column;
    protected int frontImageID;

    protected boolean flipped = false; //set each card to not flipped or matched by default
    protected boolean matched = false;

    protected Drawable front; //sets drawables for the front and back graphics of each card
    protected Drawable back;

    @SuppressLint("RestrictedApi")
    public MemoryButton(Context context, int r, int c, int cardFrontImage)
    {
        super(context);

        row = r;
        column = c;
        frontImageID = cardFrontImage;

        front = AppCompatDrawableManager.get().getDrawable(context, cardFrontImage); //pulls front card image from each individual instance in grid view
        back = AppCompatDrawableManager.get().getDrawable(context, R.drawable.tileback_94); //sets back graphic for every card by default

        setBackground(back); //sets background of card to back

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c)); //creates new gridlayout using the temporary parameters

        tempParams.width = (int) getResources().getDisplayMetrics().density * 50; //sets size for both width and height of each graphic
        tempParams.height = (int) getResources().getDisplayMetrics().density * 50;

        setLayoutParams(tempParams);
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public int getFrontImageID() {
        return frontImageID;
    }

    public void flip()
    {
        if(matched)
        {
            return; //doesnt flip if matched already
        }

        if(flipped)
        {
            animate().rotationYBy(180).withEndAction(new Runnable(){
                @Override
                public void run() {
                    setBackground(back);
                }
            });
            flipped = false;
        }

        else
        {
            animate().rotationYBy(180).withEndAction(new Runnable(){
                @Override
                public void run() {
                    setBackground(front);
                }
            });
            flipped = true;
        }
    }
}
