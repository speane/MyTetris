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
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

public class Tetris extends JFrame 
        implements ActionListener {
    
    private Timer mainTimer;
    private Board board;
    
    public Tetris() {
        gameInit();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //board.CURRENT_ROW += 1;
        ArrayList<Tile> newState = board.currentTetromino.getTiles(board.CURRENT_ROW + 1, board.CURRENT_COLUMN);
        if (isIntersection(newState, board.blocks) || isIntersection(newState, board.bottomBorder)) {
            ArrayList<Tile> tetrominoTiles = board.currentTetromino.getTiles(board.CURRENT_ROW, board.CURRENT_COLUMN);
            for (Tile tempTile : tetrominoTiles) {
                board.blocks[tempTile.row][tempTile.column] = tempTile;
            }
            board.currentTetromino = new Tetromino(Color.GREEN);
            board.CURRENT_ROW = 0;
            board.CURRENT_COLUMN = 5;
        }
        else {
            board.CURRENT_ROW++;
        }
        
        board.repaint();
        /*ArrayList<Tile> newState = board.currentTetromino.getTiles(board.CURRENT_ROW + 1, board.CURRENT_COLUMN);
        
        if (isIntersection(newState, board.staticTiles) || (board.CURRENT_ROW > 13)) {
            ArrayList<Tile> tetrominoTiles = board.currentTetromino.getTiles(board.CURRENT_ROW, board.CURRENT_COLUMN);
            board.staticTiles.addAll(tetrominoTiles);
            board.currentTetromino = new Tetromino(Color.GREEN);
            board.CURRENT_ROW = 0;
            board.CURRENT_COLUMN = 5;
        }
        board.repaint();*/
        
        /*if (board.CURRENT_ROW >= 13) {
            ArrayList<Tile> tetrominoTiles = board.currentTetromino.getTiles(board.CURRENT_ROW, board.CURRENT_COLUMN);
            board.staticTiles.addAll(tetrominoTiles);
            board.currentTetromino = new Tetromino(Color.GREEN);
            board.CURRENT_ROW = 0;
            board.CURRENT_COLUMN = 5;
        }*/
    }

    private boolean isIntersection(ArrayList<Tile> tetromino, Tile[][] blocks) {
        for (Tile[] tempRow : blocks) {
            for (Tile tempBlock : tempRow) {
                if (tempBlock != null) {
                    for (Tile tempTile : tetromino) {
                        if ((tempBlock.column == tempTile.column) &&
                            (tempBlock.row == tempTile.row)) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean isIntersection(ArrayList<Tile> tetromino, Tile[] border) {
        for (Tile tempBlock : border) {
            for (Tile tempTile : tetromino) {
                if ((tempBlock.column == tempTile.column) &&
                            (tempBlock.row == tempTile.row)) {
                            return true;
                        }
            }
        }
        
        return false;
    }
    
    private void rightKeyClick() {
        ArrayList<Tile> newState = board.currentTetromino.getTiles(board.CURRENT_ROW, board.CURRENT_COLUMN + 1);
        if (isIntersection(newState, board.blocks) || isIntersection(newState, board.rightBorder)) {

        }
        else {
            board.CURRENT_COLUMN++;
        }
        
        board.repaint();
    }
    
    private void leftKeyClick() {
        ArrayList<Tile> newState = board.currentTetromino.getTiles(board.CURRENT_ROW, board.CURRENT_COLUMN - 1);
        if (isIntersection(newState, board.blocks) || isIntersection(newState, board.leftBorder)) {

        }
        else {
            board.CURRENT_COLUMN--;
        }
        
        board.repaint();
    }
    
    public void initListeners() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_D:
                        /*board.CURRENT_COLUMN += 1;
                        board.repaint();*/
                        rightKeyClick();
                        break;
                    case KeyEvent.VK_A:
                        /*board.CURRENT_COLUMN -= 1;
                        board.repaint();*/
                        leftKeyClick();
                        break;
                    case KeyEvent.VK_P:
                        mainTimer.stop();
                        break;
                    case KeyEvent.VK_I:
                        mainTimer.start();
                        break;
                }
                
            }
        });
    }
    
    private void gameInit() {
        this.initListeners();
        this.mainTimer = new Timer(200, this);
        this.mainTimer.setInitialDelay(1000);
        board = new Board();
        this.add(board);
        this.pack();
        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void start() {
        board.currentTetromino = new Tetromino(Color.CYAN);
        mainTimer.start();

    }
}
