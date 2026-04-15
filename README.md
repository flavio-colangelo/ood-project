# ood-project
This project is a group project within the Object Oriented Design course.
The goal is to design a menu-driven console program handling product management, material management, calculation of the environmental impact and lastly providing recycling guidance.
The members of the group are: Adrián Carrillo Jordán, Elliott Knotek, Flavio Colangelo and Osikoya Omotoyosi Nelson.
The roles are divided between the team member as follows:

# TODO

## Architectural Decisions

Architectural Decisions

Our system is structured using a layered architecture consisting of Presentation, Application, Domain, and a planned Framework layer.

The Presentation layer contains the Menu class, which handles user interaction and sends requests to the application layer. It does not contain business logic.

The Application layer contains the main coordination logic, including ProductService and RecyclingGuidanceService. ProductService manages product-related operations such as creating, retrieving, listing products, and evaluating environmental impact. RecyclingGuidanceService is responsible for generating recycling guidance based on product composition.

The Application layer also includes the EnvironmentalImpactCalculator interface and its implementations, such as WeightedByLifespanStrategy and SimpleSumStrategy. This allows different calculation approaches to be added without modifying existing code.

The Domain layer contains the core entities Product and Material, which are responsible only for storing product and material data.

We also plan a Framework layer for handling external concerns such as data storage.

Dependencies follow an inward direction because the Presentation layer depends on the Application layer and the Application layer depends on the Domain layer and abstractions like EnvironmentalImpactCalculator, but the Domain layer remains independent