package com.example.mazegame;

import android.view.View;

public class MazeManual {
    public Maze maze;
    public Player player;
    public View mazeview;
    public boolean success = false;
    public int step = 10;

    public MazeManual(Maze maze, Player player, View mazeview) {
        this.maze = maze;
        this.mazeview = mazeview;
        this.player = player;
    }

    public void checkSuccess() {
        if (maze.hasIntersectionWithFinWall(player, step)){
            success = true;
        }
    }
    public void moveUp() {
        IntersectionStep is;
        if ((is = maze.hasIntersectionWithWall(player, (short) 0, step)).intersection) {
            player.y -= is.step;
        } else {
            player.y -= step;
        }
        checkSuccess();
        mazeview.invalidate();
    }

    public void moveRight() {
        IntersectionStep is;
        if ((is = maze.hasIntersectionWithWall(player, (short) 1, step)).intersection) {
            player.x += is.step;
        } else {
            player.x += step;
        }
        checkSuccess();
        mazeview.invalidate();
    }

    public void moveDown() {
        IntersectionStep is;
        if ((is = maze.hasIntersectionWithWall(player, (short) 2, step)).intersection) {
            player.y += is.step;
        } else {
            player.y += step;
        }
        checkSuccess();
        mazeview.invalidate();
    }

    public void moveLeft() {
        IntersectionStep is;
        if ((is = maze.hasIntersectionWithWall(player, (short) 3, step)).intersection) {
            player.x -= is.step;
        } else {
            player.x -= step;
        }
        checkSuccess();
        mazeview.invalidate();
    }
}
