package com.nguy4130;

public class TicTacToePlayer {
  private String name;
  private String isXorO;

  public TicTacToePlayer(String name, String isXorO) {
    this.name = name;
    this.isXorO = isXorO;
  }

  public String getName() {
    return this.name;
  }

  public String getIsXorO() {
    return this.isXorO;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIsXorO(String isXorO) {
    this.isXorO = isXorO;
  }
}
