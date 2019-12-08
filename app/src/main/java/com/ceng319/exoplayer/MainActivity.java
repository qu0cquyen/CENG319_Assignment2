package com.ceng319.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private SimpleExoPlayer exoPlayer;
    private PlayerView playerView;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playBackPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO 4: Get Player View ID from the layout
        playerView = findViewById(R.id.video_view);
    }


    private void initializePlayer()
    {
        //TODO 5: Adapt Smooth Streaming technique
        if(exoPlayer == null)
        {
           //Estimates bandwidth to be used while listening to data transfer
           BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
           //Find the available media tracks for the player.
           TrackSelector trackSelection = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
           //Create a Simple Instance of ExoPlayer.
           exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelection);
        }

        //Bind the player to Player View
        playerView.setPlayer(exoPlayer);

        //TODO 7: Provide link need to stream and create a media source
        //Video URL link
        Uri uri = Uri.parse(getString(R.string.video_link));
        MediaSource mediaSource = buildMediaSource(uri);

        //TODO 9: Provide media state information during playing [Start, Stop,....]
        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playBackPosition);
        exoPlayer.prepare(mediaSource, false, false);
    }

    //TODO 6: Load the Media into Player by URL
    private MediaSource buildMediaSource(Uri uri)
    {
        //Uri contains the URL of a media file
        //Specifies the User Agent and requests for data streaming (HTTP)
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("ua");
        //Extracts media format from the container.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        //Creates Media Source for player to play on the provided link.
        MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory,
                                                    null, null);

        return mediaSource;
    }

    //TODO 8: Rewrite app's lifecycle to adapt the player
    @Override
    public void onStart()
    {
        super.onStart();
        if(Util.SDK_INT >= 24)
        {
            initializePlayer();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        hideSystemUi();
        if((Util.SDK_INT < 24 || exoPlayer == null))
        {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi()
    {
        //Hide device's UI to put more focus on the player
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(Util.SDK_INT < 24)
        {
            releasePlayer();
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(Util.SDK_INT > 24)
        {
            releasePlayer();
        }
    }

    //Frees up player's resources and destroys it
    private void releasePlayer()
    {
        if(exoPlayer != null)
        {
            //Get the current state of the player.
            playWhenReady = exoPlayer.getPlayWhenReady();
            playBackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
