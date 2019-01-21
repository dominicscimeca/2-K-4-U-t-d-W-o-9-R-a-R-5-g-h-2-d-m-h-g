#Dominic Scimeca Disney Dog Image API
would you insulate the app from a downstream API if it had one.

##See a live demo

`http://vpc-PublicL-3Z7KH9QE26C8-567865072.us-east-1.elb.amazonaws.com/swagger-ui.html`

##Running the app locally

This is a spring boot app and can be run with: 

`mvn spring-boot:run`

##Packaging the app

The app can be packaged and run with 

`mvn package`

## Capabilities

1. List all available dog images
1. See all available dog images grouped by breed
1. See all available dog images for a particular breed
1. See the total count (which is the sum of votes up and down for an image)
   1. Eg: If an image is voted up three time and down once it will have a vote count of 2 (which is 3 - 1)
1. Vote up or Vote down any particular dog image you like or dislike
   1. Voting requires login
      1. You can register on the app
   1. You can only vote on an image once. Additional attempts will be denied
     
      
## Documentation
Locally, after running the app, you can look at Swagger Documentation at 

`http://localhost:8080/swagger-ui.html`

## Publishing to AWS
### Creating AWS Infrastructure

1. `cd infrastructure`
1. `create-iam.sh`
1. `create-vpc.sh`

### Deploying to AWS

1. `publish.sh`