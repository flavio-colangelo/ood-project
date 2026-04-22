# ood-project
This project is a group project within the Object Oriented Design course.
The goal is to design a menu-driven console program handling product management, material management, calculation of the environmental impact and lastly providing recycling guidance.
The members of the group are: Adrián Carrillo Jordán, Elliott Knotek, Flavio Colangelo and Osikoya Omotoyosi Nelson.
The roles are divided between the team member as follows:

# TODO
Our system is structured using a layered architecture consisting of Presentation, Application, Domain, and a planned Framework layer.

The Presentation layer contains the Menu class, which handles user interaction and delegates requests to the application layer. It acts as the entry point of the system and does not contain business logic.

The Application layer contains the main coordination logic. This includes ProductService and RecyclingGuidanceService. ProductService manages operations such as creating, retrieving, listing products, and evaluating environmental impact. RecyclingGuidanceService generates recycling guidance based on product composition. Additional services such as MaterialService may also be introduced to manage materials.

The Application layer also includes the ImpactCalculator interface with implementations such as WeightedByLifespanStrategy and SimpleSumStrategy. These define different strategies for calculating environmental impact and allow the system to be extended without modifying existing functionality.

The Domain layer contains the core entities Product and Material. These classes are responsible for storing data such as attributes, material composition, impact values, and recycling guidance, without containing application logic.

A Framework layer is planned to handle external concerns such as persistence (e.g., DatabaseManager).

The system follows a clear dependency direction: the Presentation layer depends on the Application layer, and the Application layer depends on the Domain layer and abstractions such as ImpactCalculator. The Domain layer remains independent of other layers.

This structure ensures clear separation of concerns and supports maintainability, testability, and extensibility.
