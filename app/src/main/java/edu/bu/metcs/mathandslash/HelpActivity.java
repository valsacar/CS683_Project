package edu.bu.metcs.mathandslash;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/*This is based off a tutorial I found online, but I forgot to record the URL of it*/

public class HelpActivity extends AppCompatActivity {

    static public final String ARG_TEXT_ID = "text_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.help_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) findViewById (R.id.help_page_intro);
        if (textView != null) {
            textView.setMovementMethod (LinkMovementMethod.getInstance());
            textView.setText (Html.fromHtml (getString (R.string.help_page_intro_html)));
        }
    }

    public void onClickHelp (View v)
    {
        int id = v.getId ();
        int textId = -1;
        switch (id) {
            case R.id.help_button1 :
                textId = R.string.topic_section_character;
                break;
            case R.id.help_button2 :
                textId = R.string.topic_section_shop;
                break;
            case R.id.help_button3 :
                textId = R.string.topic_section_fight;
                break;
            default:
                break;
        }

        if (textId >= 0) startInfoActivity (textId);
        else toast (getString(R.string.help_no_detail), true);
    }

    public void startInfoActivity (int textId)
    {
        if (textId >= 0) {
            Intent intent = (new Intent(this, HelpTopicActivity.class));
            intent.putExtra (ARG_TEXT_ID, textId);
            startActivity (intent);
        } else {
            toast (getString(R.string.help_no_info) + textId, true);
        }
    }

    public void toast (String msg, boolean longLength)
    {
        Toast.makeText (getApplicationContext(), msg,
                (longLength ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT)
        ).show ();
    }


}
