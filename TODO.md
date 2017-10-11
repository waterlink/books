SETUP iMAC
==========
1. Download repository using
git clone https://github.com/waterlink/books
2. Open Project from IntelliJ
- Choose Auto import
- specify ECMAScrip 6
3. Backend will start to run on localhost:9090/v1/books
4. To startup Frontend
- Open terminal and navigatie to project folder .../books/frontend
- run command:
npm install
5. For iMac install Watchman
brew install watchman
5. To start frontend run command:
npm start -s

New functionality on the Book sample application
================================================
1. Select member (that will borrow the book)
This can initially be achieved by just entering the correct uuid and then
progressing to the borrow book function.
Second phase can be to select the members from the members table and then
proceed to the borrow book function.
This feature should drive out the need/use of a FrontEnd router in our
react/redux context.
2. Create a feature/Integration/Acceptance/UI test
Setup and create an integration test. Use selenium for this functionality and
test the flow of starting the application, showing a list of books and borrow a
book from the list by the member using the application.
3. Show borrowed books for a member
This should help you learn a ONE-TO-MANY relationship in JPA.
4. Add, Delete, Modify book information
This is basically more of the same functionality and part of creating standard
CRUD functionality.
5. Add, Delete, Modify members
Identical to number 3.
Once this functionality is available, it might be useful
to start the application with an empty database and add checks for at least
having one member and one book in the database before the borrow functionality
can be accessed:
6. Check that at least one book and one member are present before enabling the
borrow function (Change the GUI such that the borrow button is greyed out or
not)
7. Implement check that book can not be deleted or borrowed when the status is
unavailable (meaning it has been borrowed)
8. Implement function to return one or more book(s) to the library
9. Setup local mysql or greenplum database in your dev environment.
This will replace the H2 in-memory database and will allow you to setup a
database locally as well.
10. Setup the deployment for the FrontEnd part in the CI pipeline
Currently we are building and deploying the back-end only. Next step is to setup
the front-end deployment. Steps include to build the production bundle, run the
tests and deploy to CF.
11. Split the CI pipeline in logical steps:
- Step 1: Run tests
- Step 2: Build & Deploy to Acceptance (only if tests are succesfull)
Optional
- Step 3: immplement caching artifacts to speed up the pipeline throughput
12. Create a login integration with Facebook
The user goes to the frontend application and authenticates using the Facebook
login option. When the user that is returned from the Facebook page is already
known in the member table, that will identify the user that wants to borrow a
book. If the user cannot be found in the member table, a new user can be added
in the member table and the associated Facebook identification is also stored
in the member table.
13. Create SSO (Single Sign On) with CloudFoundry for our books app
Allow the user to authenticate by means of the SSO option that is available in
our CF environment.
14. Create SSO (Single Sign On) with CloudFoundry for the Seabiscuit app
Time to put nr 12 into practice and make it happen in our own app....
