# ood-project
This project is a group project within the Object Oriented Design course.
The goal is to design a menu-driven console program handling product management, material management, calculation of the environmental impact and lastly providing recycling guidance.
The members of the group are: Adrián Carrillo Jordán, Elliott Knotek, Flavio Colangelo and Osikoya Omotoyosi Nelson.
The roles are divided between the team member as follows:

# TODO
Our system is structured using a layered architecture consisting of Presentation, Application, Domain, and a planned Framework layer.

The Presentation layer contains the Menu class, which handles user interaction and acts as the entry point of the system. It delegates all user actions to the application layer and does not contain business logic.

The Application layer contains the coordination logic of the system. This includes ProductService and RecyclingGuidanceService, which handle use cases such as creating and retrieving products, calculating environmental impact, and generating recycling guidance. The layer also includes the EnvironmentalImpactCalculator interface and its implementations (WeightedByLifespanStrategy and SimpleSumStrategy), which define different calculation behaviours.

The Domain layer contains the core data structures, Product and Material. These classes are responsible only for storing data such as product attributes, material composition, and impact values, and remain independent of application logic.

A Framework layer is planned to handle external concerns such as persistence (e.g., DatabaseManager).

Dependencies follow a clear direction: Presentation → Application → Domain. The application layer depends on abstractions (e.g., EnvironmentalImpactCalculator) rather than concrete implementations, which keeps the system flexible and easier to extend.

- explain syntax and git stuff