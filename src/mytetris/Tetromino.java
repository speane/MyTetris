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
    private int currentState = 0;
    
    public Tetromino(int colorType, int tetrominoType) {
        //this.color = color;
        switch (tetrominoType) {
            case 0:
                tiles = OType.clone();
                break;
            case 1:
                tiles = IType.clone();
                break;
            case 2:
                tiles = TType.clone();
                break;
            case 3:
                tiles = SType.clone();
                break;
            case 4:
                tiles = ZType.clone();
                break;
            default:
                tiles = OType.clone();
                break;
        }
        
        switch (colorType) {
            case 0:
                color = Color.GREEN;
                break;
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.ORANGE;
                break;
            case 3:
                color = Color.BLUE;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            default:
                color = Color.PINK;
                break;
        }
    }
    
    private final boolean[][][] OType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { false, true, true, false },
                                             { false, true, true, false } } };
    
    
    private final boolean[][][] IType =  { { { false, true, false, false }, 
                                             { false, true, false, false },
                                             { false, true, false, false },
                                             { false, true, false, false } },
        
                                           { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { false, false, false, false },
                                             { true, true, true, true } } };
    
    private final boolean[][][] TType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { false, true, false, false },
                                             { true, true, true, false } } };
    
    private final boolean[][][] ZType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { true, true, false, false },
                                             { false, true, true, false } } };
    
    private final boolean[][][] SType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { false, true, true, false },
                                             { true, true, false, false } } };
    
    
    private final boolean[][][] tiles;
    
    //private final boolean[][] tiles = { { true, true, true, true } };
    
    //private final boolean[][] tiles = { { true , true }, { true, false }};
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
        
        for (int i = 0; i < this.tiles[0].length; i++) {
            for (int j = 0; j < this.tiles[0][i].length; j++) {
                if (tiles[0][i][j]) {
                    result.add(new Tile(color, row + i, column + j));
                    /*drawCanvas.setPaint(color);
                    drawCanvas.fillRect(x + j * width, y + i * height, width, height);*/
                }
            }
        }
        
        return result;
    }
    
}
