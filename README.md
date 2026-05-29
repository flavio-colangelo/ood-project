# ood-project

The goal is to design a menu-driven console program handling product management, material management, calculation of environmental impact and lastly providing recycling guidance.

The members of the group are: Adrián Carrillo Jordán , Elliott Knotek , Flavio Colangelo , Osikoya Omotoyosi Nelson
  
Architectural Decisions:

The project follows a layered architecture consisting of Presentation, Application and Domain layers.

In the Presentation layer we have the Menu and MenuOption classes. These classes hold the user interface through which the user interacts with the application. This layer is responsible for user input, menu navigation and displaying information.

The Application layer consists of the ProductService and MaterialService classes. These classes coordinate communication between the presentation and domain layers and are responsible for handling application flow.

The Domain layer contains the main business logic and entities such as Product, Material, ProductRepository, MaterialRepository, RecyclingGuidanceService, SimpleSumStrategy, WeightedByLifespanStrategy and the EnvironmentalImpactCalculator interface.

Strategy Pattern:

The Strategy Pattern was introduced to allow different ways of calculating environmental impact without changing the Product or ProductService classes.

Currently the project contains two implementations namely SimpleSumStrategy and WeightedByLifespanStrategy

This makes the calculation logic easier to maintain, easier to extend and allows strategies to be switched without changing the rest of the application.

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