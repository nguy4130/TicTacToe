package com.nguy4130;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToe implements ActionListener {

  /**
   * Constant values
   */
  static int COLUMN = 0;
  static int ROW = 1;
  static int DIAGONAL = 2;
  static String X = "X";
  static String O = "O";
  static Color MAROON = new Color(128, 0, 0, 255);
  static Color GOLD = new Color(255, 215, 0, 255);

  /**
   * GUI components
   */
  Random random = new Random();
  JFrame frame = new JFrame();
  JPanel titlePanel = new JPanel();
  JPanel squaresPanel = new JPanel();
  JLabel textfield = new GradientLabel("Tic-Tac-Toe", MAROON, GOLD);
  JButton[][] buttons = new JButton[3][3];
  boolean humanTurn;


  TicTacToe() {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 800);
    frame.getContentPane().setBackground(new Color(50, 50, 50));
    frame.setLayout(new BorderLayout());
    frame.setVisible(true);

    textfield.setBackground(new Color(25, 25, 25));
    textfield.setForeground(new Color(25, 255, 0));
    textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
    textfield.setHorizontalAlignment(JLabel.CENTER);
    textfield.setText("Tic-Tac-Toe");
    textfield.setOpaque(true);

    titlePanel.setLayout(new BorderLayout());
    titlePanel.setBounds(0, 0, 800, 100);

    squaresPanel.setLayout(new GridLayout(3, 3));
    squaresPanel.setBackground(new Color(150, 150, 150));

    for (int i = 0; i < buttons.length; i++) {
      for (int j = 0; j < buttons[i].length; j++) {
        buttons[i][j] = new JButton();
        squaresPanel.add(buttons[i][j]);
        buttons[i][j].setFont(new Font("Menlo for Powerline", Font.BOLD, 120));
        buttons[i][j].setFocusable(false);
        buttons[i][j].addActionListener(this);
      }
    }

    titlePanel.add(textfield);
    frame.add(titlePanel, BorderLayout.NORTH);
    frame.add(squaresPanel);

    firstTurn();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < buttons.length; i++) {
      for (int j = 0; j < buttons[i].length; j++) {
        if (e.getSource() == buttons[i][j]) {
          if (humanTurn) {
            if (buttons[i][j].getText().length() == 0) {
              buttons[i][j].setForeground(MAROON);
              buttons[i][j].setText(X);
              humanTurn = false;
              textfield.setText("O turn");
              check();
            }
          } else {
            if (buttons[i][j].getText().length() == 0) {
              buttons[i][j].setForeground(GOLD);
              buttons[i][j].setText(O);
              humanTurn = true;
              textfield.setText("X turn");
              check();
            }
          }
        }
      }
    }
  }

  public void firstTurn() {

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (random.nextInt(2) == 0) {
      humanTurn = true;
      textfield.setText("X turn");

    } else {
      humanTurn = false;
      textfield.setText("O turn");
    }
  }

  public void check() {
    // Check winning combination horizontally
    for (int i = 0; i < buttons.length; i++) {
      if (buttons[i][0].getText().equals(X)
              && buttons[i][1].getText().equals(X)
              && buttons[i][2].getText().equals(X)) {
        announceWinner(X, ROW, i);
        return;
      } else if (buttons[i][0].getText().equals(O)
              && buttons[i][1].getText().equals(O)
              && buttons[i][2].getText().equals(O)) {
        announceWinner(O, ROW, i);
        return;
      }
    }

    // Check winning combination vertically
    for (int j = 0; j < buttons[0].length; j++) {
      if (buttons[0][j].getText().equals(X)
              && buttons[1][j].getText().equals(X)
              && buttons[2][j].getText().equals(X)) {
        announceWinner(X, COLUMN, j);
        return;
      } else if (buttons[0][j].getText().equals(O)
              && buttons[1][j].getText().equals(O)
              && buttons[2][j].getText().equals(O)) {
        announceWinner(O, COLUMN, j);
        return;
      }
    }

    // Check winning combination diagonally
    if (buttons[0][0].getText().equals(X)
            && buttons[1][1].getText().equals(X)
            && buttons[2][2].getText().equals(X)) {
      announceWinner(X, DIAGONAL, 1);
      return;
    } else if (buttons[0][0].getText().equals(O)
            && buttons[1][1].getText().equals(O)
            && buttons[2][2].getText().equals(O)) {
      announceWinner(O, DIAGONAL, 1);
      return;
    } else if (buttons[0][2].getText().equals(X)
            && buttons[1][1].getText().equals(X)
            && buttons[2][0].getText().equals(X)) {
      announceWinner(X, DIAGONAL, 2);
      return;
    } else if (buttons[0][2].getText().equals(O)
            && buttons[1][1].getText().equals(O)
            && buttons[2][0].getText().equals(O)) {
      announceWinner(O, DIAGONAL, 2);
      return;
    }

    //Check draw if all squares have been filled
    for (JButton[] buttonRow : buttons) {
      for (JButton square : buttonRow) {
        if(square.getText().length() == 0) {
          return;
        }
      }
    }
    announceDraw();
  }

  public void announceWinner(String player, int winningPattern, int num) {
    if (winningPattern == ROW) {
      for (int col = 0; col < buttons[0].length; col++) {
        buttons[num][col].setBackground(Color.GREEN);
      }
    } else if (winningPattern == COLUMN) {
      for (int row = 0; row < buttons.length; row++) {
        buttons[row][num].setBackground(Color.GREEN);
      }
    } else if (winningPattern == DIAGONAL && num == 1) {
      buttons[0][0].setBackground(Color.GREEN);
      buttons[1][1].setBackground(Color.GREEN);
      buttons[2][2].setBackground(Color.GREEN);
    } else {
      buttons[0][2].setBackground(Color.GREEN);
      buttons[1][1].setBackground(Color.GREEN);
      buttons[2][0].setBackground(Color.GREEN);
    }
    for (JButton[] row : buttons) {
      for (JButton square : row) {
        square.setEnabled(false);
      }
    }
    if (player.equals(X)) {
      textfield.setText("X wins!");
    } else {
      textfield.setText("O wins!");
    }
  }

  public void announceDraw() {
    for (JButton[] row : buttons) {
      for (JButton square : row) {
        square.setEnabled(false);
      }
    }
    textfield.setText("Draw!");
  }

  public void bestMove() {
    // AI's turn
    int bestScore = Integer.MIN_VALUE;

  }
}