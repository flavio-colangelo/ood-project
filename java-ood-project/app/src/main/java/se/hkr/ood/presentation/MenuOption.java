package se.hkr.ood.presentation;

import java.util.NoSuchElementException;

public class MenuOption {
  private String character;
  private String title;
  private Runnable function;

  public MenuOption(String character, String title) {
    this.character = character;
    this.title = title;
  }

  public MenuOption(String character, String title, Runnable function) {
    this.character = character;
    this.title = title;
    this.function = function;
  }

  public void setFunction(Runnable function) {
    this.function = function;
  }

  public void run() {
    if (function != null) {
      function.run();
    } else {
      throw new NoSuchElementException("The function for this option is missing.");
    }
  }

  public String getCharacter() {
    return this.character;
  }

  @Override
  public String toString() {
    return this.character + ") " + this.title;
  }
}