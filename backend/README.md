# Software Engineers App

From Amigoscode video https://www.youtube.com/watch?v=Cw0J6jYJtzw

## Create a SpringBoot application

Using _Spring Initializr_

The tutor is using Intellij Ultimate so can add dependencies as he goes along.  If we keep _Spring Initializr_ open we 
can copy the dependencies across from the newly created build.gradle.

This project requires:
- Spring Web
- PostgreSQL Driver
- Spring Data JPA

Start with Spring Web and then add PostgreSQL Driver and Spring Data JPA after the docker step otherwise the app will 
fail to start

```shell
Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
Reason: Failed to determine a suitable driver class
```


## Set up a `SoftwareEngineer` object class 

The object should contain
- constructors - no args and all
- getters & setters
- `equals()` and `hashcode()` methods
- No `toString()` method

### Use of `equals()` and `hashcode()` methods
when the value of `.equals()` is true, i.e. the same object reference, then the hash codes must be the same integer value
If the hashcode method is called multiple times on the same object it must return the same value every time
2 different objects can have the same hash code i.e. they are not unique

## Set up a SoftwareEngineerController class
A controller is:
Anything that accepts requests from the client or exposes REST API endpoints

Using a `@RequestMapping("api/v1/software-engineers")` versioned api annotation
The `@GetMapping` has no parameters
The GET method returns an immutable list using `LIST.of`

## Set up a Postgres docker container

Use a `docker-compose.yaml` file

expose a different port to the default `5432` otherwise you may clash if the services is running on your machine

Run using `docker compose up -d` - detached mode

```shell
 ✔ Network softwareengineers_db    Created                                                                                                                                                                0.1s 
 ✔ Volume "softwareengineers_db"   Created                                                                                                                                                                0.0s 
 ✔ Container postgres-spring-boot  Started        
```

`docker ps`

```shell
CONTAINER ID   IMAGE             COMMAND                  CREATED              STATUS              PORTS                    NAMES
9003e501eadc   postgres:latest   "docker-entrypoint.s…"   About a minute ago   Up About a minute   0.0.0.0:5332->5432/tcp   postgres-spring-boot
```

`docker compose ps` gives the container name rather than the id

```shell
NAME                   IMAGE             COMMAND                  SERVICE   CREATED         STATUS         PORTS
postgres-spring-boot   postgres:latest   "docker-entrypoint.s…"   db        2 minutes ago   Up 2 minutes   0.0.0.0:5332->5432/tcp
```

`docker compose logs` will contain: 
`database system is ready to accept connections
`

## Connecting to Database

Use `application.properties` in `src/main/resources` folder

I've also added a `env.properties` to hide the credentials from being hard coded in a repo file 

Complete all the details.

Note: running the app still fails as the db doesn't exist

## Creating a PostgreSQL database

Unlike with mySQL you cannot automatically create a PostgreSQL db if it doesn't exist.

Instead you need to login to the docker container and create it manually via the terminal

```shell
docker compose ps # get the container name = postgres-spring-boot
docker exec -it postgres-spring-boot bash # log into the container as root
# psgl will fail as root so login as the db user
psql -U amigoscode
\l # lists all databases - Note that an `amigoscode` database exists but we might not want that
create database amigos;
\l # to check
```
Boom 💥💥

Now restart the application and it should connect 😀

## Create table

To create a table to match the `SoftwareEngineer` pojo we use `Spring Data JPA`

It's a framework providing a high level abstraction over `JPA` reducing boilerplate code
to make CRUD operations easy to implement

### @Entity & @Id annotations

**NOTE:** will also need something like `@GeneratedValue(strategy= GenerationType.IDENTITY)` to auto generate the Id

Add an `@Entity` annotation to the Software Engineer class, which will force the use of a primary key rather 
than the `Integer id` that is currently in place (although my code didn't)

The `id` property needs an `@Id` annotation as well to identify it as the **primary key**

**NOTE:** when you annotate a class with `@Entity` the table name created is the class name 
but split by underscore and lowercase by default.
You can change it like `@Entity(name = "table_name")`

```java

@Entity
public class SoftwareEngineer {
    @Id
    private Integer id;
    private String name;
    private String techStack;

    public SoftwareEngineer() {
    }
```

These now become columns in the database 😲

The logs show this 
```shell
Hibernate: 
    create table software_engineer (
        id integer not null,
        name varchar(255),
        tech_stack varchar(255),
        primary key (id)
    )
```

Which are visible due to the following lines in `application.properties`

```properties
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
```

### Connect to the database via the terminal

Once logged in to the container as `amigoscode` - see above

```shell
\c amigos # connects to amigos database
\d software_engineer # shows the table
```

```shell
                   Table "public.software_engineer"
   Column   |          Type          | Collation | Nullable | Default
------------+------------------------+-----------+----------+---------
 id         | integer                |           | not null |
 name       | character varying(255) |           |          |
 tech_stack | character varying(255) |           |          |
Indexes:
    "software_engineer_pkey" PRIMARY KEY, btree (id)
```

## JpaRepository Interface - Database Layer

Note there are other **repositories** that can be used e.g. `CrudRepository` 👀

This `SoftwareEngineerRepository` interface provides all the necessary methods for 
database interactions.  

```java
public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer, Integer> {
}
```
It is still possible to write custom methods if required 

`SoftwareEngineer` refers to the class (object type)
`Integer` refers to the `id` data type in `SoftwareEngineer` which has been annotated with
`@Id` to show it is the unique (primary) key of the database

TLDR; **Object & Key**


## SoftwareEngineerService  - Business Logic

The business logic is the queries, used via the JPA repository (interface with all the queries)
like returning all the engineers, adding an engineer and this is what is executed in the controller

`@Service` annotation makes the class a `bean` so Spring instantiates it,
so it can be used in other classes

**NOTE:** normally you would not return the entire `SoftwareEngineer` object as it may contain data that
you may wish to hide from the requester like password or personal data.

So you would return a DTO which might look more like this:

```shell
// dev version
softwareEngineerRepository.findAll();

// real version would abstract this
softwareEngineerRepository.findAll().stream().map()...
```

once the `SoftwareEngineerService` has been set up add it as a field to the controller

`private final SoftwareEngineerService softwareEngineerService;`

This means when you hit an endpoint the business logic of the service returns the results of a
query

## Inserting data into the DB

Once logged in: 
```shell
docker exec -it postgres-spring-boot bash # log into the container as root
psql -U amigoscode # change user to DB user
# Insert data - THIS WILL FAIL AS THERE IS NO ID
INSERT INTO software_engineer (name, tech_stack) VALUES ('james', 'js, node, react');
INSERT INTO software_engineer (name, tech_stack) VALUES ('jamila', 'java, spring, spring boot');
SELECT * FROM software_engineer; # don't forget the ;

```

To rectify in `SoftwareEngineer` we also add under the `@Id` tag an `@GeneratedValue` tag e.g.

`@GeneratedValue(strategy= GenerationType.IDENTITY)`

can also generate UUIDs etc

