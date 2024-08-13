[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=k9targex_FaceitApi&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=k9targex_FaceitApi)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=k9targex_FaceitApi&metric=bugs)](https://sonarcloud.io/summary/new_code?id=k9targex_FaceitApi)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=k9targex_FaceitApi&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=k9targex_FaceitApi)
[![Duplications](https://sonarcloud.io/api/project_badges/measure?project=k9targex_FaceitApi&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=k9targex_FaceitApi)

# <center>Faceit Api</center>

## About the Project

Faceit Api is a project using JWT tokens, CQRS, Spring Security that provides a simple way to retrieve
player statistics in Counter-Strike 2: Global Offensive 2 using the Faceit API. Faceit is one of the leading
platforms for playing multiplayer online games such as Counter-Strike: Global Offensive, Dota 2, and
others.
<p align="center">
  <img src="Picture3.png" alt="Preview" width="1000"style="border-radius: 40px; overflow: hidden;">
</p>

## Security and Authentication

This project implements **Spring Security** to ensure that only authenticated and authorized users can access the application's resources. Authentication is handled through **JWT tokens**, which provide a secure and stateless way to manage user sessions.

- **JWT Tokens**: 
  - The tokens are configured with **HttpOnly** and **Secure** flags to enhance security by ensuring that the tokens are accessible only by the server and transmitted over secure connections (HTTPS).

- **Custom Error Handling**:
  - A custom error handler is in place to manage exceptions and errors consistently across the application. This ensures that the user experience remains smooth and that sensitive information is not exposed in error messages.

## Logging and Monitoring

The application includes a comprehensive **logging system** that tracks user actions and system events. This allows for detailed monitoring and auditing, which is crucial for maintaining security and performance.

## Caching

To improve performance and reduce the load on external APIs and databases, **caching** is implemented. This ensures that frequently accessed data is stored locally and retrieved quickly, reducing response times and improving user experience.

## Installation and Running

1. Clone the repository to your local computer:

    ```
    git clone https://github.com/k9targex/FaceitApi.git
    ```

2. Ensure you have Apache Maven version 3.9.6 or higher installed.

3. Open the command prompt or terminal and navigate to the project's root directory.

4. Execute the following commands:

    ```
    mvn clean install
    java -jar target\Faceit-0.0.1-SNAPSHOT.jar
    ```

   These commands will clean the project, compile and package it into a JAR file, and run the application at [http://localhost:8080](http://localhost:8080).

## Usage

### Home Page

1. Go to [http://localhost:8080](http://localhost:8080) in your web browser.

2. Enter the player's nickname in the input field and click the button to retrieve statistics.

3. The application will send a request to the Faceit API to retrieve the player's statistics and display them on the page.

### Example

Suppose you want to get statistics for a player with the nickname "s1mple".

1. Go to [http://localhost:8080](http://localhost:8080).

2. Enter "s1mple" in the input field and click the button.

3. You will see the statistics for the player "s1mple" provided by the Faceit API.
