# Back End Engineering Challenge

Below is a coding challenge that we would like you to solve.
Please read through the description carefully and implement a solution for it.
We don’t want you to over-engineer the solution but be prepared to extend the functionality in the next step of the interview process.
Finally, we ask you to submit a solution that you’d be happy to go live with and works “out of the box”.

## What we are asking you

We are looking for someone to design a RESTful API for data regarding events, venues and artists.

Given the following endpoints:

1. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/events.json` -> contains data for events. It links to artists and venues via ids
2. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json` -> contains data for artists.
3. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/venues.json` -> contains data for venues

Requirements:

    Demonstrate use of REST conventions
    Create route to:
        Get artist information for a specific artistId. This should contain all fields of given artist and all the events the artist will perform at.

Bonuses:

    Use appropriate HTTP status codes.

There is no need to implement the other implied endpoints

A few pointers about the data:

* An event happens in one venue
* One or multiple artists can perform in an event
* Multiple events happen in a venue
* A single artist can perform in multiple events

## Things we are looking for
* The service should fetch the files from S3, treating it as a REST endpoint. We are using S3 to keep things simple but we want to see a web rest client making calls to upstream :)
* The service should use reactive/non-blocking programming practices
* The service should be able to be started with a single command from the command line
* The submission should contain a git repository with commits
* The submission should contain a README with notes on how to build and run
* The README is also a good place to put notes on trade offs and design decisions
* Submit the git repository by mail as a zip file, or as a private github repository

Don't use libraries to integrate with S3. Treat it as a standard REST API. We want to see your code.
We do use things like SpringBoot. Most of our services run at Java 17. Feel free to do the same. If you do use a library then explain why in the readme.

## Things We Value
* Working software!
* Tests.
* Simple and readable code (but not necessarily easy!)
* Efficient code
* Good error handling
* Good observability
* We like functional constructs but also value good domain names and modelling.
* Evidence you have thought about edge cases and errors (either in code or the readme).

We will have a review session where we will ask questions about things like
* the chosen data structures
* Improvements that can be done to the provided input/output structures
* If time allows, we will ask you to extend the service.