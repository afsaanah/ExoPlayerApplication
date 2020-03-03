package com.example.exoplayerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity {
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;
    private String url = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    private ImageView playIv,pauseIv,exoPlayerFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playIv = findViewById(R.id.playIv);
        pauseIv = findViewById(R.id.pauseIv);
        exoPlayerFullscreen = findViewById(R.id.exo_fullscreenIv);


        try {
            playerView = findViewById(R.id.playerview);

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoUri = Uri.parse(url);

            DefaultHttpDataSourceFactory defaultHttpDataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource videosource = new ExtractorMediaSource(videoUri, defaultHttpDataSourceFactory, extractorsFactory, null, null);
            playerView.setPlayer(player);
            player.prepare(videosource);
            player.setPlayWhenReady(false);

           


            playIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    player.setPlayWhenReady(true);
                    playIv.setVisibility(View.GONE);

                    pauseIv.setVisibility(View.VISIBLE);
                    pauseIv.postDelayed(new Runnable() {
                        public void run() {
                            pauseIv.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);

                  playerView.getVideoSurfaceView().setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          playerView.showController();
                          playerView.setVisibility(View.VISIBLE);
                          pauseIv.setVisibility(View.VISIBLE);
                          pauseIv.postDelayed(new Runnable() {
                              public void run() {
                                  pauseIv.setVisibility(View.INVISIBLE);
                              }
                          }, 3000);
                      }
                  });

            /*  playerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pauseIv.setVisibility(View.VISIBLE);
                            pauseIv.postDelayed(new Runnable() {
                                public void run() {
                                    pauseIv.setVisibility(View.INVISIBLE);
                                }
                            }, 3000);
                        }
                    });*/

                }
            });
            pauseIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    player.setPlayWhenReady(false);

                    pauseIv.setVisibility(GONE);
                    playIv.setVisibility(View.VISIBLE);
                    playerView.getVideoSurfaceView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            playIv.setVisibility(View.VISIBLE);
                            pauseIv.setVisibility(GONE);
                        }
                    });

                }
            });

        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }


    }


}
