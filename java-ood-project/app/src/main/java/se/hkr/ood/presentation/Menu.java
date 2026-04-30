package se.hkr.ood.presentation;

import se.hkr.ood.application.MaterialService;
import se.hkr.ood.application.ProductService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu {
  // private static List<String> basic = Arrays.asList("a) Fetch Product", "b)
  // List all Products", "c) Other", "q) Exit");
  // private static List<String> a = Arrays.asList("Insert name", "q) Back");
  // private static List<String> a1 = Arrays.asList("The Product doesn't exist",
  // "Create new Product? (Y/N)", "q) Back");
  // private static List<String> a11 = Arrays.asList("Introduce category", "q)
  // Back");
  // private static List<String> a12 = Arrays.asList("Introduce lifespan", "q)
  // Back");
  // private static List<String> a13 = Arrays.asList("Introduce materials (ENTER
  // to confirm)", "q) Back");
  // private static List<String> a131 = Arrays.asList("doesn't exist", "Create new
  // Material? (Y/N)", "q) Back");
  // private static List<String> a132 = Arrays.asList("Introduce impact value",
  // "q) Back");
  // private static List<String> a133 = Arrays.asList("Introduce recycling
  // guidance", "q) Back");
  // private static List<String> a2 = Arrays.asList("Calculate impact value?", "a)
  // Simple Sum Strategy",
  // "b) Weighted by Lifespan Strategy", "q) Back");
  // private static List<String> back = Arrays.asList("q) Back");
  // private static List<String> c = Arrays.asList("a) Update Product", "b) Update
  // Material", "q) Back");
  // private static List<String> ca = Arrays.asList("Insert Product name", "q)
  // Back");
  // private static List<String> ca1 = Arrays.asList("a) Update name", "b) Update
  // category",
  // "c) Update estimated lifespan", "Update Materials", "q) Back");
  // private static List<String> cb = Arrays.asList("Insert Material name", "q)
  // Back");
  // private static List<String> cb1 = Arrays.asList("a) Update name", "b) Update
  // impact value",
  // "c) Update recycling guidance", "q) Back");

  private static String back = "q) Back";

  private static void fetchProductOption(Scanner scanner) {
    // System.out.println("This fetches the product!");
    // List<MenuOption> fetchProduct = new LinkedList<>();
    // fetchProduct.add(new MenuOption(() -> {}, "Insert Name", back));
    // fetchProduct.add(new MenuOption(() -> {}, "Calculate impact value?"));
    System.out.println("Insert name");
    System.out.println(back);
    String choice = scanner.nextLine();
    Boolean continuing = true;
    while (continuing) {
      if (!choice.trim().equalsIgnoreCase("")) {
        if (choice.trim().equalsIgnoreCase("q")) {
          continuing = false;
          break;
        }

        System.out.println("The Product doesn't exist\nCreate new Product? (Y/N)");
        choice = scanner.nextLine();

        if (handleExpectedUserInput(choice, "y")) {
          int step = 1;
          boolean creatingProduct = true;

          String productCategory = "";
          int productLifespan = 0;
          List<String> productMaterials = new ArrayList<>();

          while (creatingProduct) {
            switch (step) {
              case 1:
                System.out.println("Introduce Category: ");
                choice = scanner.nextLine();
                if (isBack(choice)) {
                  step--;
                } else {
                  productCategory = choice;
                  step++;
                }
                break;
              case 2:
                System.out.println("Introduce Lifespan: ");
                Boolean isCorrectLifespan = false;
                while (!isCorrectLifespan) {
                  try {
                    choice = scanner.nextLine();
                    if (!choice.trim().equalsIgnoreCase("q")) {
                      int intChoice = Integer.parseInt(choice);
                      if (intChoice < 1) {
                        System.err.println("Lifespan should be a number greater than 0.");
                      } else {
                        isCorrectLifespan = true;
                      }
                    } else {
                      step--;
                    }
                  } catch (Exception e) {
                    isCorrectLifespan = false;
                    System.err.println("Lifespan should be a number greater than 0.");
                  }
                }
                if (isBack(choice)) {
                  step--;
                } else {
                  step++;
                }
                break;
              case 3:
                System.out.println("Introduce Materials: ");
                boolean collectingMaterials = true;
                while (collectingMaterials) {
                  System.out.print("> ");
                  String materialInput = scanner.nextLine().trim();
                  if (materialInput.equalsIgnoreCase("q")) {
                    step--;
                    collectingMaterials = false;
                  } else if (materialInput.isEmpty()) {
                    step++;
                    collectingMaterials = false;
                  } else {
                    productMaterials.add(materialInput);
                  }
                }
                break;
              case 4:
                System.out.println("\nyippee!!");
                System.out.println("Category: " + productCategory);
                System.out.println("Lifespan: " + productLifespan);
                System.out.println("Materials: " + productMaterials);
              case 0:
                creatingProduct = false;
              default:
                break;
            }
          }

          break;
        } else {
          break;
        }

      } else {
        System.err.println("Input cannot be empty.");
        choice = scanner.nextLine();
      }
    }
  }

  private static void listProductOption() {
    System.out.println("This lists the product!");
  }

  private static void runOption(String choice) {
    MenuOption chosenOption = options.stream().filter(o -> o.getCharacter().equalsIgnoreCase(choice)).findFirst().get(); // get
                                                                                                                         // the
                                                                                                                         // option
                                                                                                                         // with
                                                                                                                         // the
                                                                                                                         // corresponding
                                                                                                                         // character
                                                                                                                         // (or
                                                                                                                         // throw
                                                                                                                         // NoSuchElement!!)
    chosenOption.run();
  }

  private static List<MenuOption> options = new ArrayList<>();

  private static void init(Scanner scanner) {
    options.add(new MenuOption("a", "Fetch Product", () -> {
      fetchProductOption(scanner);
    }));
    options.add(new MenuOption("b", "List Product", () -> {
      listProductOption();
    }));
  }

  public static void startLoop() {
    Scanner scanner = new Scanner(System.in);
    init(scanner);
    String choice;
    while (true) {
      displayOptions(options);
      choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return;
      }
      try {
        runOption(choice.toLowerCase().trim());
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }

  protected static void displayOptions(List<MenuOption> options) {
    for (int i = 0; i < options.size(); i++) {
      System.out.println(options.get(i).toString());
    }
    System.out.print("> ");
  }

  protected static String handleUserInput() {
    return "x";
  }

  protected static Boolean handleExpectedUserInput(String choice, String... values) {
    String formattedChoice = choice.toLowerCase().trim();
    return !Arrays.asList(values).contains(formattedChoice) ? false : true;
  }

  protected static Boolean isBack(String choice) {
    return handleExpectedUserInput(choice, "q");
  }
}
