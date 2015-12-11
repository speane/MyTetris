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

public class InfoPanel extends JPanel {
    private final Tetris parentGame;
    private final int INFO_WIDTH;
    private final int INFO_HEIGHT;
    
    public InfoPanel(Tetris parent) {
        this.parentGame = parent;
        INFO_WIDTH = parentGame.getWidth();
        INFO_HEIGHT = parentGame.getHeight();
        
        initPanel();
    }
    
    private void initPanel() {
        this.setSize(INFO_WIDTH, INFO_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
    }
}
