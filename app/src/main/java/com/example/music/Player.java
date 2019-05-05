package com.example.music;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {

    Button btn_pause,btn_next,btn_previous;
    TextView song_name;
    SeekBar song_seekBar;

    String Sname;

    static MediaPlayer media_player;
    int position;

    ArrayList<File> mySongs;
    Thread update_seekBar;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        final Button btn_pause = (Button)findViewById(R.id.pause);
        Button btn_next = (Button)findViewById(R.id.next);
        Button btn_previous = (Button)findViewById(R.id.previous);

        final TextView song_name = (TextView)findViewById(R.id.name);
        final SeekBar song_seekBar = (SeekBar)findViewById(R.id.seekBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        update_seekBar = new Thread(){
            @Override
            public void run() {

                int total_duration = media_player.getDuration();
                int current_position = 0;

                while (current_position<total_duration) {
                     try {

                         sleep(500);
                         current_position = media_player.getCurrentPosition();
                         song_seekBar.setProgress(current_position);
                     }

                     catch(InterruptedException e){
                         e.printStackTrace();
                         }

                }

            }
        };

        if(media_player !=null){
            media_player.stop();
            media_player.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs =(ArrayList)bundle.getParcelableArrayList("Songs");
        Sname = mySongs.get(position).getName().toString();

        String SongName = i.getStringExtra("Song_Name");
        song_name.setText(SongName);
        song_name.setSelected(true);

        position = bundle.getInt("position", 0);

        Uri u = Uri.parse(mySongs.get(position).toString());

        media_player = MediaPlayer.create(getApplicationContext(),u);
        media_player.start();
        song_seekBar.setMax(media_player.getDuration());

        update_seekBar.start();
        song_seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        song_seekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN);

        song_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media_player.seekTo(seekBar.getProgress());
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song_seekBar.setMax(media_player.getDuration());

                if(media_player.isPlaying()){
                    btn_pause.setBackgroundResource(R.drawable.play);
                    media_player.pause();
                }

                else{
                    btn_pause.setBackgroundResource(R.drawable.pause);
                    media_player.start();
                }

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media_player.stop();
                media_player.release();
                position = ((position+1)%mySongs.size());

                Uri u = Uri.parse(mySongs.get(position).toString());
                media_player = MediaPlayer.create(getApplicationContext(),u);

                Sname = mySongs.get(position).getName().toString();
                song_name.setText(Sname);

                media_player.start();


            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media_player.stop();
                media_player.release();

                position = ((position - 1)<0)?(mySongs.size()-1):(position-1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                media_player = MediaPlayer.create(getApplicationContext(),u);

                Sname = mySongs.get(position).getName().toString();
                song_name.setText(Sname);

                media_player.start();


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
