# Create Shrading table for URL Shortner with Docker
    We are gonna to make sharding for URL Shortner in Postgres DB.

# Commands
**Notes -** This is contain in init.sql file
- **Command For Create Tables** :-
    CREATE TABLE URL_TABLE
    (
        id serial NOT NULL PRIMARY KEY,
        URL text,
        URL_ID character(8)
    )
- **Docker File**:-
    FROM postgres
    COPY init.sql /docker-entrypoint-initdb.d

- **Build Docker Image**:-
    docker build -t pgshard .

-  **Run**:-
    Shard 1 :- docker run --name pgshard1 -p 5432:5432 -d pgshard
    Shard 2 :- docker run --name pgshard1 -p 5433:5432 -d pgshard
    Shard 3 :- docker run --name pgshard1 -p 5434:5432 -d pgshard

- **How to test it**:-
    docker ps