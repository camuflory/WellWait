package com.ninethyear2023.wellwait;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private ImageView rightWolf;
    private ImageView leftWolf;
    private ImageView upperRightBag;
    private ImageView lowerRightBag;
    private ImageView upperLeftBag;
    private ImageView lowerLeftBag;
    private ImageView imageViewEgg;
    private ConstraintLayout eggsLayout;

    private float eggX = 160f;
    private float eggY = 420f;

    private Canvas canvas;
    private Paint paint;
    private int lifes = 3;
    private int bagPosition = 0; // 1-верхняя левая, 2-нижняя левая, 3-верхняя правая, 4-нижняя правая

    private MediaPlayer mediaPlayer;
    private Handler handler;
    private final Runnable runnable = this::runTimer; // runTimer == onDraw
    boolean running; // paused
    boolean wasRunning;
    long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rightWolf = findViewById(R.id.imageViewRightWolf);
        leftWolf = findViewById(R.id.imageViewLeftWolf);
        upperRightBag = findViewById(R.id.imageViewUpperRight);
        lowerRightBag = findViewById(R.id.imageViewLowerRight);
        upperLeftBag = findViewById(R.id.imageViewUpperLeft);
        lowerLeftBag = findViewById(R.id.imageViewLowerLeft);
        eggsLayout = findViewById(R.id.EggsConstraintLayout);

        imageViewEgg = new ImageView(this);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.egg, null);
        imageViewEgg.setImageBitmap(bm);
        eggsLayout.addView(imageViewEgg);

        rightWolf.setRotationY(180f);
        upperRightBag.setRotationY(180f);
        lowerRightBag.setRotationY(180f);

        handler = new Handler();

        mediaPlayer = MediaPlayer.create(this, R.raw.sound); // создаем плеер на основе записи

        if (savedInstanceState != null) {
            time = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        running = true;
        runTimer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
            mediaPlayer.start();
        }
    }

    public void onClickUpperRight(View view) {
        upperRightBag.setVisibility(View.VISIBLE);
        rightWolf.setVisibility(View.VISIBLE);
        bagPosition = 3;
        lowerLeftBag.setVisibility(View.INVISIBLE);
        lowerRightBag.setVisibility(View.INVISIBLE);
        upperLeftBag.setVisibility(View.INVISIBLE);
        leftWolf.setVisibility(View.INVISIBLE);
    }
    public void onClickLowerRight(View view) {
        lowerRightBag.setVisibility(View.VISIBLE);
        rightWolf.setVisibility(View.VISIBLE);
        bagPosition = 4;
        lowerLeftBag.setVisibility(View.INVISIBLE);
        upperRightBag.setVisibility(View.INVISIBLE);
        upperLeftBag.setVisibility(View.INVISIBLE);
        leftWolf.setVisibility(View.INVISIBLE);
    }
    public void onClickUpperLeft(View view) {
        upperLeftBag.setVisibility(View.VISIBLE);
        leftWolf.setVisibility(View.VISIBLE);
        bagPosition = 1;
        lowerLeftBag.setVisibility(View.INVISIBLE);
        lowerRightBag.setVisibility(View.INVISIBLE);
        upperRightBag.setVisibility(View.INVISIBLE);
        rightWolf.setVisibility(View.INVISIBLE);
    }
    public void onClickLowerLeft(View view) {
        lowerLeftBag.setVisibility(View.VISIBLE);
        leftWolf.setVisibility(View.VISIBLE);
        bagPosition = 2;
        upperRightBag.setVisibility(View.INVISIBLE);
        lowerRightBag.setVisibility(View.INVISIBLE);
        upperLeftBag.setVisibility(View.INVISIBLE);
        rightWolf.setVisibility(View.INVISIBLE);
    }

    protected void runTimer() {
        if (running) {
            handler.postDelayed(runnable, 1000L);
            mediaPlayer.start();
            time++;
        }
        if (eggX <= 360f) {
            imageViewEgg.setX(eggX);
            imageViewEgg.setY(eggY);
            eggY = 0.6f * eggX + 324f;
            eggX += 20f;
        }
    }
}