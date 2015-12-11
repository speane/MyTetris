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

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Tetris extends JFrame 
        implements ActionListener {
    
    private final Random mainRandom = new Random();
    
    private Timer mainTimer;
    private Board board;
    private int playerPoints = 0;
    
    public Tetris() {
        gameInit();
    }
    
    public Tetromino getRandomTetromino() {
        return new Tetromino(mainRandom.nextInt(4), mainRandom.nextInt(7));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Tile> newState = board.currentTetromino.getTiles(board.currentRow + 1, board.currentColumn);
        
        if (isIntersection(newState, board.blocks) || isIntersection(newState, board.bottomBorder)) {
            
            if (isIntersection(newState, board.topBorder)) {
                
                mainTimer.stop();
                this.setVisible(false);
                
                JOptionPane.showMessageDialog(null, "Game over!!!\nYour score: " + playerPoints);
                
                this.dispose();
            }
            else {
                
                ArrayList<Tile> tetrominoTiles = board.currentTetromino.getTiles(board.currentRow, board.currentColumn);
                
                for (Tile tempTile : tetrominoTiles) {
                    board.blocks[tempTile.row][tempTile.column] = tempTile;
                }
                
                this.deleteFullLines();
                board.currentTetromino = getRandomTetromino();
                board.currentRow = -4;
                board.currentColumn = 3;
            }
        }
        else {
            this.board.currentRow++;
        }

        this.board.repaint();
    }
    
    public void deleteFullLines() {
        boolean find;
        Tile[][] newBlocks = board.blocks.clone();

        for (int i = 0; i < board.ROW_COUNT; i++) {
            
            find = true;
            for (int j = 0; j < board.COLUMN_COUNT; j++) {
                
                if (newBlocks[i][j] == null) {
                    
                    find = false;
                    break;
                }
            }
            if (find) {
                
                playerPoints += 50;
                moveLines(newBlocks, i);
            }
        }
        board.blocks = newBlocks;
    }
    
    private void moveLines(Tile[][] blocks, int row) {
        for (int i = row; i > 0; i--) {
            
            for (int j = 0; j < board.COLUMN_COUNT; j++) {
                
                if (blocks[i - 1][j] != null) {
                    
                    blocks[i][j] = new Tile(blocks[i - 1][j].getColor(), i, j);
                }
                else {
                    
                    blocks[i][j] = null;
                }
            }
        }
        
        for (int i = 0; i < board.COLUMN_COUNT; i++) {
            blocks[0][i] = null;
        }
    }
    
    private boolean isIntersection(ArrayList<Tile> tetromino, Tile[][] blocks) {
        for (Tile[] tempRow : blocks) {
            
            for (Tile tempBlock : tempRow) {
                
                if (tempBlock != null) {
                    
                    for (Tile tempTile : tetromino) {
                        
                        if ((tempBlock.getColumn() == tempTile.getColumn()) &&
                            (tempBlock.getRow() == tempTile.getRow())) {
                            
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
                
                if ((tempBlock.getColumn() == tempTile.getColumn()) &&
                    (tempBlock.getRow() == tempTile.getRow())) {
                    
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private void rightKeyClick() {
        ArrayList<Tile> newState = board.currentTetromino.getTiles(board.currentRow, board.currentColumn + 1);
        if (!(isIntersection(newState, board.blocks) || isIntersection(newState, board.rightBorder))) {
            board.currentColumn++;
        }
        
        board.repaint();
    }
    
    private void leftKeyClick() {
        ArrayList<Tile> newState = board.currentTetromino.getTiles(board.currentRow, board.currentColumn - 1);
        if (!(isIntersection(newState, board.blocks) || isIntersection(newState, board.leftBorder))) {
            board.currentColumn--;
        }
        
        board.repaint();
    }
    
    public void initListeners() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        rightKeyClick();
                        break;
                    case KeyEvent.VK_LEFT:
                        leftKeyClick();
                        break;
                    case KeyEvent.VK_ENTER:
                        mainTimer.stop();
                        break;
                    case KeyEvent.VK_I:
                        mainTimer.start();
                        break;
                    case KeyEvent.VK_UP:
                        changeStateClick();
                        break;
                    case KeyEvent.VK_DOWN:
                        mainTimer.setDelay(50);
                        break;
                }
                
            }
            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                        mainTimer.setDelay(300);
                        break;
                }
            }
        });
    }
    
    private void changeStateClick() {
        board.currentTetromino.nextState();
        ArrayList<Tile> tetrominoTiles = board.currentTetromino.getTiles(board.currentRow, board.currentColumn);
        if (isIntersection(tetrominoTiles, board.rightBorder) || 
            isIntersection(tetrominoTiles, board.leftBorder) ||
            isIntersection(tetrominoTiles, board.blocks)) {
            
            board.currentTetromino.prevState();
        }
        board.repaint();
    }
    
    private void gameInit() {
        this.initListeners();
        this.mainTimer = new Timer(300, this);
        this.mainTimer.setInitialDelay(1000);
        this.board = new Board();
        this.add(board);
        this.pack();
        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void start() {
        board.currentTetromino = getRandomTetromino();
        mainTimer.start();
    }
}
