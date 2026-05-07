package se.hkr.ood.presentation;

import se.hkr.ood.application.MaterialService;
import se.hkr.ood.application.ProductService;
import se.hkr.ood.domain.Product;
import se.hkr.ood.domain.Material;

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

  private static void listProductOption() {
    System.out.println("This lists the product!");
  }

  // get the option with the corresponding character (or throw NoSuchElement!!)
  private static void runOption(String choice) {
    MenuOption chosenOption = options.stream().filter(o -> o.getCharacter().equalsIgnoreCase(choice)).findFirst().get();
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

  protected static void displayOptions(List<MenuOption> options) {
    for (int i = 0; i < options.size(); i++) {
      System.out.println(options.get(i).toString());
    }
    System.out.println("q) back");
    System.out.print("> ");
  }

  protected static void displayOptions(String options) {
    System.out.println(options);
    System.out.println("q) back");
    System.out.print("> ");
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

  private static void fetchProductOption(Scanner scanner) {
    while (true) {
      displayOptions("Insert name.");
      String name = scanner.nextLine();
      if (name.trim().equalsIgnoreCase("q")) {
        return;
      }
      Product product = ProductService.createProduct();
      try {
        product = ProductService.fetchProduct(name);
      } catch (Exception e) {
        try {
          System.out.println("Product doesn't exist.");
          ProductService.setName(product, name);
          product = createProduct(product, scanner); // name
        } catch (Exception i) {
          return;
        }
      } finally {
        if (product != null) {
          // TODO
          // show the product and ask for additional action
        }
      }
    }
  }

  private static Product createProduct(Product product, Scanner scanner) {
    while (true) {
      Product sendAlong = product;
      displayOptions("Create a new product? (Y/N)");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null;
      }
      switch (choice.trim().toLowerCase()) {
        case "y":
          sendAlong = createProductStep1(sendAlong, scanner); // name
        case "n":
          throw new NullPointerException("No action taken");
        default:
          System.out.println("Invalid option");
          break;
      }
      if (sendAlong != null) {
        return sendAlong;
      }
    }
  }

  private static Product createProductStep1(Product product, Scanner scanner) {
    while (true) {
      Product sendAlong = product; // name
      displayOptions("Insert Category");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null; // name
      }
      ProductService.setCategory(sendAlong, choice);
      sendAlong = createProductStep2(sendAlong, scanner); // name category
      if (sendAlong != null) {
        return sendAlong;
      }
    }
  }

  private static Product createProductStep2(Product product, Scanner scanner) {
    while (true) {
      Product sendAlong = product; // name category
      displayOptions("Insert Category");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null; // name category
      }
      try {
        int r = Integer.parseInt(choice);
        ProductService.setlifespan(sendAlong, r); // name category lifespan
        sendAlong = createProductStep3(sendAlong, scanner);
        if (sendAlong != null) {
          return sendAlong;
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid Lifespan");
      }
    }
  }

  private static Product createProductStep3(Product product, Scanner scanner) {
    while (true) {
      Product sendAlong = product;
      displayOptions("Insert Materials (ENTER to continue)");
      String choice;
      List<String> productMaterials = new ArrayList<>();
      do {
        choice = scanner.nextLine();
        if (choice.trim().equalsIgnoreCase("q")) {
          return null;
        }
        productMaterials.add(choice);
        System.out.print(">");
      } while (choice != "");
      if (productMaterials.size() > 0) {
        List<Material> materialList = new ArrayList<>();
        for (int i = 0; i < productMaterials.size(); i++) {
          try {
            materialList.add(MaterialService.fetchMaterial(productMaterials.get(i)));
          } catch (Exception e) {
            System.out.println(materialList.get(i) + " doesn't exist.");
            materialList.add(createMaterial(productMaterials.get(i), scanner));
            if (materialList.get(i) == null) {
              break;
            }
          }
        }
        if (materialList.size() == productMaterials.size()) {
          sendAlong.setMaterials(materialList);
          return sendAlong;
        }
      }
    }
  }

  private static Material createMaterial(String name, Scanner scanner) {
    while (true) {
      Material sendAlong = new Material(name);
      displayOptions("Create a new Material? (Y/N)");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null;
      }
      switch (choice.trim().toLowerCase()) {
        case "y":
          sendAlong = createMaterialStep1(sendAlong, scanner);
        case "n":
          throw new NullPointerException("No action taken");
        default:
          System.out.println("Invalid option");
          break;
      }
      if (sendAlong != null) {
        return sendAlong;
      }
    }
  }
}