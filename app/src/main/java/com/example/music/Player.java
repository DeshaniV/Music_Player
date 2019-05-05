package com.example.music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Player extends AppCompatActivity {

    Button btn_pause,btn_next,btn_previous;
    TextView song_name;
    SeekBar song_seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Button btn_pause = (Button)findViewById(R.id.pause);
        Button btn_next = (Button)findViewById(R.id.next);
        Button btn_previous = (Button)findViewById(R.id.previous);

        TextView song_name = (TextView)findViewById(R.id.name);
        SeekBar song_seekbar = (SeekBar)findViewById(R.id.seekBar);
    }
}
