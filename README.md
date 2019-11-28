# applause-tester-matching 
## Implementation of applause-tester-matching assignment

### Design Strategy:
- Built a gradle project using Spring-Boot and H2 database. 
- Created a database schema from each of the four csv files. As such the database created has four tables: DEVICES, BUGS, TESTER_DEVICE and TESTERS. I used H2 database because it is very fast, open source, in-memory database.
- REST controller: I implemented REST API, because it allows us to call the controller to interact with the database.
- Used Spring Boot framework because it has supports libraries such as lombok, JPA, actuator etc.
- Implemented controllers, models, repositories and services following the Spring framework architechture.
- Controller - It manages the data flow into model object. In this project, controller implements a REST call to the database and returns a 'OK' response or 'bad request' depending on the parameters passed. 
- Models - The model represents a Java object carrying data. I created four classes reprsenting the database tables that can be used as objects in this project
- Repositories - A repository is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects. The CRUD repository implements database queries such as findAll and helps to make quering easier.
- Service - the ReputationLookUpService has the logic to query the database according to the parameters passed and TesterExperienceService calculates the experience of the tester according to the logic described in the assignment.
- DTO - Exposing entities through endpoints can become a security issue if we do not carefully handle what properties can be changed through what operations. Hence I implemented 'Tester Experience' DTO so that they can help hiding implementation details of domain objects. 
- Added loggers through out the code to keep a track of the methods and help with easy debugging if required.
- Added unit tests having 88% class coverage, 75% method coverage and 86% line coverage promoting test driven development

### How to run: 
- Clone the repo
- Import the project as a gradle project to your editor 
- Run command './gradlew Bootrun' through your terminal
- Navigate to your browser and open your localhost: http://localhost:8080/applause/testers/experiences

### Walk Through Example 1:

* Query: 
  * http://localhost:8080/applause/testers/experiences?country=ALL&device=iPhone%204
* Output - 
  * [{"Name":"Taybin Rutkin","Experience":66},{"Name":"Sean Wellington","Experience":28},{"Name":"Miguel Bautista","Experience":23},{"Name":"Mingquan Zheng","Experience":21}]


### Walk Through Example 2:

* Query: 
  * http://localhost:8080/applause/testers/experiences?country=ALL&device=iPhone%204&device=iPhone%205
* Output - 
  * [{"Name":"Stanley Chen","Experience":110},{"Name":"Taybin Rutkin","Experience":66},{"Name":"Sean Wellington","Experience":58},{"Name":"Miguel Bautista","Experience":53},{"Name":"Leonard Sutton","Experience":32},{"Name":"Mingquan Zheng","Experience":21}]


### Walk Through Example 3:

* Query: 
  * http://localhost:8080/applause/testers/experiences?country=US&device=ALL
* Output - 
  * [{"Name":"Taybin Rutkin","Experience":125},{"Name":"Miguel Bautista","Experience":114},{"Name":"Michael Lubavin","Experience":99}]

### Future enhancements:

 - Add User Interface
 - Handle explicit mapping for /error
 - Handle case sentivity better
 
