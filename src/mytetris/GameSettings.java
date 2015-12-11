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
public class GameSettings {
    public int ROW_COUNT = 20;
    public int COLUMN_COUNT = 10;
    public int ADD_POINTS = 50;
    public int TIMER_DELAY = 300;
    public int TIMER_INITIAL_DELAY = 500;
    public int TIMER_FAST_DELAY = 20;
    
    public int TILE_WIDTH;
    public int TILE_HEIGHT;
    public int BOARD_WIDTH;
    public int BOARD_HEIGHT;
    public int INIT_COLUMN_NUMBER = COLUMN_COUNT / 2 - 2;
    public int INIT_ROW_NUMBER = -4;
    
}
