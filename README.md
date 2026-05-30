<center>

# ood-project
![CI](https://github.com/flavio-colangelo/ood-project/actions/workflows/ci.yml/badge.svg)
![Java Version](https://img.shields.io/badge/Java%20version-25.0.1-red)
<br>
![GitHub last main commit](https://img.shields.io/github/last-commit/flavio-colangelo/ood-project?label=Last%20commit%20(main))
![GitHub last development commit](https://img.shields.io/github/last-commit/flavio-colangelo/ood-project/development?label=Last%20commit%20(development))
![GitHub repo size](https://img.shields.io/github/repo-size/flavio-colangelo/ood-project?label=Repo%20size)
<hr>

</center>

The goal is to design a menu-driven console program handling product management, material management, calculation of environmental impact and lastly providing recycling guidance.

The members of the group are: Adrián Carrillo Jordán, Flavio Colangelo , Osikoya Omotoyosi Nelson
  
Architectural Decisions:

The project follows a layered architecture consisting of Presentation, Application and Domain layers.

In the Presentation layer we have the Menu and MenuOption classes. These classes hold the user interface through which the user interacts with the application. This layer is responsible for user input, menu navigation and displaying information.

The Application layer consists of the ProductService and MaterialService classes. These classes coordinate communication between the presentation and domain layers and are responsible for handling application flow.

The Domain layer contains the main business logic and entities such as Product, Material, ProductRepository, MaterialRepository, RecyclingGuidanceService, SimpleSumStrategy, WeightedByLifespanStrategy and the EnvironmentalImpactCalculator interface.

Strategy Pattern:

The Strategy Pattern was introduced to allow different ways of calculating environmental impact without changing the Product or ProductService classes.

Currently the project contains two implementations namely SimpleSumStrategy and WeightedByLifespanStrategy

This makes the calculation logic easier to maintain, easier to extend and allows strategies to be switched without changing the rest of the application.

Callback Pattern:

The Callback pattern is used in the Menu and MenuOption classes to allow menu actions to be executed dynamically.

Each MenuOption stores a Runnable function that is passed when the menu option is created. When a user selects an option, the stored function is executed through the run() method.

This makes the menu easier to extend since new options can be added without changing the menu flow logic.

Testing:

The project uses JUnit 5 for unit testing.

Tests are implemented for:
- Product
- Material
- ProductRepository
- MaterialRepository
- RecyclingGuidanceService
- SimpleSumStrategy
- WeightedByLifespanStrategy

This tests follow the Arrange, Act, Assert structure.

# UML Class Diagram
```
@startuml

package presentation {
  class Menu {
    + startLoop(): void
  }

  class MenuOption {
    - character: String
    - title: String
    - function: Runnable
    + run(): void
    + getCharacter(): String
    + toString(): String
  }
}

package application {
  class ProductService
  class MaterialService
}

package domain {

  class Product {
    - name: String
    - category: String
    - estimatedLifespan: int
    - materials: List<Material>
  }

  class Material {
    - name: String
    - impactValue: int
    - recyclingGuidance: List<String>
  }

  class ProductRepository
  class MaterialRepository
  class DatabaseManager
  class RecyclingGuidanceService

  interface EnviromentalImpactCalculator {
    + calculate(product: Product): double
  }

  class SimpleSumStrategy
  class WeightedByLifespanStrategy
}

package exceptions {
  class ApplicationRuntimeException
  class InvalidMenuOptionException
  class NoActionSelectedException
  class ProductNotFoundException
  class MaterialNotFoundException
}

class App

App --> Menu

Menu --> MenuOption
Menu --> ProductService
Menu --> MaterialService

ProductService --> ProductRepository
ProductService --> RecyclingGuidanceService
ProductService --> EnviromentalImpactCalculator

MaterialService --> MaterialRepository

ProductRepository --> Product
MaterialRepository --> Material
ProductRepository --> DatabaseManager
MaterialRepository --> DatabaseManager

Product "*" o-- "*" Material

RecyclingGuidanceService --> Product

EnviromentalImpactCalculator <|.. SimpleSumStrategy
EnviromentalImpactCalculator <|.. WeightedByLifespanStrategy

RuntimeException <|-- ApplicationRuntimeException
RuntimeException <|-- InvalidMenuOptionException
RuntimeException <|-- NoActionSelectedException
RuntimeException <|-- ProductNotFoundException
RuntimeException <|-- MaterialNotFoundException

@enduml
```
# Flow Diagram
```
@startuml
start

:Start application;
:Open main menu;
:Display menu options;

while (User has not selected q?) is (continue)
  :Read user input;

  if (Valid menu option?) then (yes)

    if (Fetch/Create Product selected?) then (yes)
      :Ask for product name;

      if (Product exists?) then (yes)
        :Fetch product using ProductService;
        :Display product information;
      else (no)
        :Ask user to create product;

        if (User confirms?) then (yes)
          :Enter product category;
          :Enter estimated lifespan;
          :Enter materials;
          :Create product;
          :Save product through ProductRepository;
        else (no)
          :Return to main menu;
        endif
      endif

    elseif (List Products selected?) then (yes)
      :Display registered products;

    elseif (Impact calculation selected?) then (yes)
      :Select strategy;
      :Calculate environmental impact;
      :Display result;
    endif

  else (no)
    :Handle invalid menu option;
  endif

endwhile (q selected)

:Exit application;
stop
@enduml
```
