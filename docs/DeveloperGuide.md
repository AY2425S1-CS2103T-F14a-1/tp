---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# UGTeach Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `StudentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Student` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a student).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="800" />

The `Model` component,

* stores the address book data i.e., all `Student` objects (which are contained in a `UniqueStudentList` object).
* stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add a new student


The add command is used to add a new student to the address book. The `AddCommandParser` is responsible for parsing the user input and creating an `AddCommand` object. The `AddCommand` object is then executed by the `Logic` component.

`AddCommandParser` obtains the values corresponding to the prefixes `n/` `p/` `e/` `a/` `t/` `s/` `r/` `paid/` `owed/` from the user input. The `AddCommandParser` will enforce the following constraints:
* There is no preamble text between the `add` command word and the prefixes.
* The prefixes `n/` `p/` `e/` `a/` `t/` `s/` `r/` must be provided (`paid/` and `owed/` are optional).
* If the prefixes are provided, they must appear for only once.
* All values corresponding to the prefixes that are provided must be non-empty and valid.

If the constraints are not met, the `AddCommandParser` will throw a `ParseException` with an error message indicating the constraint that was violated.
Otherwise, a new instance of `Student` is created with the values obtained from the user input. 
A new instance of `AddCommand` is then created with the `Student` instance.

On execution, `AddCommand` first queries the supplied model if it contains a student with both an identical name **and** an identical phone number. If no such student exists, `AddCommand` then calls on `model::addStudent` to add the student into the addressBook data.

Finally, `AddCommand` queries the model to see if the student's schedule clashes with others in the address book. If conflicts are found, a warning message is displayed along with the conflicting students.

Below is an activity diagram when [Adding a new student](#add-a-new-student)

<puml src="diagrams/AddCommandActivityDiagram.puml" alt="AddCommandActivityDiagram"/>


The following sequence diagram shows how an add operation goes through the `Logic` component:
<puml src="diagrams/ParseArgsToGetStudentFieldReferenceFrame.puml" alt="ParseArgsToGetStudentFieldReferenceFrame"/>
<puml src="diagrams/AddSequenceDiagram-Logic.puml" alt="AddSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `AddCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an AddCommand operation goes through the `Model` component is shown below:

<puml src="diagrams/AddSequenceDiagram-Model.puml" alt="AddSequenceDiagram-Model" height="500"/>



#### Design considerations:

**Aspect: How add command is carried out:**

* **Alternative 1 (current choice):** Key in all the details for student in one command.
  * Pros: Easy to implement, as only one command needs to be key in by user.
  * Cons: Command might get too long.

* **Alternative 2:** Key in the details for students in multiple steps.
  * Pros: A step-by-step guide for adding details can be especially helpful for new users, as it offers clear and structured guidance.
  * Cons: It is hard to implement, especially with a mix of optional and compulsory fields. 
  Additionally, it is not user-friendly for fast typists, as multiline commands are required to add a student.


### Owe tuition fees

The owe command is part of UGTeach's payment tracking feature. It is used to track the amount of tuition fee owed by a student. The `OweCommandParser` is responsible for parsing the user input and creating an `OweCommand` object. The `OweCommand` object is then executed by the `Logic` component.

`OweCommandParser` obtains the `INDEX` of the student and the values corresponding to the prefix `hr/` from the user input. The `OweCommandParser` will enforce the following constraints:
* The `INDEX` must be a positive integer.
* The prefix `hr/` must be provided.
* If the prefixes are provided, they must appear for only once.
* Value corresponding to the prefix that is provided must be non-empty and valid (positive multiple of 0.5).

If the constraints are not met, the `OweCommandParser` will throw a `ParseException` with an error message indicating the constraint that was violated.
Otherwise, a new instance of `OweCommand` is then created with the values of `INDEX` and `HOURS_OWED` parsed by `OweCommandParser`.

On execution, `OweCommand` first queries the supplied model for the student to be updated using the `INDEX`. 

Then, `OweCommand` calculates the amount of tuition fee owed and checks if the total amount owed by the student exceeds the limit of `9999999.99`. If it exceeds, `OweCommand` will throw a `CommandException` with an error message indicating that limit was violated.

Finally, `OweCommand` updates the total amount of tuition fee owed by the student by creating a new `Student` instance with updated fields to replace the outdated `Student` instance in the model.

The following activity diagram summarizes what happens when a user wants to track payment after a lesson:
<puml src="diagrams/PaymentTrackingActivityDiagram.puml" width="750"/>

How an OweCommand operation goes through the `Model` component is shown below:

<puml src="diagrams/OweSequenceDiagram-Model.puml" alt="OweSequenceDiagram-Model" height="1400" />

#### Design considerations:

**Aspect: How owe executes:**

* **Alternative 1 (current choice):** Calculations for amount owed done by UGTeach.
    * Pros: User friendly.
    * Cons: May have performance issues due to the need to fetch data and perform calculations.

* **Alternative 2:** Calculations for amount owed done by the user.
    * Pros: Easy to implement.
    * Cons: Might not be user-friendly as user would need to find out what is the 
    tuition rate charged and calculate how much tuition fee did the student owe.


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:
* is a full time university student with fixed schedule
* is giving private tuition to a significant number of students
* has a need to manage a significant number of tutees' information
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Empower undergraduate private tutors to efficiently manage payments, and organize schedules using CLI.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                  | So that I can…​                                                         |
|----------|--------------------------------------------|-------------------------------|-------------------------------------------------------------------------|
| `* * *`  | new user                                   | see usage instructions        | refer to instructions when I forget how to use the App                  |
| `* * *`  | tutor                                      | add a new student             | keep track of my tutee's information                                    |
| `* * *`  | user                                       | delete an entry               | remove entries that I no longer need                                    |
| `* * *`  | private tutor                              | view all my students' details | have an overview of how many tutees I am managing                       |
| `* * *`  | tutor with many students                   | find a student by name        | locate details of tutees without having to go through the entire list   |
| `* *`    | busy undergraduate tutor                   | find students by date         | locate details of tutees that has tuition on a specific date            |
| `* *`    | new user                                   | see sample entries            | understand how the interface will look like with entries added          |
| `* *`    | user                                       | edit contact details                                       | keep my information up-to-date                 |
| `* *`    | busy undergraduate tutor                   | check who owes me tuition fee                              | identify and remind them to pay                |
| `* *`    | busy undergraduate tutor                   | be reminded of my tuitions for today                       | remember to teach for today (if any)           |
| `* *`    | busy undergraduate tutor                   | have an overview of the tuition fee earned/ owed as of now | easily keep track of how much more I should receive |
| `* *`    | forgetful user                             | detect duplicates                                          | avoid manually finding and deleting the duplicates |
| `* *`    | forgetful user                             | tag users with date and time of tuition                    | differentiate between different contacts    |
| `*`      | user                                       | hide private contact details  | minimize chance of someone else seeing them by accident                 |
| `*`      | user with many students in the address book | sort students by name          | locate a student easily                                                  |
| `*`      | user that types fast                       | be able to undo my actions    | revert back if I have made a mistake                                    |
| `*`      | user with many students in the address book | sort students by name                                       | locate a student easily                         |
| `*`      | busy undergraduate tutor                   | have information of both the children and his/her guardian | contact either of them                         |
| `*`      | tutor with many students                   | to know which guardian is associated with which children   | know which student is under that guardian/ vice-versa |


### Use cases

(For all use cases below, the **System** is the `UGTeach` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Adding a student**

**MSS**
1. User enters command to create new student entry.
1. System displays success message and command line is cleared.

   Use case ends.

**Extensions**

* 1a. System detects error in entered command.
    * 1a1. System displays error message and does not clear command line.
    * 1a2. User enters new command.<br>
  Steps 1a1-1a2 are repeated until all details entered are correct.<br>
  Use case resumes from step 2.<br><br>
  
* 1b. System detects error in parameters.
    * 1b1. System displays error message and does not clear command line.
    * 1b2. User enters command with correct parameters.<br>
  Steps 1b1-1b2 are repeated until all details entered are correct.<br>
  Use case resumes from step 2.


**Use case: UC02 - Read all entries**

**MSS**
1. User enters command to view all entries.
1. System displays list with all entries to the user.

   Use case ends.

**Extension**
* 1a. System detects error in entered command.
    * 1a1. System displays error message and does not clear command line.
    * 1a2. User enters new command.<br>
  Steps 1a1-1a2 are repeated until all details entered are correct.<br>
  Use case resumes from step 2.<br><br>

* 1b. System detects the list is empty.
    * 1b1. System shows an empty list.<br> 
  Use case ends.

**Use case: UC03 - Read total earnings**

**MSS**

1. User enters command to read total earnings and total amount owed by the students. 
1. System displays total earnings and total amount owed to the user.

   Use case ends.

**Extension**
* 1a. System detects error in entered command.
    * 1a1. System displays error message and does not clear command line.
    * 1a2. User enters new command.<br>
  Steps 1a1-1a2 are repeated until all details entered are correct.<br> 
  Use case resumes from step 2.


**Use case: UC04 - Delete a student entry**

**MSS**

1. User requests to <ins>list students(UC02)</ins>.
1. User enters command to delete a specific student.
1. System displays list with specified student deleted from the list.

   Use case ends.

**Extensions**

* 2a. System detects error in format of entered command.
    * 2a1. System displays error message and does not clear command line.
    * 2a2. User enters command with new index.<br>
  Steps 2a1-2a2 are repeated until index entered is correct.<br>
  Use case resumes from step 3.<br><br>


* 2b. System detects error in format of entered command.
    * 2b1. System displays error message and does not clear command line.
    * 2b2. User enters new command.<br<
  Steps 2b1-2b2 are repeated until all details entered are correct.<br>
  Use case resumes from step 3.


**Use case: UC05 - Find student entries**

**MSS**

1. User enters command to find students.
1. System displays list with students with matching details.

   Use case ends.

**Extensions**

* 1a. System detects error in entered command.
    * 1a1. System displays error message and does not clear command line.
    * 1a2. User enters new command.<br>
  Steps 1a1-1a2 are repeated until all details entered are correct.<br>
  Use case resumes from step 2.


**Use case: UC06 - Receiving tuition fee from a student**

**MSS**

1. User requests to <ins>find a student(UC05)</ins>.
1. User enters command to record payment received from the specified student after a lesson.
1. System updates the total tuition fee paid by the student.
1. System displays success message.

   Use case ends.

**Extensions**

* 1a. System cannot find the specified student.
    * 1a1. User <ins>adds the student to the system (UC01)</ins>.<br>
  Use case resumes from step 1.<br><br>

* 2a. System detects error in entered command.
    * 2a1. System displays error message and does not clear command line.
    * 2a2. User enters new command.<br>
  Steps 2a1-2a2 are repeated until all details entered are correct.<br> 
  Use case resumes from step 3.


**Use case: UC07 - Settle outstanding fees for student**

**MSS**

1. User requests to <ins>find a student(UC05)</ins>.
1. User enters command to settle outstanding fees for the specified student.
1. System updates the total tuition fee paid and total tuition fee owed by the student.
1. System displays success message.

   Use case ends.

**Extensions**

* 1a. System cannot find the specified student.
    * 1a1. User <ins>adds the student to the system (UC01)</ins>.<br>
  Use case resumes from step 1.<br><br>

* 2a. System detects error in entered command.
    * 2a1. System displays error message and does not clear command line.
    * 2a2. User enters new command.<br>
  Steps 2a1-2a2 are repeated until all details entered are correct.<br>
  Use case resumes from step 3.

### Non-Functional Requirements
**Environment Requirements**
1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.

**Data Requirements**
1. All the data should be stored in human-editable files and must not be stored using DBMS. 
1. Data should be saved permanently and must not be affected by power outage.

**Performance Requirements**
1. Should be able to hold up to 1000 students without any noticeable sluggishness in performance for typical usage. 
1. For any simple usage, the application should be able to respond within 2 seconds.

**Accessibility**
1. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse. 
1. The user interface should work appear seamlessly for screens with standard resolutions (1920x1080) and higher.
1. The user interface should be easy to navigate and intuitive, with clear labels, and large enough texts. 
1. The application should provide clear help sections for users, explaining how to use its features.

**Concurrency Control**
1. Should only be used by one user at a time, meaning it is designed for a single user and cannot be accessed or shared by multiple users simultaneously. 

**Testability**
1. The software should not depend on any remote server and should be able to run at any time. 
1. The application should be able to launch without an installer.
1. Features should be testable without any external access e.g., remote APIs, audio players, user accounts, internet connection, after the initial download of the application's jar file.

**Security Requirements**
1. The application is assumed to be used locally without creating any user account.
1. Data stored in human-editable files is assumed to be highly secured and not damaged.

**Maintainability Requirements**
1. The codebase should be modular and well-documented (i.e. JavaDoc, following abstraction and cohesion) to ensure ease of maintenance and updates.
1. The application must use a version control system to track changes and maintain multiple versions of the software.

**Logging**
1. Activity Logs: The system should log all user activity and critical events for security auditing and troubleshooting.


### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **DBMS**: Database Management System, a software providing tools for structural data storage.
* **CLI**: Command-line interface where you interact with the system using your keyboard
* **API**: Application Programming Interface, a set of programming code that enables data transmission between one software and another
* **Abstraction and Cohesion**: Abstraction is hiding all but relevant data in order to reduce complexity and increase efficiency. Cohesion is the degree which elements belong together
* **Version Control System**: Version control is the practice of tracking and managing changes to software code, and there are many version control systems such as Git, Apache Subversion etc

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.
   
   1. Open a command terminal, `cd` into the folder that you put the jar file in.

   1. Run the jar file with the command in the terminal `java -jar ugteach.jar`<br>
      Expected: Shows the GUI with a set of sample contacts and a reminder for lessons scheduled today. 
      The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Finding students

1. Finding students by name

    1. Test case: `find n/Alex`<br>
       Expected: Only students whose name contains keyword `Alex` listed.

    1. Test case: `find n/Alex Bernice`<br>
       Expected: Only students whose name contains keyword `Alex` **OR** `Bernice` listed.
   
    1. Test case: `find n/Alex!`<br>
       Expected: Search not performed. UGTeach shows an error message.
    
    1. Other incorrect find commands to try: `find n/  `, `find n/Bernice!`<br>
   (where keyword supplied contains non-alphanumeric characters or only whitespace)<br>
       Expected: Similar to previous.

1. Finding students by day
    1. Test case: `find d/Thursday`<br>
       Expected: Only students whose tuition day falls on `Thursday` listed.

    1. Test case: `find d/Wednesday Thursday`<br>
       Expected: Only students whose tuition day falls on `Wednesday` **OR** `Thursday` listed.
   
    1. Test case: `find d/Thur`<br>
       Expected: Search not performed. UGTeach shows an error message.
    
    1. Other incorrect find commands to try: `find d/`, `find d/foo`<br>
    (where days supplied does not match any day or contains only whitespace)<br>
       Expected: Similar to previous.

1. Finding students by name or day
   1. Test case: `find n/Alex d/Thursday`<br>
      Expected: Only students whose name contains keyword `Alex`<br>
      **AND** their tuition day falls on `Thursday` listed.
   
   1. Test case: `find d/Thursday n/Alex `<br>
      Expected: Similar to previous.

   1. Test case: `find d/Alex Bernice d/Wednesday Thursday`<br>
      Expected: Only students whose name contains keyword `Alex` **OR** `Bernice`<br>
      **AND** their tuition day falls on `Wednesday` **OR** `Thursday` listed.
   
   1. Test case: `find n/Alex d/`<br>
      Expected: Search not performed. UGTeach shows an error message.
   
   1. Other incorrect find commands to try: `find n/ d/Thursday`, `find n/Alex! d/Thursday`, `find n/Alex d/Thur`<br>
   (where keywords supplied contains non-alphanumeric characters or only whitespace) or<br>
   (where days supplied does not match any day or contains only whitespace)<br>
      Expected: Similar to previous.

### Deleting a student

1. Deleting a student while all students are being shown.

   1. Prerequisite: List all students using the `list` command. There should be **at least 1 student** listed.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. UGTeach displays success message with details of the deleted student.

   1. Test case: `delete 0`<br>
      Expected: No student is deleted. UGTeach displays error message.

   1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. Deleting a student from a filtered list.

    1. Prerequisite: Find a student using the `find` command. There should be **at least 1 student** found.

    1. Test case: `delete 1`<br>
        Expected: First contact is deleted from the filtered list. UGTeach displays success message with details of the deleted student.

    1. Test case: `delete 0`<br>
       Expected: No student is deleted. UGTeach displays error message.

    1. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
        Expected: Similar to previous.
   
### Getting a reminder

1. Getting a reminder when there are lessons scheduled for today.

    1. Prerequisite: There should be **at least 1 lesson** scheduled for today.

    1. Test case: `remind`<br>
         Expected: UGTeach displays success message with details such as student's name, time of the lesson and the subject to be taught.

1. Getting a reminder when there are no lessons scheduled for today.

    1. Prerequisite: There should be **no lessons** scheduled for today.

    1. Test case: `remind`<br>
        Expected: UGTeach displays congratulatory message for having no lessons scheduled today.

### Saving data

1. Dealing with missing/corrupted data files

   1. Prerequisite: There is a folder named `data` in the same directory as the jar file, and there is a `ugteach.json` file in the `data` folder.

   1. Test case: Delete the `ugteach.json` file.<br>
       Expected: UGTeach should create a new `ugteach.json` file with default data.
        
   1. Test case: Delete the `data` folder together with the `ugteach.json` file.<br>
       Expected: Similar to previous.

   1. Test case: Corrupt the `ugteach.json` file by changing its contents to invalid format.<br>
   e.g. add a non-alphanumeric character to one of the student's name.<br>
       Expected: UGTeach should discard all data in the file and start with an empty `ugteach.json` file.

