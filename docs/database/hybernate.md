# Hibernate en Entity Models

In je Spring Boot-project gebruik je **Hibernate** als de ORM (Object-Relational Mapping) tool om te communiceren met de **Oege-database**. Hibernate zorgt ervoor dat de data uit de database automatisch wordt gemapt naar je Java objecten (entity's), zoals bijvoorbeeld de `User` entity.

## User Entity

Een voorbeeld van een **User** entiteit die een `user` tabel in de database representeert:

```java
package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(name = "username", length = 255, nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50, nullable = false)
    private Role role = Role.USER;
}
```

In deze klasse:

- **`@Entity`** markeert de klasse als een JPA entity die naar een database tabel wordt gemapt.
- **`@Id`** geeft het primaire sleutelattribuut aan.
- **`@GeneratedValue`** zorgt ervoor dat de ID automatisch wordt gegenereerd.
- **`@Column`** specificeert details zoals de naam van de kolom, lengte, en of de waarde verplicht is.
- **`@Enumerated(EnumType.STRING)`** wordt gebruikt voor het opslaan van de `role` als een string in de database.

## Hibernate Configuratie

In de `application.properties` wordt de Hibernate-configuratie gedefinieerd, die de verbinding met de **Oege-database** regelt. Deze instellingen zorgen ervoor dat de gegevens automatisch worden opgeslagen in de database wanneer je de applicatie draait.

### Onze `application.properties`:

```properties
# Oege Database Configuration
spring.datasource.url=jdbc:mysql://oege.ie.hva.nl:3306/zkaradue2
spring.datasource.username=karadue2
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080
```

### Belangrijke Configuraties:

- **`spring.datasource.url`**: De URL van de Oege-database waar de gegevens naartoe gaan.
- **`spring.datasource.username`**: De gebruikersnaam voor de databaseverbinding.
- **`spring.jpa.hibernate.ddl-auto`**: De instelling `update` zorgt ervoor dat de database automatisch wordt bijgewerkt op basis van je entiteitsklassen. Hierdoor worden de tabellen automatisch aangemaakt en bijgewerkt bij het starten van de applicatie.
- **`spring.jpa.show-sql`**: Deze instelling zorgt ervoor dat de uitgevoerde SQL-query's zichtbaar zijn in de logoutput.
- **`spring.jpa.properties.hibernate.dialect`**: Dit stelt Hibernate in staat om met MySQL te werken.

## Direct naar de Database bij Starten

Met de bovenstaande configuratie zal Hibernate automatisch de entiteitsklassen (zoals `User`) naar de database schrijven wanneer je de applicatie start via `npm run dev`. Hibernate zorgt ervoor dat de tabelstructuren in de database overeenkomen met de entiteiten in je code. Het `ddl-auto=update` zorgt ervoor dat als er nieuwe velden of entiteiten zijn, deze automatisch worden toegepast op de database zonder handmatige tussenkomst.

---

## Conclusie

Met Hibernate en de configuratie in `application.properties` kunnen de entiteitsklassen direct communiceren met de Oege-database. Dit maakt het gemakkelijk om gegevens op te slaan, op te halen en te bewerken zonder complexe SQL-query's te schrijven. Wanneer je de applicatie start met `npm run dev`, worden de gegevens automatisch naar de database geschreven.