# Back End Engineering Challenge


Developed  non-blocking Spring Boot GET API to fetch event details of an artist.

To access the API, you can use the following cURL command:

curl --location 'localhost:8080/ticketmaster/24/eventDetails'
Here, you need to replace {artistId} with the actual ID of the artist whose event details you want to fetch.


The API consumes data from three endpoints:

1. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/events.json` -> contains data for events. It links to artists and venues via ids
2. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json` -> contains data for artists.
3. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/venues.json` -> contains data for venues


Exception Handling 

The API also handles various exceptions like ArtistNotFoundException and 
BadRequestException to provide meaningful error messages to the client.

You can refer to the codebase to get more details on the implementation. 

