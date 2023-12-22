package com.example.mazegame;

public class Player {
    public float x;
    public float y;
    public float width;
    public float height;

    public static Player player1(Maze m){
        Player player = new Player();
        player.x = m.startingPositionX;
        player.y = m.startingPositionY;
        player.width = 50;
        player.height = 50;
        return player;
    }
}
