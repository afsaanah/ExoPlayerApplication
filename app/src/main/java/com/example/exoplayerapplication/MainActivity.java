package com.example.exoplayerapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity {
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;
    private String url = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
    private ImageView playIv, pauseIv, exoPlayerFullscreen, exitfullscreenIv,replayIv;
    private TextView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



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
            thumbnail.setVisibility(View.VISIBLE);
            replayIv.setVisibility(GONE);

           player.addListener(new Player.EventListener() {
               @Override
               public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

               }

               @Override
               public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

               }

               @Override
               public void onLoadingChanged(boolean isLoading) {

               }

               @Override
               public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                   switch (playbackState) {
                       case ExoPlayer.STATE_BUFFERING:
                           break;
                       case ExoPlayer.STATE_ENDED:
                       {
                           playIv.setVisibility(GONE);
                           pauseIv.setVisibility(GONE);
                           replayIv.setVisibility(View.VISIBLE);
                           replayIv.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                  player.seekTo(0);
                                   player.setPlayWhenReady(true);
                                   replayIv.setVisibility(GONE);
                               }
                           });
                       }

                           break;
                       case ExoPlayer.STATE_IDLE:
                           break;
                       case ExoPlayer.STATE_READY:
                       {
                           break;
                       }
                       default:
                           break;

                   }
               }
               @Override
               public void onRepeatModeChanged(int repeatMode) {

               }

               @Override
               public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

               }

               @Override
               public void onPlayerError(ExoPlaybackException error) {

               }

               @Override
               public void onPositionDiscontinuity(int reason) {

               }

               @Override
               public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

               }

               @Override
               public void onSeekProcessed() {

               }
           });

            exoPlayerFullscreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fullscreen();
                }
            });
            exitfullscreenIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exitfullscreen();
                }
            });


            playIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playVideo();


                }
            });
            pauseIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pauseVideo();

                }
            });





        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }


    }


    private void pauseVideo() {
        player.setPlayWhenReady(false);
        replayIv.setVisibility(GONE);
        pauseIv.setVisibility(GONE);
        playIv.setVisibility(View.VISIBLE);
        playerView.getVideoSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playIv.setVisibility(View.VISIBLE);
                pauseIv.setVisibility(GONE);
            }
        });
        exoPlayerFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullscreen();
            }
        });
        exitfullscreenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitfullscreen();
            }
        });

    }

    private void playVideo() {
        thumbnail.setVisibility(GONE);

        replayIv.setVisibility(GONE);
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
        exoPlayerFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullscreen();
            }
        });
        exitfullscreenIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitfullscreen();
            }
        });

    }


    private void fullscreen() {
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        exoPlayerFullscreen.setVisibility(GONE);
        exitfullscreenIv.setVisibility(View.VISIBLE);


    }

    private void exitfullscreen() {
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        exoPlayerFullscreen.setVisibility(View.VISIBLE);
        exitfullscreenIv.setVisibility(GONE);


    }


    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        pauseIv.setVisibility(GONE);
        playIv.setVisibility(View.VISIBLE);
    }

    private void init() {
        playIv = findViewById(R.id.playIv);
        pauseIv = findViewById(R.id.pauseIv);
        exoPlayerFullscreen = findViewById(R.id.exo_fullscreenIv);
        exitfullscreenIv = findViewById(R.id.exit_fullscreenIv);
        thumbnail = findViewById(R.id.thumbnail);
        replayIv =findViewById(R.id.replayIv);

    }

}
