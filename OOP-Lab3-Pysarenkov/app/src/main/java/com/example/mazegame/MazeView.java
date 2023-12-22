package com.example.mazegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MazeView extends View {

    private Paint wallPaint = new Paint();
    private Paint playerPaint = new Paint();
    private Paint finishPaint = new Paint();
    private Maze maze;
    private Player player;
    private MazeManual mazeMan;
    private boolean isAdapted = false;
    private int x = 50, y = 50;
    private float currentWidth;
    private float currentHeight;
    public float proportion;
    public float padding = 50;

    private void init() {
        wallPaint.setColor(Color.BLACK);
        playerPaint.setColor(Color.RED);
        finishPaint.setColor(Color.GREEN);
    }

    public MazeView(Context context) {
        super(context);
        init();
    }

    public MazeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MazeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        this.isAdapted = false;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMazeMan(MazeManual manual) {
        this.mazeMan = manual;
    }

    public void drawWall() {
        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setWidthAndHeight();
        if (maze != null) {
            if (!isAdapted) {
                maze = MazeView.adaptToMazeView(maze, this);
                adaptPlayer(player, this);
                player.x = maze.startingPositionX;
                player.y = maze.startingPositionY;
                mazeMan.maze = maze;
                mazeMan.player = player;
                isAdapted = true;
            }
            for (Wall w : maze.horizontalWalls) {
                canvas.drawLine(w.pointX, w.pointY, w.pointX + w.width, w.pointY, wallPaint);
            }
            for (Wall w : maze.verticalWalls) {
                canvas.drawLine(w.pointX, w.pointY, w.pointX, w.pointY + w.width, wallPaint);
            }
            canvas.drawLine(maze.finishingWall.pointX, maze.finishingWall.pointY,
                    maze.finishingWall.pointX, maze.finishingWall.pointY +
                            maze.finishingWall.width, finishPaint);
        }
        if (player != null) {
//            canvas.drawRoundRect(player.x, player.y, player.width, player.height, 0, 0, playerPaint);
            canvas.drawCircle(player.x + player.width / 2, player.y + player.height / 2, player.width / 2, playerPaint);
        }
    }

    public void setWidthAndHeight() {
        currentWidth = this.getMeasuredWidth();
        currentHeight = this.getMeasuredHeight();
    }

    public static Maze adaptToMazeView(Maze maze, MazeView mazeView) {
        List<Wall> walls = new ArrayList<>();
        mazeView.proportion = (mazeView.currentWidth - 2 * mazeView.padding) / maze.width;
        float adaptedWidth = Math.round(maze.width * mazeView.proportion);
        float adaptedHeight = Math.round(maze.height * mazeView.proportion);
        for (Wall w : maze.verticalWalls) {
            walls.add(new Wall(Math.round(w.pointX * mazeView.proportion) + mazeView.padding,
                    Math.round(w.pointY * mazeView.proportion) + mazeView.padding,
                    Math.round(w.width * mazeView.proportion),
                    false));
        }
        for (Wall w : maze.horizontalWalls) {
            walls.add(new Wall(Math.round(w.pointX * mazeView.proportion) + mazeView.padding,
                    Math.round(w.pointY * mazeView.proportion) + mazeView.padding,
                    Math.round(w.width * mazeView.proportion),
                    true));
        }
        Wall finishingWall = new Wall(Math.round(maze.finishingWall.pointX * mazeView.proportion) + mazeView.padding,
                Math.round(maze.finishingWall.pointY * mazeView.proportion) + mazeView.padding,
                Math.round(maze.finishingWall.width * mazeView.proportion),
                maze.finishingWall.isHorizontal);
        float newSPX = maze.startingPositionX * mazeView.proportion + mazeView.padding;
        float newSPY = maze.startingPositionY * mazeView.proportion + mazeView.padding;
        float[] newOuterBounds = {mazeView.padding,
                mazeView.padding + adaptedWidth,
                mazeView.padding + adaptedHeight,
                mazeView.padding};
        return new Maze(adaptedWidth, adaptedHeight, newSPX, newSPY, walls, finishingWall, newOuterBounds);
    }

    public static void adaptPlayer(Player player, MazeView mazeView) {
        player.width = Math.round(player.width * mazeView.proportion);
        player.height = Math.round(player.height * mazeView.proportion);
    }

    public static void adaptRoute(Route route, MazeView mazeView){
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < route.nodes.size(); i++) {
            temp.add(Math.toIntExact(Math.round(route.nodes.get(i) * mazeView.proportion)));
        }
        route.nodes.clear();
        route.nodes.addAll(temp);
    }
}
