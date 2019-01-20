#Dominic Scimeca Disney API Challenge

## Todos
1. [x] Init Readme
1. [x] Read through challenge carefully (in general)
1. [x] Determine Architectural Approach (in general)
1. [x] Init Spring Boot
1. [x] Dog Entity
1. [x] Breed Entity
1. [x] Get All Dogs Endpoint
1. [x] Get Dog Endpoint
1. [ ] Documentation
1. [x] Error Handling
1. [x] Authentication
1. [ ] Logging 
1. [ ] GMT
1. [ ] Swagger Documentation


# API Challenge

Design and create a RESTful API that could be used to manage a list of dog images. 

# Requirements

The operations we expect to see would be:

1. [x] List all of the available dog pictures grouped by breed
- get all dogs endpoint
- dog entity has a breed field
- breed entity
- dog picture entity with dog and url field
1. [x] List all of the available dog pictures of a particular breed
- get dogs by breed endpoint
1. [x] Vote up and down a dog picture
- voteup dog endpoint
- votedown dog endpoint
- vote entity
1. [x] The details associated with a dog picture

The information the Dog Breed App needs to function is:

1. [x] A URL to a dog picture
- validate correct url
1. [x] The number of votes a picture has received
- summation of votes
1. [x] The dog's breed
- Breed, name, id
1. [x] Any other information required to identify a specific dog

The Dog Breed App expects the response to be sorted by the number of votes a picture has received.

The API responses must be in JSON.

## Additional Voting Requirements

Each client is allowed to vote once for any particular dog picture.
- vote up and down endpoints need a user (authentication) (jwt)
- user entity [ id, email, password (hashed) ]
- register and login endpoints
- jwt is returned for auth endpoint
- jwt is checked at the start of vote up or vote down
- other endpoints do not need login (like if this is a public site etc)




# Build, Deployment and Running

While this falls outside of the challenge, please consider the following as you get ready to discuss your solution with the team:

* How would you package this for deployment?
* How and where would you deploy this app?
* How can you tell that the app is up and running?
* How would you configure the app as it goes from dev, to qa and finally to production?
* How would you insulate the app from a downstream API if it had one.