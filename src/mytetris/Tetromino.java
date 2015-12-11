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
    private int currentState;
    private final boolean[][][] tiles;
    private final Color color;
    
    public void nextState() {
        currentState = (currentState + 1) % tiles.length;
    }
    
    public void prevState() {
        currentState = (currentState + tiles.length - 1) % tiles.length;
    }
    
    public Tetromino(int colorType, int tetrominoType) {
        this.currentState = 0;
        
        switch (tetrominoType) {
            case 0:
                this.tiles = OType.clone();
                break;
            case 1:
                this.tiles = IType.clone();
                break;
            case 2:
                this.tiles = TType.clone();
                break;
            case 3:
                this.tiles = SType.clone();
                break;
            case 4:
                this.tiles = ZType.clone();
                break;
            case 5:
                this.tiles = JType.clone();
                break;
            case 6:
                this.tiles = LType.clone();
                break;
            default:
                this.tiles = OType.clone();
                break;
        }
        
        switch (colorType) {
            case 0:
                this.color = Color.GREEN;
                break;
            case 1:
                this.color = Color.RED;
                break;
            case 2:
                this.color = Color.ORANGE;
                break;
            case 3:
                this.color = Color.BLUE;
                break;
            case 4:
                this.color = Color.YELLOW;
                break;
            default:
                this.color = Color.PINK;
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
                                             { true, true, true, true },
                                             { false, false, false, false } } };
    
    private final boolean[][][] TType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { false, true, false, false },
                                             { true, true, true, false } },
    
                                           { { false, false, false, false }, 
                                             { false, true, false, false },
                                             { false, true, true, false },
                                             { false, true, false, false } },
    
                                           { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { false, true, true, true },
                                             { false, false, true, false } },
    
                                           { { false, false, false, false }, 
                                             { false, false, true, false },
                                             { false, true, true, false },
                                             { false, false, true, false } }};
    
    private final boolean[][][] ZType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { true, true, false, false },
                                             { false, true, true, false } },
    
                                             { { false, false, false, false }, 
                                             { false, true, false, false },
                                             { true, true, false, false },
                                             { true, false, false, false } }};
    
    private final boolean[][][] SType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { false, true, true, false },
                                             { true, true, false, false } },
    
                                            { { false, false, false, false }, 
                                             { true, false, false, false },
                                             { true, true, false, false },
                                             { false, true, false, false } } };
    
    private final boolean[][][] LType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { false, false, true, false },
                                             { true, true, true, false } },
    
                                            { { false, false, false, false }, 
                                             { false, true, false, false },
                                             { false, true, false, false },
                                             { false, true, true, false } },
    
                                            { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { true, true, true, false },
                                             { true, false, false, false } },
    
                                            { { false, false, false, false }, 
                                             { true, true, false, false },
                                             { false, true, false, false },
                                             { false, true, false, false } } };
    
    private final boolean[][][] JType =  { { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { true, false, false, false },
                                             { true, true, true, false } },
    
                                            { { false, false, false, false }, 
                                             { false, true, true, false },
                                             { false, true, false, false },
                                             { false, true, false, false } },
    
                                            { { false, false, false, false }, 
                                             { false, false, false, false },
                                             { true, true, true, false },
                                             { false, false, true, false } },
    
                                            { { false, false, false, false }, 
                                             { false, true, false, false },
                                             { false, true, false, false },
                                             { true, true, false, false } } };
    
    

    
    void draw(Graphics2D drawCanvas, int row, int column, int width, int height) {
        ArrayList<Tile> tetrominoTiles = this.getTiles(row, column);
        
        for (Tile tempTile : tetrominoTiles) {
            tempTile.draw(drawCanvas, width, height);
        }
    }
    
    public ArrayList<Tile> getTiles(int row, int column) {
        ArrayList<Tile> result = new ArrayList<>();
        
        for (int i = 0; i < this.tiles[currentState].length; i++) {
            for (int j = 0; j < this.tiles[currentState][i].length; j++) {
                if (this.tiles[currentState][i][j]) {
                    result.add(new Tile(this.color, row + i, column + j));
                }
            }
        }
        
        return result;
    }
    
}
