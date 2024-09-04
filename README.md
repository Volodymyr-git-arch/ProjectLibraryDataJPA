# ProjectLibraryDataJPA
## Project info

My project is a digital library,
a standard CRUD application in 
which we 
can access the Database through 
a browser, and can ***create***, ***read***,
***update***, and ***delete***.
entities. 

We use Spring Data JPA,
 ***repositories*** and special
***service*** classes - in which we write
all the logic of the work.
We do not use DAO and Jdbc Template,
SQL queries happen under the hood
Spring Data JPA.
In our case there are 2 
entities:
the ***Person***  and the ***Book***.

My application is
Spring Boot,
which contains a built-in web server.
Boot automatically configures
configuration and
dependency compatibility. To run
the application I only need to run
the main method in a special class.



To display the view, we use ***html***
forms with a ***thymeleaf***.
***Thymeleaf*** is a template
engine that
allows you to dynamically display
changes in forms.
### 1.PostgreSQL database

We use a connection to the
PostgreSQL database.

In the database, there are 2 tables, 
a ***person*** and a ***book***, 
the relationship 
between them is ***one to many***,
 one person can have many books. 

When receiving a person entity, the loading
of related entities occurs lazily, and when
receiving a book entity, the loading of the
related person entity is not lazy.
### 2.Entities 
In the model package we have 2 entities
***Person*** and ***Book***,
linked to tables in the database. The fields
in each entity
are validated by annotations,
all errors when filling in
the fields are placed in the
***bindingResult*** object and Spring then
displays each error to the user. There are also
getters and setters.
### 3.PeopleController
### 3.1  List of all readers
http://localhost:8080/people - 
By clicking on this ***url*** we
perform a GET request for the ***index*** method
PeopleController,and thus we get the entire
list of library readers.

In the library, you can get a list of all 
readers on the page in the browser,
we can see the details of each person:
***fullName*** and ***yearOfBirth***.

There is also a link to create a new person,
after pressing the button the
following happens:
##### 3.1.1 GET request
GET request to method ***new person***
we get empty form of person.
##### 3.1.2 POST request
After filling it 
click the button and 
POST request
to method ***create*** 
 creates a new person in
the database.
###  3.2 Individual person page
http://localhost:8080/people/id - GET request in 
PeopleController, method ***show***.
For this ***url*** the user's 
personal page is shown, you can also click
on a specific person in the general list.
On the person's page, there
are buttons:
##### 3.2.1 ***edit*** - editing person data
GET request on method ***edit*** in 
PeopleController put an existing
person into a form,
after updating the form,
the data is sent by POST
request to the 
***update*** method, updating data in the
database.

##### 3.2.2 ***delete*** - removal of a person
Deleting a person from the database
 occurs
by a DELETE request to the controller method
***delete***.
##### 3.2.3 ***list of books***

The  ***list of books*** that stored
by this person is also displayed, if a book
is stored for more than a
certain number of days,
it is displayed in red. This is done by
the CSS code in the presentation page 
***resources***->***templates***->
***people***->***show.html***.
### 4.BooksController
### 4.1 General list of books
http://localhost:8080/books - we type in the 
browser and get all books;


###  4.2 Individual book page
http://localhost:8080/books/id -  we type in the
browser and рget a specific
book, or click
on a book in the general list.

Going to the book page, we have standard 
links:
##### 4.2.1 Delete 
##### 4.2.2 Edit
##### 4.2.3 Drop down list function

On the page of each specific book we have
a button with a drop-down list of all 
readers, so if the book free, 
we can assign this book to a
person, if the book is already occupied, 
then we have a button to release the book
when the reader returns it back.

This is done by the controller methods
***release*** if the book is already occupied,
and ***assign*** if the book is free.
### 5. Search book by the first letters
There is a book search function
by the first letters.
http://localhost:8080/books/search - 
this ***url*** calls the ***searchPage*** 
method
in the BookController,which returns
 an empty
search form, where
we can enter the first letters
of the book title (the first
letter must be capital).

Then after
pressing the search button
POST request is sent to the method
***makeSearch***, and if such a book
exists, its page is displayed to us,
as well as its owner,
if the book is currently taken by someone.
### 6. Information about some classes
### 6.1 Services
In the services folder we have classes
BooksService, and PeopleService
in which usually all the business logic of 
our application is written.


In the controller we do not 
access the repository
directly, we call methods
of the service, and the service in turn
calls methods of the repository,
which interacts with the database.
#### 6.1.1 BooksService - Pagination and sorting
In the Book service method
***findWithPagination***
using ***Pagination and sorting***
can divide the list
of books into pages, and sort by year.
To do this, in the parameters of the
GET request for the entire list
of books, we specify the
number of books on one
page, the page number, and
the boolean value whether to sort
books by year or not.
#### 6.1.2 PeopleService

In method ***getBooksByPersonId*** in 
PeopleService we get the entity
person,
to load the related entities - books, 
using lambda we go through the list of
books, and assign an expiration
date of 10 days in milliseconds
for the books, when this period passes,
the book will be expired, 
and will be highlighted in red.

In this method, we have here we have
lazy loading of books,
but since we iterate over books,
they will definitely be loaded, but
it does not hurt to always call
Hibernate.initialize
if the code changes in the future and the iteration
over books is deleted.
### 6.2 Repositories

In the repository we have only custom
methods written,
standard methods in the repository
do not need to be written, it already 
knows about them.
#### 6.2.1 BooksRepository

In ***BooksRepository*** we have custom
method ***findByTitleStartingWith*** - 
in which 
we can find book by the 
first few letters in database.
#### 6.2.2 PeopleRepository

In ***PeopleRepository*** we have custom method
***findByFullName*** in which we find a person 
by his ***fullName*** in database.
### 6.3 PersonValidator
The ***PersonValidator*** class in the
***util*** package performs a more 
complex Spring
validation than simple field validation.
It checks whether such a person
already exists in the database 
by their ***fullName*** when a librarian
registers a person.
If it does, it returns 
an error that such a person
already exists.


### Conclusion
Thus, this application allows you to carry
out the work of the library in a digital 
format.


**Project author: Volodymyr
Krizhanivsky - Backend Engineer**