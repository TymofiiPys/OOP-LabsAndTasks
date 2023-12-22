package com.example.mazegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
    public final float width;
    public final float height;
    public final float startingPositionX;
    public final float startingPositionY;
    public final List<Wall> verticalWalls;
    public final List<Wall> horizontalWalls;
    public final Wall finishingWall;
    public float[] outerBounds;

    public Maze(float width, float height, float startingPositionX, float startingPositionY,
                List<Wall> walls, Wall finishingWall, float[] outerBounds) {
        this.width = width;
        this.height = height;
        this.startingPositionX = startingPositionX;
        this.startingPositionY = startingPositionY;
        this.verticalWalls = getWalls(walls, false);
        this.horizontalWalls = getWalls(walls, true);
        this.finishingWall = finishingWall;
        this.outerBounds = outerBounds;
    }

    private List<Wall> getWalls(List<Wall> walls, boolean isHorizontal) {
        List<Wall> ret = new ArrayList<>();
        for (Wall w : walls) {
            if (w.isHorizontal == isHorizontal) {
                ret.add(w);
            }
        }
        return ret;
    }

    public Maze(float width, float height, float startingPositionX, float startingPositionY,
                List<Wall> verticalWalls, List<Wall> horizontalWalls, Wall finishingWall,
                float[] outerBounds) {
        this.width = width;
        this.height = height;
        this.startingPositionX = startingPositionX;
        this.startingPositionY = startingPositionY;
        this.verticalWalls = verticalWalls;
        this.horizontalWalls = horizontalWalls;
        this.finishingWall = finishingWall;
        this.outerBounds = outerBounds;
    }

    public IntersectionStep hasIntersectionWithWall(Player player, short dir, int step) {
        switch (dir) {
            case 0:
                for (Wall w : horizontalWalls) {
                    if ((player.y - step <= w.pointY)
                            && (player.y >= w.pointY)
                            && (player.x + player.width >= w.pointX && player.x <= w.pointX + w.width)) {
                        return new IntersectionStep(true, player.y - w.pointY - 2);
                    }
                }
                for (Wall w : verticalWalls) {
                    if ((player.y - step <= w.pointY + w.width)
                            && (player.y >= w.pointY + w.width)
                            && (w.pointX <= player.x + player.width && w.pointX >= player.x)) {
                        return new IntersectionStep(true, player.y - w.pointY - w.width - 2);
                    }
                }
                if (player.y - step <= outerBounds[0] - player.height / 2)
                    return new IntersectionStep(true, 0);
                break;
            case 1:
                for (Wall w : verticalWalls) {
                    if ((player.x + step + player.width >= w.pointX)
                            && (player.x + player.width <= w.pointX)
                            && (player.y + player.height >= w.pointY && player.y <= w.pointY + w.width)) {
                        return new IntersectionStep(true, w.pointX - player.width - player.x - 2);
                    }
                }
                for (Wall w : horizontalWalls) {
                    if ((player.x + step + player.width >= w.pointX)
                            && (player.x + player.width <= w.pointX)
                            && (w.pointY <= player.y + player.height && w.pointY >= player.y)) {
                        return new IntersectionStep(true, w.pointX - player.width - player.x - 2);
                    }
                }
                if (player.x + player.width + step >= outerBounds[1] + player.width / 2)
                    return new IntersectionStep(true, 0);
                break;
            case 2:
                for (Wall w : horizontalWalls) {
                    if ((player.y + player.height + step >= w.pointY)
                            && (player.y + player.height <= w.pointY)
                            && (player.x + player.width >= w.pointX && player.x <= w.pointX + w.width)) {
                        return new IntersectionStep(true, w.pointY - player.height - player.y - 2);
                    }
                }
                for (Wall w : verticalWalls) {
                    if ((player.y + player.height + step >= w.pointY)
                            && (player.y + player.height <= w.pointY)
                            && (w.pointX <= player.x + player.width && w.pointX >= player.x)) {
                        return new IntersectionStep(true, w.pointY - player.y - player.height - 2);
                    }
                }
                if (player.y + player.height + step >= outerBounds[2] + player.height / 2)
                    return new IntersectionStep(true, 0);
                break;
            case 3:
                for (Wall w : verticalWalls) {
                    if ((player.x - step <= w.pointX)
                            && (player.x >= w.pointX)
                            && (player.y + player.height >= w.pointY && player.y <= w.pointY + w.width)) {
                        return new IntersectionStep(true, player.x - w.pointX - 2);
                    }
                }
                for (Wall w : horizontalWalls) {
                    if ((player.x - step <= w.pointX + w.width)
                            && (player.x >= w.pointX + w.width)
                            && (w.pointY <= player.y + player.height && w.pointY >= player.y)) {
                        return new IntersectionStep(true, player.x - w.pointX - w.width - 2);
                    }
                }
                if (player.x - step <= outerBounds[3] - player.width / 2)
                    return new IntersectionStep(true, 0);
                break;
        }
        return new IntersectionStep(false, 0);
    }

    public boolean hasIntersectionWithFinWall(Player player, int step) {
        if (!finishingWall.isHorizontal) {
            if ((player.x + step + player.width >= finishingWall.pointX)
                    && (player.x + player.width <= finishingWall.pointX)
                    && (player.y + player.height >= finishingWall.pointY
                    && player.y <= finishingWall.pointY + finishingWall.width)) {
                return true;
            }
        } else {
            if ((player.y + player.height + step >= finishingWall.pointY)
                    && (player.y + player.height <= finishingWall.pointY)
                    && (player.x + player.width >= finishingWall.pointX
                    && player.x <= finishingWall.pointX + finishingWall.width)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public IntersectionStep hasIntersectionWithVertWall(Player player, boolean right, int step) {
        if (right) {
            for (Wall w : verticalWalls) {
                if ((player.x + step + player.width >= w.pointX)
                        && (player.x + player.width <= w.pointX)
                        && (player.y >= w.pointY && player.y + player.height <= w.pointY + w.width)) {
                    return new IntersectionStep(true, w.pointX - player.width - player.x);
                }
            }
        } else {
            for (Wall w : verticalWalls) {
                if ((player.x - step <= w.pointX)
                        && (player.x >= w.pointX)
                        && (player.y >= w.pointY && player.y + player.width <= w.pointY + w.width)) {
                    return new IntersectionStep(true, player.x - w.pointX);
                }
            }
        }
        return new IntersectionStep(false, 0);
    }

    @Deprecated
    public IntersectionStep hasIntersectionWithHorWall(Player player, boolean down, int step) {
        if (down) {
            for (Wall w : horizontalWalls) {
                if ((player.y + player.height + step >= w.pointY)
                        && (player.y + player.height <= w.pointY)
                        && (player.x >= w.pointX && player.x + player.width <= w.pointX + w.width)) {
                    return new IntersectionStep(true, w.pointY - player.height - player.y);
                }
            }
        } else {
            for (Wall w : horizontalWalls) {
                if ((player.y - step <= w.pointY)
                        && (player.y >= w.pointY)
                        && (player.x >= w.pointX && player.x + player.width <= w.pointX + w.width)) {
                    return new IntersectionStep(true, player.y - w.pointY);
                }
            }
        }
        return new IntersectionStep(false, 0);
    }

    public static Maze generateMaze() {
        int width = 900;
        int height = 1200;
        List<Wall> walls = new ArrayList<>();

        int rows = width / 100;
        int columns = height / 100;

        Random rand = new Random();
        Wall startWall = new Wall(0, rand.nextInt(rows) * 100, 100, false);
        Wall finishingWall = new Wall(width, rand.nextInt(rows) * 100, 100, false);
        // Outer walls
        walls.add(new Wall(0, 0, width, true));
        walls.add(new Wall(0, height, width, true));
        walls.add(new Wall(0, 0, startWall.pointY, false));
        walls.add(new Wall(0, startWall.pointY + 100, height - (startWall.pointY + 100), false));
        walls.add(new Wall(width,  0, finishingWall.pointY, false));
        walls.add(new Wall(width, finishingWall.pointY + 100,height - (finishingWall.pointY + 100), false));

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(rand.nextInt(2) == 0) {
                    walls.add(new Wall(i * 100, j * 100, 100, false));
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                if(rand.nextInt(2) == 0) {
                    walls.add(new Wall(i * 100, j * 100, 100, true));
                }
            }
        }

//        int[][] mazeWeights = new int[rows][columns];
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                mazeWeights[i][j] = 1;
//            }
//        }
//        for (int i = 1; i < rows - 1; i += 2) // make a grid of empty rooms
//            for (int j = 1; j < columns - 1; j += 2) {
//                emptyCt++;
//                mazeWeights[i][j] = -emptyCt; // each room is represented by a different negative number
//                if (i < rows - 2) { // record info about wall below this room
//                    wallrow[wallCt] = i + 1;
//                    wallcol[wallCt] = j;
//                    wallCt++;
//                }
//                if (j < columns - 2) { // record info about wall to right of this room
//                    wallrow[wallCt] = i;
//                    wallcol[wallCt] = j + 1;
//                    wallCt++;
//                }
//            }

        return new Maze(900, 1200, -25, startWall.pointY + 25, walls,
                finishingWall,
                new float[]{0, 900, 1200, 0});
    }

    public static Maze MazeExample1() {
        List<Wall> walls = new ArrayList<>();

        walls.add(new Wall(0, 0, 900, true));
        walls.add(new Wall(0, 0, 100, false));
        walls.add(new Wall(700, 0, 300, false));
        walls.add(new Wall(900, 0, 600, false));

        walls.add(new Wall(0, 100, 600, true));
        walls.add(new Wall(700, 100, 100, true));
        walls.add(new Wall(400, 100, 400, false));
        walls.add(new Wall(600, 100, 100, false));

        walls.add(new Wall(0, 200, 200, true));
        walls.add(new Wall(500, 200, 100, true));
        walls.add(new Wall(0, 200, 1000, false));
        walls.add(new Wall(200, 200, 200, false));
        walls.add(new Wall(300, 200, 400, false));
        walls.add(new Wall(800, 200, 100, false));

        walls.add(new Wall(0, 300, 100, true));
        walls.add(new Wall(400, 300, 300, true));
        walls.add(new Wall(800, 300, 100, true));

        walls.add(new Wall(100, 400, 100, true));
        walls.add(new Wall(500, 400, 100, true));
        walls.add(new Wall(700, 400, 100, true));
        walls.add(new Wall(500, 400, 100, false));
        walls.add(new Wall(800, 400, 200, false));

        walls.add(new Wall(0, 500, 200, true));
        walls.add(new Wall(400, 500, 300, true));
        walls.add(new Wall(200, 500, 300, false));
        walls.add(new Wall(700, 500, 300, false));

        walls.add(new Wall(0, 600, 100, true));
        walls.add(new Wall(300, 600, 300, true));
        walls.add(new Wall(800, 600, 100, true));
        walls.add(new Wall(100, 600, 100, false));
        walls.add(new Wall(600, 600, 100, false));

        walls.add(new Wall(0, 700, 100, true));
        walls.add(new Wall(300, 700, 300, true));
        walls.add(new Wall(800, 700, 100, true));
        walls.add(new Wall(300, 700, 500, false));
        walls.add(new Wall(800, 700, 300, false));
        walls.add(new Wall(900, 700, 500, false));

        walls.add(new Wall(0, 800, 200, true));
        walls.add(new Wall(400, 800, 300, true));
        walls.add(new Wall(400, 800, 200, false));
        walls.add(new Wall(500, 800, 100, false));

        walls.add(new Wall(200, 900, 100, true));
        walls.add(new Wall(600, 900, 100, true));
        walls.add(new Wall(100, 900, 200, false));
        walls.add(new Wall(700, 900, 100, false));

        walls.add(new Wall(100, 1000, 200, true));
        walls.add(new Wall(400, 1000, 300, true));
        walls.add(new Wall(200, 1000, 100, false));

        walls.add(new Wall(400, 1100, 400, true));
        walls.add(new Wall(500, 1100, 100, false));

        walls.add(new Wall(0, 1200, 900, true));

        Wall finishingWall = new Wall(900, 600, 100, false);

        return new Maze(900, 1200, -25, 125, walls,
                finishingWall,
                new float[]{0, 900, 1200, 0});
    }
}
