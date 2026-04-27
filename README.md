# ood-project
This project is a project within the Object Oriented Design course.
The goal is to design a menu-driven console program handling product management, material management, calculation of the environmental impact and lastly providing recycling guidance.

# TODO

Architectual Decisions:

The application is structured using a layered architecture with the Presentation, Application and Domain Layer.

In the first Presentation layer we have the Menu class. This class facilitates the interaction between the user and system. It handles the display of menus and user choices.

The next Application layer consists of the ProductService class, RecyclingGuidanceService class and strategies WeightedByLifespan and SimpleSumStrategy, implementing the EnvironmentalImpactCalculator interface.
The Product,  and Material classes exist in the Domain layer.
