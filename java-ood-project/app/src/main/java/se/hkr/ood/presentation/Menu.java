package se.hkr.ood.presentation;

import se.hkr.ood.application.MaterialService;
import se.hkr.ood.application.ProductService;
import se.hkr.ood.domain.Product;
import se.hkr.ood.domain.SimpleSumStrategy;
import se.hkr.ood.domain.WeightedByLifespanStrategy;
import se.hkr.ood.domain.Material;
import se.hkr.ood.exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu {
  private static void listProductOption() {
    System.out.println("This lists the product!");
  }

  // get the option with the corresponding character (or throw exception!!)
  private static void runOption(List<MenuOption> options, String choice) {
    MenuOption chosenOption = options.stream()
        .filter(o -> o.getCharacter().equalsIgnoreCase(choice)) // get option with right character
        .findFirst() // only one thank you
        .orElseThrow(() -> new InvalidMenuOptionException("Invalid option selected."));
    chosenOption.run();
  }

  private static List<MenuOption> options = new ArrayList<>();
  private static List<MenuOption> impactValue = new ArrayList<>();
  private static List<MenuOption> otherOptions = new ArrayList<>();
  private static List<MenuOption> updateProduct = new ArrayList<>();
  private static List<MenuOption> updateMaterial = new ArrayList<>();

  private static void init(Scanner scanner) throws SQLException {
    options.clear();
    otherOptions.clear();

    options.add(new MenuOption("a", "Fetch Product", () -> {
      try {
        fetchProductOption(scanner);
      } catch (Exception e) {
        throw new ApplicationRuntimeException(e.getMessage());
      }
    }));
    options.add(new MenuOption("b", "List Product", () -> {
      listProductOption();
    }));
    options.add(new MenuOption("c", "Other", () -> {
      genericOptionsLoop(otherOptions, scanner);
    }));
    otherOptions.add(new MenuOption("a", "Update Product", () -> {
      try {
        updateProduct(scanner);
      } catch (SQLException e) {
        throw new ApplicationRuntimeException(e.getMessage());
      }
    }));
    otherOptions.add(new MenuOption("b", "Update Material", () -> {
      try {
        updateMaterial(scanner);
      } catch (SQLException e) {
        throw new ApplicationRuntimeException(e.getMessage());
      }
    }));
  }

  private static void init(Scanner scanner, Product product) {
    impactValue.clear();
    updateProduct.clear();

    impactValue.add(new MenuOption("a", "Simple Sum Strategy", () -> {
      double impact = ProductService.enviromentalImpact(product, new SimpleSumStrategy());
      System.out.println("Simple Sum Impact Value: " + impact);
    }));
    impactValue.add(new MenuOption("b", "Weighted by Lifespan Strategy", () -> {
      double impact = ProductService.enviromentalImpact(product, new WeightedByLifespanStrategy());
      System.out.println("Weighted Lifespan Impact Value: " + impact);
    }));
    updateProduct.add(new MenuOption("a", "Update Name", () -> {
      genericUpdate("name", "Insert new Name", product, scanner);
    }));
    updateProduct.add(new MenuOption("b", "Update Category", () -> {
      genericUpdate("category", "Insert new Category", product, scanner);
    }));
    updateProduct.add(new MenuOption("c", "Update Lifespan", () -> {
      genericUpdate("enstimatedLifespan", "Insert new Lifespan", product, scanner, true);
    }));
    // updateProduct.add(new MenuOption("d", "Update Materials", () -> {
    //   Product updated = null;
    //   try {
    //     updated = createProductStep3(product, scanner);
    //   } catch (Exception e) {
    //     throw new ApplicationRuntimeException("Error while updating materials: " + e.getMessage());
    //   }
    //   if (updated != null) { // could put database push inside step 3, but I'm worried about coupling
    //     ProductService.createProduct(updated);
    //     System.out.println("Materials updated and saved!");
    //   }
    // })); // TODO; SHOULD HANDLE PRODUCT MATERIALS TABLE THING
  }

  private static void init(Scanner scanner, Material material) {
    updateMaterial.clear();
    updateMaterial.add(new MenuOption("a", "Update Name", () -> {
      genericUpdate("name", "Insert new Name", material, scanner);
    }));
    updateMaterial.add(new MenuOption("b", "Update Impact Value", () -> {
      genericUpdate("impactValue", "Insert new Impact Value", material, scanner, true);
    }));
    // updateMaterial.add(new MenuOption("c", "Update Recycling Guidance", () -> {
    //   try {
    //     materialUpdateRecyclingGuidance(material, scanner);
    //   } catch (Exception e) {
    //     throw new ApplicationRuntimeException(e.getMessage());
    //   }
    // })); TODO; UPDATE TO MAKE IT MORE GENERIC
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

  private static void genericOptionsLoop(List<MenuOption> options, Scanner scanner) {
    while (true) {
      String choice;
      displayOptions(options);
      choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return;
      }
      try {
        runOption(options, choice.toLowerCase().trim());
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
    }
  }

  public static void startLoop() throws SQLException {
    Scanner scanner = new Scanner(System.in);
    init(scanner);
    genericOptionsLoop(options, scanner);
    scanner.close();
  }

  private static void fetchProductOption(Scanner scanner) throws SQLException {
    displayOptions("Insert product name.");
    String name = scanner.nextLine().trim();
    if (name.equalsIgnoreCase("q"))
      return;

    Product product;
    try {
      product = ProductService.fetchProduct(name);
      System.out.println("Name: " + product.getName() + " :: Category: " + product.getCategory()); // product.toString()
                                                                                                   // should do this
    } catch (ProductNotFoundException e) {
      System.out.println("\nProduct doesn't exist.");
      try {
        Product temp = ProductService.generateProduct();
        ProductService.setName(temp, name);
        product = createProduct(temp, scanner);

        if (product != null) {
          ProductService.createProduct(product);
          System.out.println("\n[SUCCESS] Product '" + product.getName() + "' saved to DB!");
        } else {
          return; // Cancelled
        }
      } catch (NoActionSelectedException i) {
        return;
      }
    }

    if (product != null) {
      init(scanner, product);
      System.out.println("\nCalculate impact value?");
      genericOptionsLoop(impactValue, scanner);
    }
  }

  private static Product createProduct(Product product, Scanner scanner) throws SQLException {
    while (true) {

      displayOptions("Create a new product? (Y/N)");
      String choice = scanner.nextLine().trim().toLowerCase();
      if (choice.equals("q") || choice.equals("n")) {
        throw new NoActionSelectedException("Product creation cancelled.");
      }
      if (choice.equals("y")) {
        Product result = createProductStep1(product, scanner);
        if (result != null) {
          return result;
        } else {
          System.out.println("Invalid option.");
          // return null; //?
        }
      }
    }
  }

  private static Product createProductStep1(Product product, Scanner scanner) throws SQLException {
    while (true) {

      displayOptions("Insert Category");
      String choice = scanner.nextLine().trim();
      if (choice.equalsIgnoreCase("q")) {
        return null;
      }
      ProductService.setCategory(product, choice);
      Product result = createProductStep2(product, scanner);
      if (result != null) {
        return result;
      }
    }
  }

  private static Product createProductStep2(Product product, Scanner scanner) throws SQLException {
    while (true) {
      displayOptions("Insert Lifespan");
      String choice = scanner.nextLine().trim();
      if (choice.equalsIgnoreCase("q"))
        return null;

      try {
        int lifespan = Integer.parseInt(choice);
        ProductService.setlifespan(product, lifespan);
        return createProductStep3(product, scanner);
      } catch (NumberFormatException e) {
        System.out.println("Invalid Lifespan. Please enter a number.");
      }
    }
  }

  private static Product createProductStep3(Product product, Scanner scanner) throws SQLException {
    while (true) {
      List<String> productMaterials = new ArrayList<>();
      System.out.println("Insert Materials (Press ENTER to continue)");

      while (true) {
        System.out.print("> ");
        String choice = scanner.nextLine().trim();
        if (choice.equalsIgnoreCase("q"))
          return null;
        if (choice.isEmpty())
          break;
        productMaterials.add(choice);
      }

      if (productMaterials.isEmpty()) {
        System.out.println("At least one Material is required.");
        // return createProductStep3(product, scanner); // retry
        continue; // cause of while (true) it asks again anyways
      }

      List<Material> materialList = new ArrayList<>();
      boolean quit = false;
      for (String matName : productMaterials) {
        try {
          materialList.add(MaterialService.fetchMaterial(matName));
        } catch (MaterialNotFoundException e) {
          System.out.println("\nMaterial '" + matName + "' doesn't exist.");
          try {

            Material newMat = createMaterial(matName, scanner);
            if (newMat == null) {
              quit = true;
              break;
            }
            MaterialService.createMaterial(newMat);
            materialList.add(newMat);
          } catch (Exception x) {
            // TODO: This was generated automatically but to be fair I don't think this matters rn
          }
        }
      }

      ProductService.setMaterials(product, materialList);
      return product;
    }
  }

  private static Material createMaterial(String name, Scanner scanner) {
    displayOptions("Create a new Material '" + name + "'? (Y/N)");
    String choice = scanner.nextLine().trim().toLowerCase();

    if (choice.equals("q") || choice.equals("n")) {
      throw new NoActionSelectedException("Material creation cancelled.");
    }
    if (choice.equals("y")) {
      Material material = MaterialService.generateMaterial();
      MaterialService.setName(material, name);
      return createMaterialStep1(material, scanner);
    }
    return null;
  }

  private static Material createMaterialStep1(Material material, Scanner scanner) {
    while (true) {
      displayOptions("Insert Impact Value");
      String choice = scanner.nextLine().trim();
      if (choice.equalsIgnoreCase("q"))
        return null;

      try {
        int impact = Integer.parseInt(choice);
        MaterialService.setImpactValue(material, impact);
        return createMaterialStep2(material, scanner);
      } catch (NumberFormatException e) {
        System.out.println("Invalid Impact Value. Must be a number.");
      }
    }
  }

  private static Material createMaterialStep2(Material material, Scanner scanner) {
    List<String> guidance = new ArrayList<>();
    System.out.println("Insert Recycling Guidance steps (Press ENTER on an empty line to finish)");

    while (true) {
      System.out.print("> ");
      String choice = scanner.nextLine().trim();
      if (choice.equalsIgnoreCase("q"))
        return null;
      if (choice.isEmpty())
        break;
      guidance.add(choice);
    }

    if (guidance.isEmpty()) {
      System.out.println("Please add at least one Recycling Guidance step.");
      return createMaterialStep2(material, scanner); // Retry
    }

    MaterialService.setRecyclingGuidance(material, guidance);
    return material;
  }

  private static void updateProduct(Scanner scanner) throws SQLException {
    displayOptions("Insert Product name to update.");
    String name = scanner.nextLine().trim();
    if (name.equalsIgnoreCase("q"))
      return;

    try {
      Product product = ProductService.fetchProduct(name);
      init(scanner, product);
      genericOptionsLoop(updateProduct, scanner);
    } catch (ProductNotFoundException e) {
      System.out.println("Product doesn't exist, try again.");
    }
  }

  private static void updateMaterial(Scanner scanner) throws SQLException {
    displayOptions("Insert Material name to update.");
    String name = scanner.nextLine().trim();
    if (name.equalsIgnoreCase("q"))
      return;

    try {
      Material material = MaterialService.fetchMaterial(name);
      init(scanner, material);
      genericOptionsLoop(updateMaterial, scanner);
    } catch (MaterialNotFoundException e) {
      System.out.println("Material doesn't exist, try again.");
    }
  }

  private static void genericUpdate(String attribute, String promptText, Object target, Scanner scanner,
      boolean requiresNumber) {
    while (true) {
      displayOptions(promptText);
      String choice = scanner.nextLine().trim();
      if (choice.equalsIgnoreCase("q"))
        return;

      if (requiresNumber) {
        try {
          Integer.parseInt(choice);
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Must be a number.");
          continue;
        }
      }

      try {
        if (target instanceof Product) {
          ProductService.update(attribute, choice, (Product) target);
        } else if (target instanceof Material) {
          MaterialService.update(attribute, choice, (Material) target);
        }
        System.out.println("Update saved to DB!");
        return;
      } catch (Exception e) {
        System.out.println("Update failed: " + e.getMessage());
        return;
      }
    }
  }

  private static void genericUpdate(String attribute, String promptText, Object target, Scanner scanner) {
    genericUpdate(attribute, promptText, target, scanner, false); // parsing a number is rarer
  }
}