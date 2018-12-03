package com.example.vlad.navigation.ui.defineLocationSystems.dialogSystem;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.vlad.navigation.database.CounterExitOfBuilding;

/**
 * Created by RoMka on 16.04.2016.
 */
public class BuilderNextQuestion {
    private static final String[] question = {"Are you near exit?", "Select exit", "Do you see same room?",
            "Input number room", "You ought to go to near room"};
    private static final BuilderNextQuestion builder = new BuilderNextQuestion();
    private static TextView view;
    private static int levelTree = 1;
    private boolean flag;

    private BuilderNextQuestion() {
    }

    public void setTextView(TextView view) {
        this.view = view;
    }

    public static BuilderNextQuestion getInstance() {
        levelTree = 1;
        return builder;
    }

    public boolean execute(boolean flagAnswer) {
        flag = flagAnswer;
        switch (levelTree) {
            case 1:
                if (flagAnswer) {
                    view.setText(question[0]);
                };
                levelTree++;
                return !flagAnswer;
            case 2:
                if (flagAnswer) {
                    view.setText(question[2]);
                };
                levelTree++;
                return flagAnswer;
            case 3:
                return true;
        }
        return true;
    }

    public void setResultLayout(ViewGroup layout, Activity activity) {
        switch (levelTree) {
            case 1:
                FinderGlobalLocation finder = new FinderGlobalLocation();
                break;
            case 2:
                setViewOnSecondLevel(layout, activity);
                break;
            case 3:
                setViewOnThirdLevel(layout,activity);
                break;
        }
    }

    private void setViewOnSecondLevel(ViewGroup layout, Activity activity) {
        CounterExitOfBuilding counter = new CounterExitOfBuilding();
        int numberExits = counter.execute();
        if (numberExits > 1) {
            Bitmap[] images = counter.getPicturesExits();
            for (int i = 0; i < numberExits; ++i) {
                ImageView view = new ImageView(activity.getBaseContext());
                view.setImageBitmap(images[i]);
                layout.addView(view);
            }
        } else {
            //TODO: start activity map
        }
    }

    private void setViewOnThirdLevel(ViewGroup layout, Activity activity) {
        if (flag) {
            layout.removeAllViews();
            TextView text = new TextView(activity.getBaseContext());
            text.setText(question[3]);
            layout.addView(text);
            EditText textBox = new EditText(activity.getBaseContext());
            text.setTextColor(Color.rgb(0,0,0));
            Button button = new Button(activity.getBaseContext());
            button.setText("Select");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            layout.addView(textBox);
        } else {
            layout.removeAllViews();
            TextView text = new TextView(activity.getBaseContext());
            text.setTextColor(Color.rgb(0,0,0));
            text.setText(question[4]);
            layout.addView(text);
            Button button = new Button(activity.getBaseContext());
            button.setText("go back");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            layout.addView(button);
        }
    }
}