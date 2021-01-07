/**
 * http://www.java2s.com/Tutorial/Java/0240__Swing/GradientLabel.htm
 */

package com.nguy4130;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.JLabel;

public class GradientLabel extends JLabel {

  private Color start;
  private Color end;

  public GradientLabel(String text) {
    super(text);
    start = Color.LIGHT_GRAY;
    end = getBackground();
  }

  public GradientLabel(String text, Color start, Color end) {
    super(text);
    this.start = start;
    this.end = end;
  }

  public void paint(Graphics g) {
    int width = getWidth();
    int height = getHeight();

    // Create the gradient paint
    GradientPaint paint = new GradientPaint(0, 0, start, width, height, end, false);

    // Need to cast to Graphics2D
    Graphics2D g2d = (Graphics2D) g;

    // Save old paint
    Paint oldPaint = g2d.getPaint();

    // Set the paint to use
    g2d.setPaint(paint);

    // Fill the background using paint
    g2d.fillRect(0, 0, width, height);

    // Restore the original paint
    g2d.setPaint(oldPaint);

    super.paint(g);
  }
}
