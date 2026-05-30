# Outline for Week 9

We refactored existing code without adding features, reduced coupling and tried keeping our tests green while making changes.

## What we changed in the project

- Cleanup/refactoring of repositories and database logic.

- Better separation between Menu, Services, Domain and DB logic.

- README/documentation updates.

- UML + flow/sequence diagrams.

- Menu refactoring using `MenuOption` and `Runnable` to reduce large `if/else` menu logic and make the menu easier to maintain and extend.

- Strategy refactoring around `SimpleSumStrategy` and `WeightedByLifespanStrategy` for environmental impact calculations.

## Testing as a safety net

We ran tests before and after refactoring.

We tried fixing failing tests and making sure behaviour stayed the same.

We used Gradle/JUnit to verify nothing broke.

## Some design improvements were

Much better separation of responsibilities (SRP).

Higher cohesion / lower coupling.

Cleaner structure and easier maintainability.

## Challenges / reflection

Keeping tests green while refactoring.

Small changes + testing after each change.

Making sure behaviour stayed the same while improving structure.
