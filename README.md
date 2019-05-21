# MovieIT
A website to that acts like a movie database.Users can search for movies,add movies to their favourites and rate them,review movies,visit other users' profiles,edit their own profile.The administrator can add new movies to the database.To do all the above you have to register first.

## Setup
* You have to use a `Postgres` Database . You need to create a schema named `movieit`
* Change the file `application.properties` to match your postgres credentials
* `Hibernate` will take care of creating the schema tables
* In order to create an `Administrator` account you have to change `line 47` at `RegisterController.java` to `userService.createAdmin(user);`.You can't create such a role directly from the database since the passwords are hashed.
