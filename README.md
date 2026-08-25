# GamesDB

RESTful API for games I have played or want to play

## Running locally
- Install Java 21 (other versions may work)
- Set up a PostgreSQL server where you have the credentials to a user with read/write permissions
- Create a database on the PostgreSQL server
- Have the PostgreSQL server running (e.g. on host localhost:5432)
- Define environment variables
  - Depending on how you run the application
    - VSCode: Define in launch.json
    - Intellij: Define in Run/Debug Configurations
    - Command line: Pass as arguments when running the .jar
  - Define the following variables (replacing the square brackets with the relevant values)
    - `"SPRING_DATASOURCE_URL": "jdbc:postgresql://[host]/[database name]"`
    - `"SPRING_DATASOURCE_USERNAME": "[PostgreSQL user username]"`
    - `"SPRING_DATASOURCE_PASSWORD": "[PostgreSQL user password]"`
- Run the application
  - From your IDE, or
  - From the terminal
    - Build with one of the following from the root folder
      - Linux: `mvn clean package`
      - Windows/MacOS: `mvnw clean package`
    - Run with `java -jar target/[name of the created .jar] [environment variables]`

## Roadmap
### Improvements
- DecimalMin/DecimalMax aren't actually supported for Double. Consider:
  - BigDecimal
  - Custom Score object with valid range and one decimal
### Standard API features
- CRUD
  - ~~Basic implementation~~
  - ~~Validation~~
  - Check duplicates
- ~~RESTful~~
  - ~~HATEOAS~~
- Pagination
- Sorting
- Filtering
- Search (fuzzy?)
- Documentation
  - ~~Swagger~~
  - Implement more helpful documentation
### Tests
- ~~GameService~~
- ~~ConsoleService~~
- ~~StatusService~~
### Permanent database
- ~~PostgreSQL~~
### Games (object)
- Fields:
  - ~~`Title` (string)~~
  - ~~`Status` (object)~~
  - ~~`Console` (object)~~
  - ~~`Score` (double)~~
    - ~~Implementation~~
    - ~~Ensure 0<=score<=10~~
    - ~~Ensure single decimal~~
  - ~~`Sorting name` (string)~~
  - `Artwork`? (image/link?)
- ~~CRUD~~
### Status (object)
- ~~I'm leaning towards object, just to make it flexible~~
- ~~CRUD~~
- ~~Return StatusResponse for good measure~~
### Console (object)
- ~~Needs to be flexible since there will keep coming new consoles~~
- ~~CRUD~~
### Hosting
- ~~On my website~~
- Container?
- Authentication
  - My collection
    - Full read/write for me
    - Maybe read for others?
  - Temporary collection
    - Full read/write for everyone
  - Multiple users? Not sure
### Frontend (separate project)