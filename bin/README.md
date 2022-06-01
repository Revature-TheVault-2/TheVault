# TheVault2
Project 3 - Banking application with added features

## Project Descriptions

A banking application where you can create and manage checking and savings accounts.


## Technologies Used

- Java
- PostgreSQL
- Angular
  - HTML
  - CSS
  - TypeScript
- Spring
  - Spring Security
  - Spring Mail
  - Spring Data
  - Spring WebMVC
  - Spring DevTools
- Amazon AWS
  - EC2
  - CodeBuild
  - CodeDeploy
  - CodePipeline
  - RDS  
- Maven
- Lombok
- Jackson Databind
- JUnit Jupiter
- Mockito
- H2


## Features

- Email Alerts
- Authentication
- Export PDFs
- Budget Calculator
- Reset Password
- Notifications
- Light Mode

## Recomendations for Future Sprints

- Implement password hashing.
- Use OAuth2 authentication.
- Force HTTPS instead of HTTP.
- Allow for overdrafting in the code for withdrawals.
- Find a way to save the budget from the budget calculator.
- Tracking updating totals for withdrawals and deposits.
- Create different user types.
- Be able to lock or unlock an account when travelling.
- etc.


## Usage

1. When the page loads after startup, you will land on the login page. If you already have an account, you may log in, otherwise click the register button.
2. On the register account page, fill in your information and hit submit. You will be routed back to the log in page.
3. Now with an account created, enter your credentials and log in.
4. On the home page, you may edit your profile or create, delete, or access any accounts you may have.
5. On the edit profile page, you can update your personal information and set up email notifications, correct the fields you wish to change and hit submit.
6. To create an account, click on the drop down menu and select the type of account you wish to make and hit submit. A card will show up with that account's information.
7. To delete an account, you simply need to hit the delete button under the card associated with the account you wish to delete.
8. To select an account, click on the button that is on the card for the account you wish to work on.
9. Now, within the account selected, you will see the current and pending balances, the transaction history and be able to make transactions.
10. To create a transaction, click on the dropdown menu in the navbar above the account details. Here, you can select what kind of transaction you wish to perform.
11. For withdrawals and deposits, simply fill out the fields and hit submit.
12. For transfers, you must have another account, you will hit select on the account you wish to transfer to, enter the amount, and hit submit.
13. From any page once you've logged in, you can access the budget calculator. In the budget calculator you can set your monthly income and expenses to see how you can budget your monthly expenditure.
14. Where the transaction history appears, next to the search bar, there is an option to get a monthly report. Doing so will produce a pdf of your statments from the range selected.
15. On the main log in page, there is a button to reset password button that will take you to a new page, it will take in an email. You will recieve an email with a token and and a link to a new page. The link will take you to a page that will take in the token and a new password and update the password accordingly.
16. Finally, when you are done. Click the logout button.


## Contributors

- Brody Stevens
- Jake Rowe
- Caleb Gulledge
- Gibbons Alty
- Frederick Doell
- Christian Castro
- Colin Modderman
- Eric Mateo
- David Dominguez
- Dillon Meier
- Frank Carag
- Kendrickp Garcia
- Kimberly Vargas
- Stephen Brady
- Ramzy Malak
