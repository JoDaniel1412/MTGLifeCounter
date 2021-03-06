package org.atlas.mtglifecounter.controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.atlas.mtglifecounter.R;
import org.atlas.mtglifecounter.game.Game;
import org.atlas.mtglifecounter.game.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupGameActivity extends AppCompatActivity {

    private Game game = Game.getInstance();
    private EditText starting_life_entry;
    private TextView commander_switch_message;
    private Switch commander_switch;
    private Switch vanguard_switch;
    private Switch display_players_name_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_game);

        starting_life_entry = findViewById(R.id.starting_life_entry);
        commander_switch_message = findViewById(R.id.commander_switch_message);
        commander_switch = findViewById(R.id.commander_switch);
        vanguard_switch = findViewById(R.id.vanguard_switch);
        display_players_name_switch = findViewById(R.id.display_players_name_switch);

        // Disables commander switch if only one Player
        if (game.getPlayers().size() == 1) {
            commander_switch_message.setVisibility(View.VISIBLE);
            commander_switch.setEnabled(false);
        }
    }

    public void pressed_play(View view) {
        game.setStartingLife(Integer.parseInt(starting_life_entry.getText().toString()));
        game.setCommander(commander_switch.isChecked());
        game.setVanguard(vanguard_switch.isChecked());
        game.setPlayerNamesDisplayed(display_players_name_switch.isChecked());

        Log.i("SetupGameAttributes", "Starting Life: " + game.getStartingLife());
        Log.i("SetupGameAttributes", "Commander enable: " + game.isCommander());
        Log.i("SetupGameAttributes", "Vanguard enable: " + game.isVanguard());
        Log.i("SetupGameAttributes", "Players Name enable: " + game.isPlayerNamesDisplayed());

        clearPlayersData();

        Intent animation = new Intent(this, GameActivity.class);
        startActivity(animation);
    }

    @SuppressLint("SetTextI18n")
    public void pressed_commander_switch(View view) {
        if (commander_switch.isChecked()) {
            starting_life_entry.setText("40");
        } else {
            starting_life_entry.setText("20");
        }
    }

    private void clearPlayersData() {
        // Sets the life for each Player
        List<Player> players = game.getPlayers();
        for (Player player : players) {
            player.setLife(game.getStartingLife());

            // Clears the Commander damage
            HashMap<Player, Integer> map = player.getCommanderDamage();
            for (Map.Entry<Player, Integer> entry : map.entrySet()) {
                entry.setValue(0);
            }
        }
    }
}
