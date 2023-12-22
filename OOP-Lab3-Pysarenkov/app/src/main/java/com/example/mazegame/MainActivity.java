package com.example.mazegame;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MazeView mazeView;
    private ImageButton buttonUp;
    private ImageButton buttonDown;
    private ImageButton buttonLeft;
    private ImageButton buttonRight;
    private boolean autoModeOn = false;

    private Maze initMaze() {
        return Maze.MazeExample1();
    }

    private Maze initMaze2() {
        return Maze.generateMaze();
    }

    private Player initPlayer(Maze maze) {
        return Player.player1(maze);
    }

    @SuppressLint({"ClickableViewAccessibility", "UseSwitchCompatOrMaterialCode"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mazeView = findViewById(R.id.mazeView);
        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);

        Button buttonRestart = findViewById(R.id.buttonRestart);
        Button buttonRegenerate = findViewById(R.id.buttonRegenerate);
        Switch automodeSwitch = findViewById(R.id.automodeSwitch);

        Maze maze1 = initMaze();
        mazeView.setMaze(maze1);
        Player player = initPlayer(maze1);
        mazeView.setPlayer(player);
        MazeManual manual = new MazeManual(maze1, player, mazeView);
        mazeView.setMazeMan(manual);

        buttonUp.setOnTouchListener(new RepeatListener(0, 80,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!autoModeOn){
                        manual.moveUp();
                        if (manual.success) {
                            Snackbar.make(view, "YOU PASSED", 5000).show();;
                        }}
                    }
                }));
        buttonRight.setOnTouchListener(new RepeatListener(0, 80,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!autoModeOn){
                        manual.moveRight();
                        if(manual.success) {
                            Snackbar.make(view, "YOU PASSED", 5000).show();
                        }}
                    }
                }));
        buttonDown.setOnTouchListener(new RepeatListener(0, 80,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!autoModeOn) {
                            manual.moveDown();
                            if (manual.success) {
                                Snackbar.make(view, "YOU PASSED", 5000).show();
                                ;
                            }
                        }
                    }
                }));
        buttonLeft.setOnTouchListener(new RepeatListener(0, 80,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!autoModeOn) {
                            manual.moveLeft();
                            if (manual.success) {
                                Snackbar.make(view, "YOU PASSED", 5000).show();
                                ;
                            }
                        }
                    }
                }));
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manual.success = false;
                manual.player.x = manual.maze.startingPositionX;
                manual.player.y = manual.maze.startingPositionY;
                mazeView.invalidate();
            }
        });
        buttonRegenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Maze maze1 = initMaze2();
                mazeView.setMaze(maze1);
                Player player = initPlayer(maze1);
                mazeView.setPlayer(player);
                manual.player = player;
                manual.maze = maze1;
                mazeView.setMazeMan(manual);
                manual.success = false;
                manual.player.x = manual.maze.startingPositionX;
                manual.player.y = manual.maze.startingPositionY;
                mazeView.invalidate();
            }
        });

        MazeAuto mazeAuto = new MazeAuto(manual);
        mazeAuto.directionButtons[0] = buttonUp;
        mazeAuto.directionButtons[1] = buttonRight;
        mazeAuto.directionButtons[2] = buttonDown;
        mazeAuto.directionButtons[3] = buttonLeft;
        automodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                buttonRestart.callOnClick();
                Route r = Route.routeExample1();
                MazeView.adaptRoute(r, mazeView);
                Thread autoThr = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mazeAuto.autoPass(r);
                    }
                });
                if(b) {
                    mazeAuto.interrupted = false;
                    autoModeOn = true;
                    autoThr.start();
                } else {
                    mazeAuto.interrupted = true;
                    autoModeOn = false;
                }
            }
        });
    }
}