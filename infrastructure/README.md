###Basic Infrastructure setup for Fargate with public LB and private subnet

#### IAM

1. `create-iam.sh` creates a cloudformation stack from the definition file `iam.yml`
    1. `iam.yml` defines 1 User and 3 roles
        1. EcsServiceLinkedRole - Role that needs to exist for ECS to work correctly
            1. https://stackoverflow.com/questions/47635331/cannot-create-ecs-service-via-cloudformation
        1. ECSRole - Role ECS needs to manage hosting instances and load balancer
            1. register and deregister containers with the LB
            1. create and update fargate / EC2
        1. ECSTaskExecutionRole - Role needed to execute the task of deploying
            1. Download from the image from ECR
            1. Create a log stream for the container
1. `create-vpc.sh` creates a cloudformation stack from the definition file `public-private-vpc.yml`
    1. `public-private-vpc` defines 
        1. 4 Subnets (2 public, 2 private)
        1. Internet Gateway
        1. Public Routes, Private Routes       

###Resources

####Using ECR
https://docs.aws.amazon.com/AmazonECR/latest/userguide/docker-basics.html

####Fargate
1. Fargate can use ALB, NLB (can't use ELBs) 
1. Fargate resources: https://github.com/nathanpeck/awesome-ecs#aws-fargate
1. Cloud formation examples: https://github.com/nathanpeck/aws-cloudformation-fargate  
    1. does not run out to the box!
        1. needs an AWS::IAM::ServiceLinkedRole
        1. which is included in the template here
1. Deep Dive into AWS Fargate: https://www.youtube.com/watch?v=IEvLkwdFgnU