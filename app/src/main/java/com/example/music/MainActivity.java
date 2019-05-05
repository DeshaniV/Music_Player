package com.example.music;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView My_song_list;
    String[] songs;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        My_song_list = (ListView)findViewById(R.id.song_list);

        runtime_per();
    }

    public void runtime_per(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        display();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        finish();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


    public ArrayList<File> find_song(File file){
        ArrayList<File> Song_array =  new ArrayList<>();

        File[] files = file.listFiles();

        for(File singleFile:files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                Song_array.addAll(find_song(singleFile));
            }

            else{
                if(singleFile.getName().endsWith(".mp3")||singleFile.getName().endsWith(".wav")){
                    Song_array.add(singleFile);
                }

            }

        }
        return Song_array;
    }

    void display(){

        final ArrayList <File> My_songs = find_song(Environment.getExternalStorageDirectory());
        songs = new String[My_songs.size()];

        for( i=0;i<My_songs.size();i++){
            songs[i]= My_songs.get(i).getName().toString().replace(".mp3", "")
                    .replace(".wav","");
        }

        ArrayAdapter<String> my_adap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,songs);
        My_song_list.setAdapter(my_adap);

    }
}
