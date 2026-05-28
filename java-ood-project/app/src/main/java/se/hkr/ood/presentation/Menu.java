package se.hkr.ood.presentation;

import se.hkr.ood.application.MaterialService;
import se.hkr.ood.application.ProductService;
import se.hkr.ood.domain.Product;
import se.hkr.ood.domain.SimpleSumStrategy;
import se.hkr.ood.domain.WeightedByLifespanStrategy;
import se.hkr.ood.domain.Material;
import se.hkr.ood.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
  private static void listProductOption() {
    System.out.println("This lists the product!"); // TODO
  }

  // get the option with the corresponding character (or throw NoSuchElement!!)
  private static void runOption(List<MenuOption> options, String choice) {
    MenuOption chosenOption = options.stream().filter(o -> o.getCharacter().equalsIgnoreCase(choice)).findFirst().get();
    chosenOption.run();
  }

  private static List<MenuOption> options = new ArrayList<>();
  private static List<MenuOption> impactValue = new ArrayList<>();
  private static List<MenuOption> otherOptions = new ArrayList<>();
  private static List<MenuOption> updateProduct = new ArrayList<>();
  private static List<MenuOption> updateMaterial = new ArrayList<>();

  private static void init(Scanner scanner) {
    options.add(new MenuOption("a", "Fetch Product", () -> {
      fetchProductOption(scanner);
    }));
    options.add(new MenuOption("b", "List Product", () -> {
      listProductOption();
    }));
    options.add(new MenuOption("c", "Other", () -> {
      genericOptionsLoop(otherOptions, scanner);
    }));
    otherOptions.add(new MenuOption("a", "Update Product", () -> {
      updateProduct(scanner);
    }));
    otherOptions.add(new MenuOption("b", "Update Material", () -> {
      updateMaterial(scanner);
    }));
  }

  private static void init(Scanner scanner, Product product) {
    List<MenuOption> tempImpactValue = new ArrayList<>();
    List<MenuOption> tempUpdateProduct = new ArrayList<>();
    tempImpactValue.add(new MenuOption("a", "Simple Sum Strategy", () -> {
      double impact = ProductService.enviromentalImpact(product, new SimpleSumStrategy());
      System.out.println("Simple Sum Impact Value: " + impact);
    }));
    tempImpactValue.add(new MenuOption("b", "Weighted by Lifespan Strategy", () -> {
      double impact = ProductService.enviromentalImpact(product, new WeightedByLifespanStrategy());
      System.out.println("Simple Sum Impact Value: " + impact);
    }));
    tempUpdateProduct.add(new MenuOption("a", "Update Name", () -> {
      productUpdateName(product, scanner);
    }));
    tempUpdateProduct.add(new MenuOption("b", "Update Category", () -> {
      productUpdateCategory(product, scanner);
    }));
    tempUpdateProduct.add(new MenuOption("c", "Update Lifespan", () -> {
      productUpdateLifespan(product, scanner);
    }));
    tempUpdateProduct.add(new MenuOption("d", "Update Materials", () -> {
      createProductStep3(product, scanner);
    }));
    impactValue = tempImpactValue;
    updateProduct = tempUpdateProduct;
  }

  private static void init(Scanner scanner, Material material) {
    List<MenuOption> tempUpdateMaterial = new ArrayList<>();
    tempUpdateMaterial.add(new MenuOption("a", "Update Name", () -> {
      materialUpdateName(material, scanner);
    }));
    tempUpdateMaterial.add(new MenuOption("b", "Update Impact Value", () -> {
      materialUpdateImpactValue(material, scanner);
    }));
    tempUpdateMaterial.add(new MenuOption("c", "Update Recycling Guidance", () -> {
      materialUpdateRecyclingGuidance(material, scanner);
    }));
    updateMaterial = tempUpdateMaterial;
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

  public static void startLoop() {
    Scanner scanner = new Scanner(System.in);
    init(scanner);
    genericOptionsLoop(options, scanner);
    scanner.close();
  }

  private static void fetchProductOption(Scanner scanner) {
    while (true) {
      displayOptions("Insert name.");
      String name = scanner.nextLine();
      if (name.trim().equalsIgnoreCase("q")) {
        return;
      }
      Product product = ProductService.generateProduct();
      try {
        product = ProductService.fetchProduct(name);
      } catch (ProductNotFoundException e) {
        try {
          System.out.println("Product doesn't exist.");
          ProductService.setName(product, name);
          product = createProduct(product, scanner);
        } catch (NoActionSelectedException i) {
          return;
        }
      }
      if (product != null) {
        init(scanner, product);
        System.out.println(product);
        ProductService.createProduct(product);
        System.out.println("Calculate impact value?");
        genericOptionsLoop(impactValue, scanner);
        return;
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
          sendAlong = createProductStep1(sendAlong, scanner);
          break;
        case "n":
          throw new NoActionSelectedException("No action taken");
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
      Product sendAlong = product;
      displayOptions("Insert Category");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null;
      }
      ProductService.setCategory(sendAlong, choice);
      sendAlong = createProductStep2(sendAlong, scanner);
      if (sendAlong != null) {
        return sendAlong;
      }
    }
  }

  private static Product createProductStep2(Product product, Scanner scanner) {
    while (true) {
      Product sendAlong = product;
      displayOptions("Insert Lifespan");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null;
      }
      try {
        int r = Integer.parseInt(choice);
        ProductService.setlifespan(sendAlong, r);
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
      choice = scanner.nextLine();
      while (choice != "") {
        productMaterials.add(choice);
        System.out.print("> ");
        choice = scanner.nextLine();
        if (choice.trim().equalsIgnoreCase("q")) {
          return null;
        }
      }
      if (productMaterials.size() > 0) {
        List<Material> materialList = new ArrayList<>();
        for (int i = 0; i < productMaterials.size(); i++) {
          try {
            materialList.add(MaterialService.fetchMaterial(productMaterials.get(i)));
          } catch (MaterialNotFoundException e) {
            System.out.println(productMaterials.get(i) + " doesn't exist.");
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
      } else {
        System.out.println("At least one Material is required");
      }
    }
  }

  private static Material createMaterial(String name, Scanner scanner) {
    while (true) {
      Material sendAlong = MaterialService.generateMaterial();
      MaterialService.setName(sendAlong, name);
      displayOptions("Create a new Material? (Y/N)");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null;
      }
      switch (choice.trim().toLowerCase()) {
        case "y":
          sendAlong = createMaterialStep1(sendAlong, scanner);
          break;
        case "n":
          throw new NoActionSelectedException("No action taken");
        default:
          System.out.println("Invalid option");
          break;
      }
      if (sendAlong != null) {
        MaterialService.createMaterial(sendAlong);
        System.out.println("e");
        return sendAlong;
      }
    }
  }

  private static Material createMaterialStep1(Material material, Scanner scanner) {
    while (true) {
      Material sendAlong = material;
      displayOptions("Insert Impact Value");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null;
      }
      try {
        int r = Integer.parseInt(choice);
        MaterialService.setImpactValue(material, r);
        sendAlong = createMaterialStep2(sendAlong, scanner);
        if (sendAlong != null) {
          return sendAlong;
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid Impact Value");
      }
    }
  }

  private static Material createMaterialStep2(Material material, Scanner scanner) {
    while (true) {
      Material sendAlong = material;
      displayOptions("Insert Recycling Guidance (ENTER to continue)");
      String choice = scanner.nextLine();
      List<String> recyclingGuidance = new ArrayList<>();
      if (choice.trim().equalsIgnoreCase("q")) {
        return null;
      }
      while (choice != "") {
        recyclingGuidance.add(choice);
        System.out.print("> ");
        choice = scanner.nextLine();
        if (choice.trim().equalsIgnoreCase("q")) {
          return null;
        }
      }
      if (recyclingGuidance.size() > 0) {
        sendAlong = MaterialService.setRecyclingGuidance(sendAlong, recyclingGuidance);
        return sendAlong;
      } else {
        System.out.println("Please add a Recycling Guidance");
      }
    }
  }

  private static void updateProduct(Scanner scanner) {
    while (true) {
      displayOptions("Insert Product name.");
      String name = scanner.nextLine();
      if (name.trim().equalsIgnoreCase("q")) {
        return;
      }
      Product product = ProductService.generateProduct();
      try {
        product = ProductService.fetchProduct(name);
        init(scanner, product);
        genericOptionsLoop(updateProduct, scanner);
      } catch (ProductNotFoundException e) {
        System.out.println("Product doesn't exist, try again");
      } catch (NoActionSelectedException e) {
        return;
      }
    }
  }

  private static void updateMaterial(Scanner scanner) {
    while (true) {
      displayOptions("Insert Material name.");
      String name = scanner.nextLine();
      if (name.trim().equalsIgnoreCase("q")) {
        return;
      }
      Material material = MaterialService.generateMaterial();
      try {
        material = MaterialService.fetchMaterial(name);
        init(scanner, material);
        genericOptionsLoop(updateMaterial, scanner);
      } catch (MaterialNotFoundException e) {
        System.out.println("Material doesn't exist, try again");
      }
    }
  }

  private static void productUpdateName(Product product, Scanner scanner) {
    displayOptions("Insert new Name");
    String choice = scanner.nextLine();
    if (choice.trim().equalsIgnoreCase("q")) {
      return;
    }
    ProductService.setName(product, choice);
  }

  private static void productUpdateCategory(Product product, Scanner scanner) {
    displayOptions("Insert new Category");
    String choice = scanner.nextLine();
    if (choice.trim().equalsIgnoreCase("q")) {
      return;
    }
    ProductService.setCategory(product, choice);
  }

  private static void productUpdateLifespan(Product product, Scanner scanner) {
    while (true) {
      displayOptions("Insert new Lifespan");
      String choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return;
      }
      try {
        int r = Integer.parseInt(choice);
        ProductService.setlifespan(product, r);
        return;
      } catch (NumberFormatException e) {
        System.out.println("Invalid Lifespan");
      }
    }
  }

  private static void materialUpdateName(Material material, Scanner scanner) {
    displayOptions("Insert new Name");
    String choice = scanner.nextLine();
    if (choice.trim().equalsIgnoreCase("q")) {
      return;
    }
    MaterialService.setName(material, choice);
  }

  private static void materialUpdateImpactValue(Material material, Scanner scanner) {
    displayOptions("Insert new Impact Value");
    String choice = scanner.nextLine();
    if (choice.trim().equalsIgnoreCase("q")) {
      return;
    }
    try {
      int r = Integer.parseInt(choice);
      material = MaterialService.setImpactValue(material, r);
      return;
    } catch (NumberFormatException e) {
      System.out.println("Invalid Impact Value");
    }
  }

  private static void materialUpdateRecyclingGuidance(Material material, Scanner scanner) {
    displayOptions("Insert new Recycling Guidance (ENTER to continue)");
    String choice = scanner.nextLine();
    List<String> recyclingGuidance = new ArrayList<>();
    if (choice.trim().equalsIgnoreCase("q")) {
      return;
    }
    while (choice != "") {
      recyclingGuidance.add(choice);
      System.out.print(">");
      choice = scanner.nextLine();
      if (choice.trim().equalsIgnoreCase("q")) {
        return;
      }
    }
    if (recyclingGuidance.size() > 0) {
      material = MaterialService.setRecyclingGuidance(material, recyclingGuidance);
    } else {
      System.out.println("Please add a Recycling Guidance");
    }
  }
}
