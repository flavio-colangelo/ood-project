# Development Requirements
## Functional Requirements
- Create products with name, catrgory, etc.
- List registered products
- Define materials with: name, environmental impact value, and recycling
category/instruction
- Calculate total environmental impact for a product based on its materials
- Provide recycling guidance based on the product’s material composition
- Handle mixed-material products in a reasonable and documented way
## Non-functional Requirements
- Make the program easy to use
- Make the database be able to be backuped
- Make the user not be able to input invalid data
# System Boundary
## Inside Boundary
- Product management
- Impact calculation
- 
## Outside Boundary
- Database for products and materials
# Domain Concept Identification
PRODUCT (value object)
<br>name<br>category<br>estimated_lifespan<br>material_list

MATERIAL (value object)
<br>name

RECYCLING GUIDANCE (service)
<br>name

IMPACT CALCULATOR (service)

DATABASE MANAGER (entity)


# CRC Cards

## Products - value
The Product class is responsible for storing its attributes such as name, category, estimated lifespan, and materials. It provides access to its materials for the Environmental Impact Calculator and Recycling Guidance.

| Responsibility | Collaborators |
| :------------- | :------------ |
| Know its attributes | Materials |
| Hold list of materials | Environmental Impact Calculator |
| Expose composition for Environmental Impact Calculator | Recycling Guidance |
|  | Category |
|  |  |

## Materials - value
The material class is responsible for storing information about itself such as name and recycling guidance. The material knows its attributes. The material makes its composition available for the Product.
| Responsibility | Collaborators |
| :------------- | :------------ |
| Know its attributes | Product |
| Be reusable across products |  |

## Special Recycling Category - value
The Special Recycling Category class is responsible for storing information about itself such as name and recycling guidance. The Special Recycling Category knows its attributes. The Special Recycling Category makes its composition available for the Product.
| Responsibility | Collaborators |
| :------------- | :------------ |
| Know its attributes | Product |
| Be reusable across products |  |

## *Recycling Guidance - service
The Recycling Guidance class provides the user with the guidance based on the product’s material or category. Based on the materials and it receives a proper guidance from the database through Product.


| Responsibility | Collaborators |
| :------------- | :------------ |
| Identify the material(s) of a product | Product |
| Identify the category of a product | |
| Curate recycling guidance |  |
| Handle mixed materials |  |

## Impact Calculator - service
The Impact Calculator calculates the environmental impact of a product based on its material. It uses the composition of a product to calculate the environmental impact.
| Responsibility | Collaborators |
| :------------- | :------------ |
| Calculate environmental impact | Product |

<!-- ## Database Manager - entity
| Responsibility | Collaborators |
| :------------- | :------------ |
| Hold database credentials |  |
| Fetch from database |  |
| Store in database |  | -->

<!-- ## Menu (entity)
| Responsibility | Collaborators |
| :------------- | :------------ |
| Store menu options | Products |
| Display menu options |  |
| Handle user input |  | -->

# UML Class Diagram
```puml
@startuml diagram

class RecyclingGuidance {

}

class Product {
    - name : String
    - category : Category
    - estimatedLifespan : Integer
    - material : List<Material>
}

class Material {
    - name : String
    - recyclingGuidance : List<String>
}

class ImpactCalculator {

}

class Category {
    - name : String
    - recyclingGuidance : List<String>
}

' class DatabaseManager {
'     - username : String
'     - password : String
' }
' 
' class Menu {
'     - menuOptions : List<String>
' }

Product "*" *-- "1" Category
Product "*" *-- "*" Material
Product <-- RecyclingGuidance : curates from
Product --> ImpactCalculator : uses
' Menu --> Product : uses
' DatabaseManager <-- Product : uses
' DatabaseManager <-- Material : uses
' Menu --> RecyclingGuidance : uses

@enduml diagram
```

# Git Commands
`git clone`<br>
`git clone https://github.com/flavio-colangelo/ood-project`<br>
`git checkout nameOFTheBranch`<br>
`git checkout -b nameOfTheBranch`<br>
bug/date/nameOfTheBug<br>
feature/addProducts<br>
`git status`<br>
`git add fileName.extension`<br>
`git add .`<br>
*.class<br>
`git commit -m 'enter your message here'`<br>
`git push origin branchName`<br>
`git pull branch` <br>
`git branch`<br>
`git branch -d nameOfTheBranch`
`git fetch origin development`