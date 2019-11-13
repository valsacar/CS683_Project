package edu.bu.metcs.activitylifecycle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final static int requestViewChar = 1;
    MathCharacter player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        //TODO: Implement saving characters so we don't start from 0 each time.
        this.player = new MathCharacter();
    }

    public void onClickChar(View view) {
        Intent intent = new Intent(this, ViewCharActivity.class);
        intent.putExtra("character", this.player);
        startActivityForResult(intent, requestViewChar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestViewChar) {
            if(resultCode == RESULT_OK) {
                MathCharacter character=(MathCharacter)data.getSerializableExtra("character");
                this.player = character;
            }
        }
    }
}
