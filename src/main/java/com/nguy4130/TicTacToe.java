package com.nguy4130;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  static int HUMAN_MODE = 1;
  static int AI_MODE = 2;
  static String HUMAN = "Player";
  static String AI = "AI";
  static String HUMAN_1 = "Player 1";
  static String HUMAN_2 = "Player 2";
  final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

  /**
   * GUI components
   */
  Random random = new Random();
  JFrame gameFrame = new JFrame();
  JPanel titlePanel = new JPanel();
  JPanel optionPanel = new JPanel();
  JButton humanVsHumanButton = new JButton("Player 1 vs Player 2");
  JButton humanVsAiButton = new JButton("Player vs AI");
  JButton restartButton = new JButton("Restart");
  JPanel squaresPanel = new JPanel();
  JLabel textField = new GradientLabel("Tic-Tac-Toe", MAROON, GOLD);
  JLabel test = new GradientLabel("Tic-Tac-Toe", MAROON, GOLD);
  JButton[][] buttons = new JButton[3][3];
  boolean player1Turn;
  TicTacToePlayer player1;
  TicTacToePlayer player2;
  int playingMode = 0; // 1 for p1vsp2 and 2 for p1vsAI
  boolean playingWithAI;

  TicTacToe() {

    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setTitle("Tic-Tac-Toe");
    gameFrame.setSize(800, 800);
    gameFrame.getContentPane().setBackground(new Color(50, 50, 50));
    gameFrame.setLayout(new BorderLayout());
    gameFrame.setVisible(true);

    textField.setForeground(Color.BLACK);
    textField.setFont(new Font("Ink Free", Font.BOLD, 75));
    textField.setHorizontalAlignment(JLabel.CENTER);
    textField.setText("Choose Playing Mode");
    textField.setOpaque(false);

    titlePanel.setLayout(new BorderLayout());
    titlePanel.setBounds(0, 0, 800, 100);

    humanVsAiButton.addActionListener(this);
    humanVsHumanButton.addActionListener(this);
    restartButton.addActionListener(this);

    optionPanel.setLayout(new FlowLayout());
    optionPanel.setBounds(0,0, 800,50);
    optionPanel.add(humanVsAiButton);
    optionPanel.add(humanVsHumanButton);
    optionPanel.add(restartButton);

    squaresPanel.setLayout(new GridLayout(3, 3));
    squaresPanel.setBackground(new Color(150, 150, 150));

    for (int i = 0; i < buttons.length; i++) {
      for (int j = 0; j < buttons[i].length; j++) {
        buttons[i][j] = new JButton();
        squaresPanel.add(buttons[i][j]);
        buttons[i][j].setFont(new Font("Ink Free", Font.BOLD, 120));
        buttons[i][j].setFocusable(false);
        buttons[i][j].addActionListener(this);
        buttons[i][j].setEnabled(false);
      }
    }

    titlePanel.add(textField, BorderLayout.NORTH);
    titlePanel.add(optionPanel, BorderLayout.SOUTH);

    gameFrame.add(titlePanel, BorderLayout.NORTH);
    gameFrame.add(squaresPanel, BorderLayout.CENTER);



//    firstTurn();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source == humanVsAiButton) {
      LOGGER.log(Level.INFO, "Playing against AI");
      textField.setText("Human vs AI");
      humanVsAiButton.setEnabled(false);
      humanVsHumanButton.setEnabled(false);
      enableSquares(true);
      player1 = new TicTacToePlayer(HUMAN, X);
      player2 = new TicTacToePlayer(AI, O);
      playingWithAI = true;
      firstTurn();
    }
    else if (source == humanVsHumanButton) {
      LOGGER.log(Level.INFO, "Playing against another player");
      textField.setText("Player 1 vs Player 2");
      humanVsAiButton.setEnabled(false);
      humanVsHumanButton.setEnabled(false);
      enableSquares(true);
      player1 = new TicTacToePlayer(HUMAN_1, X);
      player2 = new TicTacToePlayer(HUMAN_2, O);
      playingMode = HUMAN_MODE;
      playingWithAI = false;
      firstTurn();
    }
    else if (source == restartButton) {
      LOGGER.log(Level.INFO, "Restarting the game");
      enableSquares(false);
      humanVsHumanButton.setEnabled(true);
      humanVsAiButton.setEnabled(true);
      textField.setText("Choose Playing Mode");
    }
    else if (source instanceof JButton) {
      for (int i = 0; i < buttons.length; i++) {
        for (int j = 0; j < buttons[i].length; j++) {
          if (source == buttons[i][j]) {
            if (buttons[i][j].getText().length() == 0) {
              if (player1Turn) {
                buttons[i][j].setForeground(MAROON);
                buttons[i][j].setText(X);
                player1Turn = false;
                textField.setText(player2.getName() + "'s turn");
                if(playingWithAI) {
                  makeAIMove();
                }
              }
              else if(!playingWithAI) {
                buttons[i][j].setForeground(GOLD);
                buttons[i][j].setText(O);
                player1Turn = true;
                textField.setText(player1.getName() + "'s turn");
              }
              check();
            }
          }
        }
      }
    }
  }

  public void makeAIMove() {
    player1Turn = false;
    textField.setText(player2.getName() + "'s turn");
    List<JButton> emptyList = new ArrayList<>();
    int emptyNum = 0;
    for(JButton[] squareRow : buttons) {
      for(JButton button : squareRow) {
        if(button.getText().length() == 0) {
          emptyList.add(button);
          emptyNum++;
        }
      }
    }

    int chosenAISquare = random.nextInt(emptyNum);
    emptyList.get(chosenAISquare).setForeground(GOLD);
    emptyList.get(chosenAISquare).setText(O);
    player1Turn = true;
    textField.setText(player1.getName() + "'s turn");
  }

  public void enableSquares(boolean on) {
    for (JButton[] squaresRow : buttons) {
      for (JButton square : squaresRow) {
        if (!on) {
          square.setText("");
        }
        square.setEnabled(on);
      }
    }
  }
  public void preGame() {
    textField.setText("Choose Playing Mode");
  }

  public void firstTurn() {

//    try {
//      Thread.sleep(2000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    textfield.setText("Choose Playing Mode");
    if (random.nextInt(2) == 0) {
      // Human's turn
      player1Turn = true;
      textField.setText(player1.getName() +"'s turn");

    } else {
      // AI's turn
      player1Turn = false;
      textField.setText(player2.getName() + "'s turn");
      if(playingWithAI) {
        makeAIMove();
      }
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
      textField.setText(player1.getName() + " wins!");
    } else {
      textField.setText(player2.getName() + " wins!");
    }
  }

  public void announceDraw() {
    for (JButton[] row : buttons) {
      for (JButton square : row) {
        square.setEnabled(false);
      }
    }
    textField.setText("Draw!");
  }


  public void bestMove() {
    // AI's turn
    int bestScore = Integer.MIN_VALUE;

  }
}