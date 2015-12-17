
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
import java.awt.BorderLayout;

public class Tetris extends JFrame 
        implements ActionListener {
    
    private final Random mainRandom = new Random();
    
    public static GameSettings settings = new GameSettings();
    private Timer mainTimer;
    private Board board;
    private InfoPanel sidePanel;
    private int playerPoints = 0;
    public boolean gameOver = false;
    public boolean gamePaused = false;
    private int level = 0;
    private int newPoints = 0;
    private int timerDelay = settings.TIMER_DELAY;
    private final int timerInitDelay = settings.TIMER_INITIAL_DELAY;
    private final int timerDelayChange = settings.TIMER_DELAY_CHANGE;
    private final JFrame parentFrame;
    
    public Tetris(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        gameInit();
    }
    
    public int getScore() {
        return playerPoints;
    }
    
    public int getBoardWidth() {
        return Tetris.settings.BOARD_WIDTH;
    }
    
    public Tetromino getNextTetromino() {
        return this.board.nextTetromino;
    }
    
    public Tetromino getRandomTetromino() {
        return new Tetromino(mainRandom.nextInt(board.MAX_COLOR_NUMBER), 
                             mainRandom.nextInt(board.MAX_TYPE_NUMBER));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Tile> newState = 
                board.currentTetromino.getTiles(board.currentRow + 1, board.currentColumn);
        
        if (isIntersection(newState, board.blocks) || 
            isIntersection(newState, board.bottomBorder)) {
            
            if (isIntersection(newState, board.topBorder)) {
                gameOver = true;
                mainTimer.stop();
            }
            else {
                
                ArrayList<Tile> tetrominoTiles = 
                        board.currentTetromino.getTiles(board.currentRow, board.currentColumn);
                
                for (Tile tempTile : tetrominoTiles) {
                    board.blocks[tempTile.row][tempTile.column] = tempTile;
                }
                
                this.deleteFullLines();
                board.currentTetromino = board.nextTetromino;
                board.currentRow = settings.INIT_ROW_NUMBER;
                board.currentColumn = settings.INIT_COLUMN_NUMBER;
                
                board.nextTetromino = getRandomTetromino();
            }
        }
        else {
            this.board.currentRow++;
        }

        this.repaintGame();
    }
    
    public int getLevel() {
        return this.level;
    }
    
    private void repaintGame() {
        this.board.repaint();
        this.sidePanel.repaint();
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
                
                playerPoints += settings.ADD_POINTS;
                newPoints += settings.ADD_POINTS;
                
                if (newPoints >= settings.NEW_LEVEL_POINTS) {
                    this.level++;
                    newPoints = 0;
                    this.timerDelay -= this.timerDelayChange;
                }
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
        ArrayList<Tile> newState = 
                board.currentTetromino.getTiles(board.currentRow, 
                                                board.currentColumn + 1);
        
        if (!(isIntersection(newState, board.blocks) || 
              isIntersection(newState, board.rightBorder))) {
            
            board.currentColumn++;
        }
        
        board.repaint();
    }
    
    private void leftKeyClick() {
        ArrayList<Tile> newState = 
                board.currentTetromino.getTiles(board.currentRow, 
                                                board.currentColumn - 1);
        
        if (!(isIntersection(newState, board.blocks) || 
              isIntersection(newState, board.leftBorder))) {
            
            board.currentColumn--;
        }
        
        board.repaint();
    }
    
    public void initListeners() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    closeGame();
                }
                
                if (!gameOver && !gamePaused) {
                    switch(e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT:
                            rightKeyClick();
                            break;
                        case KeyEvent.VK_LEFT:
                            leftKeyClick();
                            break;
                        case KeyEvent.VK_UP:
                            changeStateClick();
                            break;
                        case KeyEvent.VK_DOWN:
                            mainTimer.setDelay(settings.TIMER_FAST_DELAY);
                            break;
                    }  
                }
                if (!gameOver) {
                    if (KeyEvent.VK_ENTER == e.getKeyCode())
                            if (!gamePaused) {
                                mainTimer.stop();
                                gamePaused = true;
                            }
                            else {
                                mainTimer.start();
                                gamePaused = false;
                            }
                    board.repaint();
                }
                else {
                    if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                        closeGame();
                    }
                }
                
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (!gameOver && !gamePaused) {
                    switch(e.getKeyCode()) {
                        case KeyEvent.VK_DOWN:
                            mainTimer.setDelay(timerDelay);
                            break;
                    }
                }
            }
        });
    }
    
    private void closeGame() {
        this.setVisible(false);
        this.parentFrame.setVisible(true);
        this.dispose();
    }
    
    private void changeStateClick() {
        board.currentTetromino.nextState();
        
        ArrayList<Tile> tetrominoTiles = 
                board.currentTetromino.getTiles(board.currentRow, 
                                                board.currentColumn);
        
        if (isIntersection(tetrominoTiles, board.rightBorder) || 
            isIntersection(tetrominoTiles, board.leftBorder) ||
            isIntersection(tetrominoTiles, board.blocks)) {
            
            board.currentTetromino.prevState();
        }
        
        board.repaint();
    }
    
    private void gameInit() {
        this.initListeners();
        this.mainTimer = new Timer(this.timerDelay, this);
        this.mainTimer.setInitialDelay(this.timerInitDelay);
        this.board = new Board(this);
        this.sidePanel = new InfoPanel(this);
        
        this.setLayout(new BorderLayout());
        this.add(board, BorderLayout.CENTER);
        this.add(sidePanel, BorderLayout.EAST);
        this.setResizable(false);
        
        this.pack();
        this.setTitle("Tetris");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        if (this.getWidth() == 489) {
            this.setSize(499, 630);
        }
    }
    
    public void start() {
        
        board.currentTetromino = getRandomTetromino();
        board.nextTetromino = getRandomTetromino();
        mainTimer.start();
    }
}
