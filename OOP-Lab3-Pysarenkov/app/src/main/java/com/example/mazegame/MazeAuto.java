package com.example.mazegame;

import android.widget.ImageButton;

public class MazeAuto {
    public MazeManual manual;
    public ImageButton[] directionButtons;

    public boolean interrupted = false;
    public MazeAuto(MazeManual manual) {
        this.manual = manual;
        this.directionButtons = new ImageButton[4];
    }

    public void autoPass(Route r) {
        short direction;
        int passedInOneDirection = 0;
        for (int i = 0; i < r.nodes.size(); i++) {
            try {
                direction = r.directions.get(i);
                while (passedInOneDirection < r.nodes.get(i) && !interrupted) {
                    switch (direction) {
                        case 0:
                            manual.moveUp();
                            break;
                        case 1:
                            manual.moveRight();
                            break;
                        case 2:
                            manual.moveDown();
                            break;
                        case 3:
                            manual.moveLeft();
                            break;
                    }
                    passedInOneDirection += manual.step;

                    Thread.sleep(80);

                }
            } catch (InterruptedException e) {
                break;
            }
            if(interrupted) {
                break;
            }
            passedInOneDirection = 0;
        }
    }
}
