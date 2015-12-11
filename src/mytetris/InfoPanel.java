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

public class InfoPanel extends JPanel {
    private final Tetris parentGame;
    private final int INFO_WIDTH;
    private final int INFO_HEIGHT;
    private final int SCORE_X;
    private final int SCORE_Y;
    
    public InfoPanel(Tetris parent) {
        this.parentGame = parent;
        System.out.println(parentGame.getBoardWidth());
        INFO_WIDTH = (int)(parentGame.settings.BOARD_WIDTH / 1.5);
        INFO_HEIGHT = parentGame.settings.BOARD_HEIGHT;
        System.out.println((int)(INFO_WIDTH * 0.1));
        System.out.println((int)(INFO_HEIGHT * 0.5));
        SCORE_X = (int)(INFO_WIDTH * 0.1);
        SCORE_Y = (int)(INFO_HEIGHT * 0.5);
        
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
    }
    
    private void drawScore(Graphics2D drawCanvas) {
        drawCanvas.setPaint(Color.white);
        drawCanvas.setFont(new Font("Courier New", Font.BOLD, 20));
        
        drawCanvas.drawString("Score: ", SCORE_X, SCORE_Y - 25);
        drawCanvas.drawString(Integer.toString(parentGame.getScore()), 
                                               SCORE_X, SCORE_Y);
        /*drawCanvas.drawString(Integer.toString(parentGame.getScore())"LOL", 
                                               SCORE_X, SCORE_Y);*/
    }
}
