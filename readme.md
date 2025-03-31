# Setting JPA Managed Types via HibernatePropertiesCustomizer

as suggested in [this StackOverflow answer](https://stackoverflow.com/a/79542185/10263724).

## Overview
This project demonstrates an idea how to replace the `@EntityScan` annotation with manual configuration to include only specific entity classes. The goal is to avoid scanning the entire package and instead include only the necessary entity classes.

Spring Boot 3.4.4
--> [Hibernate 6.6.x as Spring Boot managed dependency](https://docs.spring.io/spring-boot/appendix/dependency-versions/coordinates.html)


## Motivation
In a large modulith (modular monolith) project there might be shared entities that are used by multiple modules.
The modules share a database, but not every module should have access to every table, while some tables need to be accessed by multiple modules. This is enforced by different DB Users per module and respective DB User Grants (not in this sample project).
The problem is, that the entity scan includes all entities in the specified package and sub-packages.
If an entity is included in the entity scan for which the module does not have permission to access the underlying table, the application will fail at runtime.
One workaround is to put every entity class into a dedicated package and specify every entity-package on a fine-grained level in the entity scan annotation. This is somewhat cumbersome - a lot of boilerplate code and useless packages are created.
The `@EntityScan` annotation also offers a property `basePackageClasses` that allows specifying single classes, but this is often misunderstood. It is just a type-safe alternative to specifying the package names as Strings. The scan checks the package where the class is located and includes all entities in this package to the scan, not only the single entity class.


## The idea
The sample project implements a different approach to manually specify the entity classes in a configuration class, instead of using `@EntityScan`. 
This is done to ensure that only the required entity classes are included.

The `Book` entity is located in a 'remote' package/domain to avoid auto-discovery. By that, the scenario of shared/remote entities is constructed in this example.
The `Book` entity needs to be explicitly added as managed type. This is done through a [HibernatePropertiesCustomizer](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/orm/jpa/HibernatePropertiesCustomizer.html) bean in configuration class [`JpaConfig`](./src/main/java/org/example/bookstore/config/JpaConfig.java).


## Running the Application
1. Use the provided `docker-compose.yml` file to spin up a MySQL database
1. Edit `/resources/schema.sql` to insert some books into the table.
1. Run the Spring Boot application.
1. Invoke endpoints via [test.http](./test.http) file.

