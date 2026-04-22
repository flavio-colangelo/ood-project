package se.hkr.ood.presentation;

import se.hkr.ood.application.MaterialService;
import se.hkr.ood.application.ProductService;

public class Menu {

  public static void startLoop() {
    displayOptions();
    ProductService.fetchProduct(handleUserInput());
  }

  protected static void displayOptions() {

  }

  protected static String handleUserInput() {
    return "x";
  }
}
