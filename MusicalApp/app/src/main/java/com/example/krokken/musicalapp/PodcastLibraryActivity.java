package com.example.krokken.musicalapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class PodcastLibraryActivity extends AppCompatActivity {

    //Main color for the background of each list item
    private int mColorBackground = R.color.podcast_background;

    //Settings has all the settings information and methods
    Settings settings = new Settings();

    //Arraylist for the podcasts on the device
    private ArrayList<Library> podcastLibraryArrayList;

    //Mediaplayer
    private MediaPlayer mMediaPlayer;

    //Audiomanager
    private AudioManager mAudioManager;

    //Listview
    private ListView listView;

    //On completion listener for the media player. It will decide if it continues to play
    //and if it will be random
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
            if (settings.getContinuousPlay()) {
                //If continuous play is selected, it will play another song
                Toast.makeText(PodcastLibraryActivity.this, "Continuous play selected", Toast.LENGTH_SHORT).show();
                if (settings.getRandomPlay()) {
                    //If random play is selected, it would randomly choose a new song to play
                    Toast.makeText(PodcastLibraryActivity.this, "Random play is selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    //Focus change listener for the audio manager
    //Decides what to do as the focus changes
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if ((focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) && mMediaPlayer != null) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        // Pause playback because your Audio Focus was
                        // temporarily stolen, but will be back soon.
                        mMediaPlayer.pause();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback, because you lost the Audio Focus.
                        mAudioManager.abandonAudioFocus(afChangeListener);
                    } else if (focusChange ==
                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, because something else is also
                        // playing audio over you.
                        mMediaPlayer.pause();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback, because you hold the Audio Focus
                        // again!
                        mMediaPlayer.start();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        setTitle(getString(R.string.podcasts_title));
        listView = findViewById(R.id.library_list_view);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        createLibraryArrayList();
        mediaPlayerListeners();
    }

    private void createLibraryArrayList() {
        podcastLibraryArrayList = new ArrayList<>();
        String[] STAR = {"*"};
        String[] audioExt = getResources().getStringArray(R.array.audio_extensions);
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //TODO Fix IS_PODCAST
        //@IS_NOTIFICATION can be changed back to IS_PODCAST, but since I have none and the one
        //I found for my phone wasn't being called by that tag
        String selection = MediaStore.Audio.Media.IS_NOTIFICATION + " != 0";
        Cursor albumCursor = managedQuery(uri, STAR, selection, null, null);

        if (albumCursor != null) {
            if (albumCursor.moveToFirst()) {
                do {
                    String songName = albumCursor.getString(albumCursor.
                            getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    for (String s : audioExt) {
                        songName = songName.replace(s, "");
                    }
                    String duration = albumCursor.getString(albumCursor
                            .getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String path = albumCursor.getString(albumCursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));
                    String albumName = (albumCursor.getString(albumCursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    int albumId = albumCursor.getInt(albumCursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    Bitmap albumBitmap;
                    try {
                        String albumArtPath = albumCursor.getString(albumCursor
                                .getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                        albumBitmap = BitmapFactory.decodeFile(albumArtPath);
                    } catch (Exception e) {
                        albumBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.sample);
                    }
                    podcastLibraryArrayList.add(new Library(songName, albumBitmap, path, duration));
                } while (albumCursor.moveToNext());
            }
        }

        final LibraryAdapter libraryAdapter = new LibraryAdapter(this, podcastLibraryArrayList, mColorBackground);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(com.example.krokken.musicalapp.PodcastLibraryActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        listView.setAdapter(libraryAdapter);
    }

    private void mediaPlayerListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Library musicLibraryPosition = podcastLibraryArrayList.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    try {
                        mMediaPlayer = new MediaPlayer();
                        mMediaPlayer.setDataSource(musicLibraryPosition.getAudioResourceId());
                        mMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
                //If request to get the focus fails, it releases the player
                if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED) releaseMediaPlayer();
            }
        });
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
