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
import java.awt.Graphics2D;

public class Tile {
    public Tile(Color color, int row, int column) {
        this.color = color;
        this.row = row;
        this.column = column;
    }
    
    public int row;
    public int column;
    
    public int getRow() {
        return this.row;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    
    public int getColumn() {
        return this.column;
    }
    
    public void setColumn(int column) {
        this.column = column;
    }
    
    private Color color;
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void draw(Graphics2D drawCanvas, int width, int height) {
        drawCanvas.setPaint(color);
        drawCanvas.fillRect(column * width, row * height, width, height);
    }
}
