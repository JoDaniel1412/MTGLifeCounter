package org.atlas.mtglifecounter.controllers;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.graphics.LifeCounter;
import org.atlas.mtglifecounter.logic.Players;
import org.atlas.mtglifecounter.util.Math;

public class GameActivity extends AppCompatActivity {

    private FrameLayout game_layout;
    private boolean game_layout_loaded = false;
    private int[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game_layout = findViewById(R.id.game_layout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!game_layout_loaded && event.getAction() == MotionEvent.ACTION_UP) {
            game_layout_loaded = true;
            loadGrid();
        }
        return super.onTouchEvent(event);
    }

    private void loadGrid() {
        int players_selected = PlayerSelectionActivity.players_selected;
        colors = loadColors();

        int columns = (int) java.lang.Math.sqrt(players_selected);
        int rows = Math.ceilingDivision(players_selected, columns);
        int width = game_layout.getWidth();
        int height = game_layout.getHeight();
        int xOffset = width / columns;
        int yOffset = height / rows;
        int x = 0;
        int y = 0;

        int p = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //if (p == 5) xOffset *= 2;
                //if (xOffset > width) xOffset /= 2;
                int color = Math.getRandomNumberInRange(0, colors.length - 1);

                // Sets the Life Counter Views
                LifeCounter lifeCounter = new LifeCounter(this);
                lifeCounter.setBackgroundColor(colors[color]);
                lifeCounter.setX(x);
                lifeCounter.setY(y);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(xOffset, yOffset);
                lifeCounter.setLayoutParams(params);

                // Load the Life Counter
                Players.getPlayers_life().add(lifeCounter);
                game_layout.addView(lifeCounter);

                color += 1;
                x += xOffset;
                p++;
            }
            x = 0;
            y += yOffset;
        }
    }

    @SuppressLint("ResourceType")
    private int[] loadColors() {
        return new int[]{
                ContextCompat.getColor(this, R.color.colorMaximumBlueGreen),
                ContextCompat.getColor(this, R.color.colorRoseMadder),
                ContextCompat.getColor(this, R.color.colorBrightYellow),
                ContextCompat.getColor(this, R.color.colorIndigo),
                ContextCompat.getColor(this, R.color.colorMaastrichtBlue),
                ContextCompat.getColor(this, R.color.colorMaximumRed),
                ContextCompat.getColor(this, R.color.colorPaleAqua),
                ContextCompat.getColor(this, R.color.colorDarkPurple),
                ContextCompat.getColor(this, R.color.colorHeidelbergRed),
                ContextCompat.getColor(this, R.color.colorCharlestonGreen),
                ContextCompat.getColor(this, R.color.colorAsparagus),
        };
    }
}
