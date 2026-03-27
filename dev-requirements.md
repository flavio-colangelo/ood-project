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

RECYCLING GUIDANCE (??)
<br>name

IMPACT CALCULATOR (service)

DATABASE MANAGER (entity)

MENU (entity)

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
`git branch`<br>
`git branch -d nameOfTheBranch`

# CRC Cards

## Products - value
| Responsibility | Collaborators |
| :------------- | :------------ |
| Know its attributes | Materials |
| Hold list of materials | Environmental Impact Calculator |
| Expose composition for Environmental Impact Calculator | Recycling Guidance |
|  | Database Manager |

## Materials - value
| Responsibility | Collaborators |
| :------------- | :------------ |
| Know its attributes | Products (referenced by) |
| Be reusable across products | Materials |
|  | Database Manager |

## Recycling Guidance - service
| Responsibility | Collaborators |
| :------------- | :------------ |
| Identify the material(s) of a product | Products |
| Fetch material guide from database | Database Manager |
| Handle mixed materials |  |

## Impact Calculator - service
| Responsibility | Collaborators |
| :------------- | :------------ |
| Calculate environmental impact | Product |

## Database Manager - entity
| Responsibility | Collaborators |
| :------------- | :------------ |
| Hold databsae credentials |  |
| Fetch from database |  |
| Store in database |  |

## Menu (entity)
| Responsibility | Collaborators |
| :------------- | :------------ |
| Store menu options |  |
| Display menu options |  |
| Handle user input |  |