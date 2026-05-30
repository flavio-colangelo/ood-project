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

The members of the group are: Adrián Carrillo Jordán, Flavio Colangelo, and Osikoya Omotoyosi Nelson

## Running the Project
0. Install dependency

This project uses SqLite3, and it is a requirement to have it in your PATH. Follow [this tutorial](https://www.tutorialspoint.com/sqlite/sqlite_installation.htm) to set up SQLite.
1. Clone the repository

```bash
git clone https://github.com/flavio-colangelo/ood-project
cd ood-project
```
2. Run the java project

```bash
cd java-ood-project
gradle run
```
Or, for cleaner output,
```bash
gradle run --console plain --q
```
  
## Architectural Decisions

The project follows a layered architecture consisting of Presentation, Application and Domain layers, with an extra Infrastructure layer. This is to make sure that there is even more separation of concerns, each layer having its own responsibility.

### Presentation Layer
In the Presentation layer we have the Menu and MenuOption classes. These classes hold the user interface through which the user interacts with the application. This layer is responsible for user input, menu navigation and displaying information. If the UI were to be exchanged with another system, the separation between it and the underlying layers makes it possible.

### Application Layer
The Application layer consists of the ProductService and MaterialService classes. These classes coordinate communication between the presentation and domain layers and are responsible for handling application flow.

### Domain Layer
The Domain layer contains the main business logic and entities such as Product, Material, ProductRepository, MaterialRepository, RecyclingGuidanceService, SimpleSumStrategy, WeightedByLifespanStrategy and the EnvironmentalImpactCalculator interface.

### Infrastructure Layer
The Infrastructure layer holds the DatabaseManager, which is the only class that directly interfaces with the database. The Repository classes in the Domain layer use it to fetch information from the database, which they then parse and process.

## Strategy Pattern

The Strategy Pattern was introduced to allow different ways of calculating environmental impact without changing the Product or ProductService classes.

An alternative would be using a switch or if-else statement that checks for if the strategy that needs to be used is StrategyA or StrategyB, which is a far too rigid approach. One must update that switch/if-else statement as soon as a Strategy is added, introducing the possibility of human error. This pattern mitigates that issue, allowing strategies to be separately defined and functionally swappable.

## Callback Pattern

The Callback pattern is used in the DatabaseManager and Repository classes to allow for object parsing without writing object-specific login in the DatabaseManager. 

Without this, the DatabaseManager class would have to have separate functions and logic for fetching and then returning an object, but wih this pattern the DatabaseManager uses a parser provided by the Repositories, which keeps concerns separate.

## Testing

The project uses JUnit 5 for unit testing.

Tests are implemented for:
- Product
- Material
- ProductRepository
- MaterialRepository
- SimpleSumStrategy
- WeightedByLifespanStrategy

This tests follow the Arrange, Act, Assert structure.

# Package Structure Diagram
The package structure diagram for the project is available in [PackageStructure.puml](docs/PackageStructure.puml).


# UML Class Diagram
The UML diagram for the project is available in [ClassDiagram.puml](docs/ClassDiagram.puml).


# Flow Diagram
The Flow diagram for the project is available in [FlowDiagram.puml](docs/FlowDiagram.puml).

It describes the flow of the program when fetching a product. Which includes the product and, perhaps, material creation to ensure the user always ends with a product when fetching for one even when the product doesn't exist within the database, allowing a smoother user experience.
