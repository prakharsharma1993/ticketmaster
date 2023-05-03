# Back End Engineering Challenge


Developed a non-blocking Spring Boot GET API to fetch event details of an artist.

To access the API, you can use the following cURL command:

curl --location 'localhost:8080/ticketmaster/24/eventDetails'
Here, you need to replace {artistId} with the actual ID of the artist whose event details you want to fetch.


The API consumes data from three endpoints:

1. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/events.json` -> contains data for events. It links to artists and venues via ids
2. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json` -> contains data for artists.
3. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/venues.json` -> contains data for venues

The API endpoint /ticketmaster/{artistId}/eventDetails processes the request by retrieving the artist's details 
from the artists.json endpoint and filtering the list of events returned from the events.json endpoint by the artist ID.
It then retrieves additional details for each event from the venues.json endpoint and maps the data to the Event and 
Venue DTOs.
Finally, the API returns the Artist DTO with a list of events associated with that artist.

To improve the performance of the API, the endpoint makes use of the Flux and Mono reactive types to process data asynchronously, 
minimizing the amount of blocking time for the server.

I have created a generic method that consume above endpoint and generate DTO according the JSON

*Exception Handling 

The API also handles various exceptions like ArtistNotFoundException and 
BadRequestException to provide meaningful error messages to the client.

*Overall

The API codebase provides an example of how to create a non-blocking and reactive endpoint in Spring Boot, 
and how to consume data from multiple endpoints to create DTOs.
