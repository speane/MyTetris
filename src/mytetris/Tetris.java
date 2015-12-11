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
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Tetris extends JFrame 
        implements ActionListener {
    
    private Timer mainTimer;
    private Board board;
    private Random mainRandom = new Random();
    private int playerPoints = 0;
    
    public Tetris() {
        gameInit();
    }
    
    public Tetromino getRandomTetromino() {
        
        return new Tetromino(mainRandom.nextInt(4), mainRandom.nextInt(7));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //board.CURRENT_ROW += 1;
        //printBoard(board.blocks);
        ArrayList<Tile> newState = board.currentTetromino.getTiles(board.CURRENT_ROW + 1, board.CURRENT_COLUMN);
        if (isIntersection(newState, board.blocks) || isIntersection(newState, board.bottomBorder)) {
            if (isIntersection(newState, board.topBorder)) {
                mainTimer.stop();
                this.setVisible(false);
                JOptionPane.showMessageDialog(null, "Game over!!!\nYour score: " + playerPoints);
                
                this.dispose();
            }
            else {
                ArrayList<Tile> tetrominoTiles = board.currentTetromino.getTiles(board.CURRENT_ROW, board.CURRENT_COLUMN);
                for (Tile tempTile : tetrominoTiles) {
                    board.blocks[tempTile.row][tempTile.column] = tempTile;
                }
                this.deleteFullLines();
                //board.currentTetromino = new Tetromino(Color.RED, 1);
                board.currentTetromino = getRandomTetromino();
                board.CURRENT_ROW = -4;
                board.CURRENT_COLUMN = 3;
            }
        }
        else {
            board.CURRENT_ROW++;
        }
        
        //printBoard(board.blocks);
        board.repaint();
        //printBoard(board.blocks);
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

    /*public void printBoard(Tile[][] tiles) {
        System.out.println();
        for (int i = 0; i < board.ROW_COUNT; i++) {
            for (int j = 0; j < board.COLUMN_COUNT; j++) {
                if (tiles[i][j] == null) {
                    System.out.print("false  ");
                }
                else {
                    System.out.print("true   ");
                }
            }
            System.out.println();
        }
    }*/
    
    public void deleteFullLines() {
        boolean find;
        Tile[][] newBlocks = board.blocks.clone();
        //printBoard(board.blocks);
        
        for (int i = 0; i < board.ROW_COUNT; i++) {
            find = true;
            for (int j = 0; j < board.COLUMN_COUNT; j++) {
                if (newBlocks[i][j] == null) {
                    find = false;
                    break;
                }
            }
            if (find) {
                
                //printBoard(board.blocks);
                playerPoints += 50;
                moveLines(newBlocks, i);
               // printBoard(board.blocks);
            }
        }
        
        board.blocks = newBlocks;
       // printBoard(board.blocks);
    }
    
    private void moveLines(Tile[][] blocks, int row) {
        //Tile[][] blocks = this.board.blocks.clone();
        for (int i = row; i > 0; i--) {
            for (int j = 0; j < board.COLUMN_COUNT; j++) {
                if (blocks[i - 1][j] != null) {
                    blocks[i][j] = new Tile(blocks[i - 1][j].getColor(), i, j);
                }
                else {
                    blocks[i][j] = null;
                }
            }
            //blocks[i] = blocks[i - 1];
        }
        
        for (int i = 0; i < board.COLUMN_COUNT; i++) {
            blocks[0][i] = null;
        }
        
        //this.board.blocks = blocks.clone();
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
                    case KeyEvent.VK_RIGHT:
                        /*board.CURRENT_COLUMN += 1;
                        board.repaint();*/
                        rightKeyClick();
                        break;
                    case KeyEvent.VK_LEFT:
                        /*board.CURRENT_COLUMN -= 1;
                        board.repaint();*/
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
        ArrayList<Tile> tetrominoTiles = board.currentTetromino.getTiles(board.CURRENT_ROW, board.CURRENT_COLUMN);
        if (isIntersection(tetrominoTiles, board.rightBorder) || 
                isIntersection(tetrominoTiles, board.rightBorder) ||
                        isIntersection(tetrominoTiles, board.blocks)) {
            board.currentTetromino.prevState();
        }
        board.repaint();
    }
    
    private void gameInit() {
        this.initListeners();
        this.mainTimer = new Timer(300, this);
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
        board.currentTetromino = getRandomTetromino();
        mainTimer.start();

    }
}
