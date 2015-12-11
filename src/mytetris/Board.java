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
import java.util.ArrayList;
import java.util.Random;


public class Board extends JPanel {
    
    private final int TILE_WIDTH = 30;
    private final int TILE_HEIGHT = 30;
    public final int COLUMN_COUNT = 10;
    public final int ROW_COUNT = 20;
    private final int BOARD_WIDTH = TILE_WIDTH * COLUMN_COUNT;
    private final int BOARD_HEIGHT = TILE_HEIGHT * ROW_COUNT;
    public int CURRENT_ROW = -4;
    public int CURRENT_COLUMN = 3;
    public Tile blocks[][];
    public ArrayList<Tile>[] staticTiles;
    public Tile[] bottomBorder;
    public Tile[] leftBorder;
    public Tile[] rightBorder;
    public Tile[] topBorder;
    
    public Tetromino currentTetromino;
    
    
    
    public Board() {
        
        blocks = new Tile[ROW_COUNT][COLUMN_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                blocks[i][j] = null;
            }
        }
        initBorders();
        init();
    }
    
    private void initBorders() {
        bottomBorder = new Tile[COLUMN_COUNT];
        for (int i = 0; i < COLUMN_COUNT; i++) {
            bottomBorder[i] = new Tile(Color.BLACK, ROW_COUNT, i);
        }
        
        leftBorder = new Tile[ROW_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            leftBorder[i] = new Tile(Color.BLACK, i, -1);
        }
        
        rightBorder = new Tile[ROW_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            rightBorder[i] = new Tile(Color.BLACK, i, COLUMN_COUNT);
        }
        
        topBorder = new Tile[COLUMN_COUNT];
        for (int i = 0; i < COLUMN_COUNT; i++) {
            topBorder[i] = new Tile(Color.BLACK, -1, i);
        }
    }
    
    private void init() {
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
    }
    
    @Override
    public void paintComponent(Graphics drawCanvas) {
        super.paintComponent(drawCanvas);
        
        Graphics copy = drawCanvas.create();
        Graphics2D g2d = (Graphics2D)copy;
        
        
        this.drawBoard(g2d);
        
        this.currentTetromino.draw(g2d, CURRENT_ROW, CURRENT_COLUMN, TILE_WIDTH, TILE_HEIGHT);
        this.drawGrid(g2d);
        
    }
    
    
    
    private void drawGrid(Graphics2D drawCanvas) {
        drawCanvas.setPaint(Color.LIGHT_GRAY);
        for (int i = 1; i < COLUMN_COUNT; i++) {
            drawCanvas.drawLine(i * TILE_WIDTH, 0, i * TILE_WIDTH, BOARD_HEIGHT);
        }

        for (int j = 1; j < ROW_COUNT; j++) {
            drawCanvas.drawLine(0, j * TILE_HEIGHT, BOARD_WIDTH, j * TILE_HEIGHT);
        }
    }
    
    public void printBoard(Tile[][] tiles) {
        System.out.println();
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                if (tiles[i][j] == null) {
                    System.out.print("false  ");
                }
                else {
                    System.out.print("true   ");
                }
            }
            System.out.println();
        }
    }
    
    private void drawBoard(Graphics2D drawCanvas) {
        /*for (Tile tempTile : staticTiles) {
            tempTile.draw(drawCanvas, TILE_WIDTH, TILE_HEIGHT);
        }*/
        
        printBoard(blocks);
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                if (blocks[i][j] != null) {
                    blocks[i][j].draw(drawCanvas, TILE_WIDTH, TILE_HEIGHT);
                }
            }
        }
        printBoard(blocks);
    }
  

}
