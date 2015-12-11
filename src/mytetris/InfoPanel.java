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

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.util.ArrayList;

public class InfoPanel extends JPanel {
    private final Tetris parentGame;
    private final int INFO_WIDTH;
    private final int INFO_HEIGHT;
    private final int SCORE_X;
    private final int SCORE_Y;
    private final int NEXT_TET_X;
    private final int NEXT_TET_Y;
    private final int NEXT_TET_WIDTH;
    private final int NEXT_TET_HEIGHT;
    private final int NEXT_TILE_WIDTH;
    private final int NEXT_TILE_HEIGHT;
    private final int NEXT_TET_SHIFT_X;
    private final int NEXT_TET_SHIFT_Y;
    
    public InfoPanel(Tetris parent) {
        this.parentGame = parent;
        
        INFO_WIDTH = (int)(parentGame.settings.BOARD_WIDTH / 1.5);
        INFO_HEIGHT = parentGame.settings.BOARD_HEIGHT;
        
        SCORE_X = (int)(INFO_WIDTH * 0.1);
        SCORE_Y = (int)(INFO_HEIGHT * 0.5);
        
        NEXT_TET_X = (int)(INFO_WIDTH * 0.1);
        NEXT_TET_Y = (int)(INFO_HEIGHT * 0.1);
        NEXT_TET_WIDTH = (int)(INFO_WIDTH * 0.8);
        NEXT_TET_HEIGHT = (int)(INFO_HEIGHT * 0.25);
        
        NEXT_TILE_WIDTH = (int)(NEXT_TET_WIDTH * 0.16);
        NEXT_TILE_HEIGHT = (int)(NEXT_TET_HEIGHT * 0.16);
        
        NEXT_TET_SHIFT_X = (int)(NEXT_TET_WIDTH * 0.25);
        NEXT_TET_SHIFT_Y = (int)(NEXT_TET_HEIGHT * 0.1);
        
        initPanel();
    }
    
    private void initPanel() {
        this.setPreferredSize(new Dimension(INFO_WIDTH, INFO_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics drawCanvas) {
        super.paintComponent(drawCanvas);
        Graphics2D g2d = (Graphics2D)(drawCanvas.create());
        
        this.drawPanel(g2d);
        
        g2d.dispose();
    }
    
    private void drawPanel(Graphics2D drawCanvas) {
        this.drawScore(drawCanvas);
        this.drawNextTetromino(drawCanvas, this.parentGame.getNextTetromino());
    }
    
    private void drawNextTetromino(Graphics2D drawCanvas, Tetromino drawTet) {
        drawCanvas.setPaint(Color.WHITE);
        drawCanvas.drawRect(NEXT_TET_X, NEXT_TET_Y,
                            NEXT_TET_WIDTH, NEXT_TET_HEIGHT);
        
        ArrayList<Tile> tiles = drawTet.getTiles(0, 0);
        
        drawCanvas.setPaint(tiles.get(1).getColor());
        for (Tile tempTile : tiles) {
            drawCanvas.fillRect(NEXT_TET_X + tempTile.getColumn() * 
                                    NEXT_TILE_WIDTH  + NEXT_TET_SHIFT_X, 
                                NEXT_TET_Y + tempTile.getRow() *
                                    NEXT_TILE_HEIGHT + NEXT_TET_SHIFT_Y, 
                                NEXT_TILE_WIDTH, NEXT_TILE_HEIGHT);
        }
    }
    
    private void drawScore(Graphics2D drawCanvas) {
        drawCanvas.setPaint(Color.white);
        drawCanvas.setFont(new Font("Courier New", Font.BOLD, 20));
        
        drawCanvas.drawString("Score: ", SCORE_X -5, SCORE_Y - 25);
        drawCanvas.drawString(Integer.toString(parentGame.getScore()), 
                                               SCORE_X, SCORE_Y);
        
        drawCanvas.drawString("Level: ", SCORE_X -5, SCORE_Y + 25);
        drawCanvas.drawString(Integer.toString(parentGame.getLevel()), 
                                               SCORE_X, SCORE_Y + 50);
    }
}
