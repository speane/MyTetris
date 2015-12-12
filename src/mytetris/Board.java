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
import java.awt.Font;

public class Board extends JPanel {
    
    private final int TILE_WIDTH = 30;
    
    private static final GameSettings settings = new GameSettings();
    
    public final int MAX_COLOR_NUMBER = 4;
    public final int MAX_TYPE_NUMBER = 7;
    
    private final int TILE_HEIGHT = 30;
    public final int COLUMN_COUNT = 10;
    public final int ROW_COUNT = 20;
    private final int BOARD_WIDTH = 100;
    private final int BOARD_HEIGHT = TILE_HEIGHT * ROW_COUNT;
    private final int PAUSED_MESSAGE_X = 30;
    private final int PAUSED_MESSAGE_Y = BOARD_HEIGHT / 2;
    
    public int currentRow = -4;
    public int currentColumn = 3;
    public Tile blocks[][];
    public ArrayList<Tile>[] staticTiles;
    public Tile[] bottomBorder;
    public Tile[] leftBorder;
    public Tile[] rightBorder;
    public Tile[][] topBorder;
    private Tetris parentGame;
    
    public Tetromino currentTetromino;
    public Tetromino nextTetromino;
    
    
    public Board(Tetris game) {
        this.parentGame = game;
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
        
        leftBorder = new Tile[ROW_COUNT + 4];
        for (int i = 0; i < ROW_COUNT + 4; i++) {
            leftBorder[i] = new Tile(Color.BLACK, i - 4, -1);
        }
        
        rightBorder = new Tile[ROW_COUNT + 4];
        for (int i = 0; i < ROW_COUNT + 4; i++) {
            rightBorder[i] = new Tile(Color.BLACK, i - 4, COLUMN_COUNT);
        }
        
        topBorder = new Tile[5][COLUMN_COUNT];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++)
                topBorder[i][j] = new Tile(Color.BLACK, -i, j);
        }
    }
    
    private void drawPaused(Graphics2D drawCanvas) {
        if (parentGame.gamePaused) {
            drawCanvas.setPaint(Color.WHITE);
            drawCanvas.setFont(new Font("Courier New", Font.BOLD, 65));
            drawCanvas.drawString("PAUSED", PAUSED_MESSAGE_X, PAUSED_MESSAGE_Y);
        }
    }
    
    private void drawGameOver(Graphics2D drawCanvas) {
        if (parentGame.gameOver) {
            drawCanvas.setPaint(Color.BLACK);
            drawCanvas.setFont(new Font("Courier New", Font.BOLD, 45));
            drawCanvas.drawString("Game Over", PAUSED_MESSAGE_X, PAUSED_MESSAGE_Y - 100);
            
            
            drawCanvas.setFont(new Font("Courier New", Font.BOLD, 40));
            drawCanvas.drawString("Points " + parentGame.getScore(),
                                  PAUSED_MESSAGE_X + 5, PAUSED_MESSAGE_Y);
            
            if (parentGame.getScore() > Tetris.settings.HIGH_SCORE) {
                
                Tetris.settings.HIGH_SCORE = parentGame.getScore();
                drawCanvas.setFont(new Font("Courier New", Font.BOLD, 30));
                drawCanvas.drawString("New HighScore!!!", PAUSED_MESSAGE_X - 15, PAUSED_MESSAGE_Y + 100);
            }
        }
    }
    
    private void init() {
        this.setPreferredSize(new Dimension(settings.BOARD_WIDTH, settings.BOARD_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
    }
    
    @Override
    public void paintComponent(Graphics drawCanvas) {
        super.paintComponent(drawCanvas);
        
        Graphics copy = drawCanvas.create();
        Graphics2D g2d = (Graphics2D)copy;
        
        this.drawBoard(g2d);
        this.currentTetromino.draw(g2d, currentRow, currentColumn, TILE_WIDTH, TILE_HEIGHT);
        this.drawGrid(g2d);
        this.drawPaused(g2d);
        this.drawGameOver(g2d);
        
        g2d.dispose();
    }
    
    private void drawGrid(Graphics2D drawCanvas) {
        drawCanvas.setPaint(Color.LIGHT_GRAY);
        
        for (int i = 1; i < settings.COLUMN_COUNT; i++) {
            drawCanvas.drawLine(i * TILE_WIDTH, 0, i * TILE_WIDTH, settings.BOARD_HEIGHT);
        }

        for (int j = 1; j < settings.ROW_COUNT; j++) {
            drawCanvas.drawLine(0, j * TILE_HEIGHT, settings.BOARD_WIDTH, j * TILE_HEIGHT);
        }
    }
    
    private void drawBoard(Graphics2D drawCanvas) {

        for (int i = 0; i < ROW_COUNT; i++) {
            
            for (int j = 0; j < COLUMN_COUNT; j++) {
                
                if (blocks[i][j] != null) {
                    
                    blocks[i][j].draw(drawCanvas, TILE_WIDTH, TILE_HEIGHT);
                }
            }
        }
    }
  

}
