# Library Spring Boot app

Simple Spring Boot app that is some sort of digital library.  <br/>
You can add people from /people/new and so is for the books books/new, edit them, assign books to people and release them from book owning. <br/>
Also there simple search for the books and simple check if they are expired (took more than 10 days ago). <br/>
SQL queries for creating and filling tables are in `main/sql`(app uses PostgreSQL)

## Guideline

To run the app, clone this repository and run main method in LibraryBootApplication.class or use Maven.
Run your Database locally or remote. <br/>
The app was created with Spring Initialzr and contains following dependencies: <br/>
Spring Web starter, Thymeleaf, Hibernate validation, Lombok, PostgreSQL JDBC Driver. <br/>
By default it uses Hibernate, so do not forget to create and set up `application.properties` in `resources`<br/>
### Example of `application.properties`:

**#** Data source config <br/>
spring.datasource.driver-class-name=org.postgresql.Driver <br/>
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db <br/>
spring.datasource.username=user <br/>
spring.datasource.password=password <br/>
**#** Hibernate config <br/>
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect <br/>
spring.jpa.properties.hibernate.show_sql=true <br/>

`spring.jpa.properties.hibernate.show_sql=true` means that all of the Hibernate queries will be printed in console. <br/>
Delete the line if it is not necessary.
