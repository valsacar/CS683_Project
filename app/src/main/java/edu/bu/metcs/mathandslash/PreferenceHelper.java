package edu.bu.metcs.mathandslash;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class PreferenceHelper {
    private static String currentPlayer;
    private static MathCharacter currentCharacter;

    public static MathCharacter loadCharacter(String playerNumber) {
        MathCharacter character;
        currentPlayer = playerNumber;

        Gson gson = new Gson();
        String json = MainActivity.getPreferences().getString(playerNumber, "");
        character = gson.fromJson(json, MathCharacter.class);

        //Check to see if we have a saved character or not.
        if (character == null) {
            character = new MathCharacter();

            save();
        }

        currentCharacter = character;
        return character;
    }

    public static void save() {
        SharedPreferences.Editor prefsEditor = MainActivity.getPreferences().edit();
        Gson saveGson = new Gson();
        String saveJson = saveGson.toJson(currentCharacter);
        prefsEditor.putString(currentPlayer, saveJson);
        prefsEditor.commit();
    }

    public static MathCharacter getCurrentCharacter() {return currentCharacter;}

    public static MathCharacter reloadCurrentCharacter() {
        currentCharacter = loadCharacter(currentPlayer);
        return getCurrentCharacter();
    }
}
