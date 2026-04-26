package se.hkr.ood.presentation;

import se.hkr.ood.application.MaterialService;
import se.hkr.ood.application.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {
  private static List<String> basic = Arrays.asList("a) Fetch Product", "b) List all Products", "c) Other", "q) Exit");
  private static List<String> a = Arrays.asList("Insert name", "q) Back");
  private static List<String> a1 = Arrays.asList("The Product doesn't exist", "Create new Product? (Y/N)", "q) Back");
  private static List<String> a11 = Arrays.asList("Introduce category", "q) Back");
  private static List<String> a12 = Arrays.asList("Introduce lifespan", "q) Back");
  private static List<String> a13 = Arrays.asList("Introduce materials (ENTER to confirm)", "q) Back");
  private static List<String> a131 = Arrays.asList("doesn't exist", "Create new Material? (Y/N)", "q) Back");
  private static List<String> a132 = Arrays.asList("Introduce impact value", "q) Back");
  private static List<String> a133 = Arrays.asList("Introduce recycling guidance", "q) Back");
  private static List<String> a2 = Arrays.asList("Calculate impact value?", "a) Simple Sum Strategy",
      "b) Weighted by Lifespan Strategy", "q) Back");
  private static List<String> back = Arrays.asList("q) Back");
  private static List<String> c = Arrays.asList("a) Update Product", "b) Update Material", "q) Back");
  private static List<String> ca = Arrays.asList("Insert Product name", "q) Back");
  private static List<String> ca1 = Arrays.asList("a) Update name", "b) Update category",
      "c) Update estimated lifespan", "Update Materials", "q) Back");
  private static List<String> cb = Arrays.asList("Insert Material name", "q) Back");
  private static List<String> cb1 = Arrays.asList("a) Update name", "b) Update impact value",
      "c) Update recycling guidance", "q) Back");

  private class MenuOption {
    private char character;
    private String title;
    private Runnable function;

    public MenuOption(char character, String title) {
      this.character = character;
      this.title = title;
    }

    public MenuOption(char character, String title, Runnable function) {
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
      }
    }
  }

  public static void startLoop() {
    boolean looping = true;
    while (looping) {
      displayOptions(basic);
      switch (handleUserInput().toLowerCase()) {
        case "a":

          break;
        case "b":

          break;
        case "c":

          break;
        case "q":
          looping = false;
          break;
        default:
          System.out.println("Invalid option, please try again.");
          break;
      }

    }
  }

  protected static void displayOptions(List<String> options) {
    for (int i = 0; i < options.size(); i++) {
      System.out.println(options.get(i));
    }
    System.out.print("> ");
  }

  protected static String handleUserInput() {
    return "x";
  }
}
