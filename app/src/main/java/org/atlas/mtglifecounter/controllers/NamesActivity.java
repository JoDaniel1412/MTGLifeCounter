package org.atlas.mtglifecounter.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.game.Game;
import org.atlas.mtglifecounter.game.Player;

import java.util.ArrayList;
import java.util.List;

public class NamesActivity extends AppCompatActivity {

    Game game = Game.getInstance();
    EditText p1;
    EditText p2;
    EditText p3;
    EditText p4;
    EditText p5;
    EditText p6;
    List<EditText> names = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);

        p1 = findViewById(R.id.player_name_1);
        p2 = findViewById(R.id.player_name_2);
        p3 = findViewById(R.id.player_name_3);
        p4 = findViewById(R.id.player_name_4);
        p5 = findViewById(R.id.player_name_5);
        p6 = findViewById(R.id.player_name_6);

       loadNamesEntries();
    }

    public void pressed_continue(View view) {
        // Sets the name for each player
        int i = 0;
        for (Player player : game.getPlayers()) {
            String name = names.get(i).getText().toString();
            player.setName(name);
            i++;
        }

        Intent animation = new Intent(this, SetupGameActivity.class);
        startActivity(animation);
    }

    private void loadNamesEntries() {
        List<Player> playerList = game.getPlayers();
        List<EditText> temp = new ArrayList<>();
        temp.add(p1);
        temp.add(p2);
        temp.add(p3);
        temp.add(p4);
        temp.add(p5);
        temp.add(p6);

        for (int i = 0; i < playerList.size(); i++) {
            EditText name = temp.get(i);
            name.setEnabled(true);
            names.add(name);
        }
    }
}
