# GamesDB

RESTful API for games I have played or want to play

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
  - OpenAPI/Swagger
### Tests
- ~~GameService~~
- ~~ConsoleService~~
- ~~StatusService~~
### Permanent database
- e.g. PostgreSQL
### Games (object)
- Fields:
  - ~~`Title` (string)~~
  - ~~`Status` (object)~~
  - ~~`Console` (object)~~
  - ~~`Score` (double)~~
    - ~~Implementation~~
    - ~~Ensure 0<=score<=10~~
    - ~~Ensure single decimal~~
  - `Sorting name` (string)
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
- On my website
- Authentication
  - My collection
    - Full read/write for me
    - Maybe read for others?
  - Temporary collection
    - Full read/write for everyone
  - Multiple users? Not sure
### Frontend (separate project)