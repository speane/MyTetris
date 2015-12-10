/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytetris;

/**
 *
 * @author Evgeny
 */

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

public class Tetromino {
    public Tetromino(Color color) {
        this.color = color;
    }
    private final boolean[][] tiles = { { true , true }, { true, false }};
    private final Color color;
    
    void draw(Graphics2D drawCanvas, int row, int column, int width, int height) {
        /*for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (tiles[i][j]) {
                    drawCanvas.setPaint(color);
                    drawCanvas.fillRect(x + j * width, y + i * height, width, height);
                }
            }
        }*/
        ArrayList<Tile> tiles = this.getTiles(row, column);
        for (Tile tempTile : tiles) {
            tempTile.draw(drawCanvas, width, height);
        }
    }
    
    public ArrayList<Tile> getTiles(int row, int column) {
        ArrayList<Tile> result = new ArrayList<>();
        
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                if (tiles[i][j]) {
                    result.add(new Tile(Color.CYAN, row + j, column+ i));
                    /*drawCanvas.setPaint(color);
                    drawCanvas.fillRect(x + j * width, y + i * height, width, height);*/
                }
            }
        }
        
        return result;
    }
}
