# Stream Viewer Demo
##### Author: Athikom Vorapattanapong
##### Contact: Athikom.Vorapat@gmail.com


##### Assumptions:
1. I wasn't sure what the "event" in the description meant, so I assumed it was the most recent videos by the streamer. I tried looking up events in the api docs, however it is not yet avaialble to the public. Although there are some work arounds, it wasn't fully consistent so I decided to go with recent videos.
2. The code is optimized as much as possible within the given time frame. Not the most efficient but it is "good enough" with given time frame.
3. It wasn't clear in the description what was expected of webhooks so it will not be obvious in the UI if webhook works or not. I set the webhook processor to log everytime the subscribed event occur, however this is only visible in the heroku logs. Contact through my email above if you want to see those logs.

##### Notes:
1. If an error page appears, try clearing session and cache then refresh.
2. "Browser Back" is not fully implemented so it may cause unexpected behaviour

##### Answers to questions:
1. ***How would you deploy the above on AWS? (ideally a rough architecture diagram will help)***
    a. Create a Dockerfile with jetty as base image and then pack the Spring Boot project into a war file. Config the Dockerfile to copy this war file into the container and run the war file using jetty. This process can be streamlined using gradle tasks.
    b. Publish docker container to a remote repository (eg. jFrog). Again gradle tasks will help.
    c. Create an Ubuntu EC2 instance (or any other amis that have docker capability) and install docker.
    d. Run the published docker image on EC2 instance and connect a load balancer to the instance, exposing the instance to the internet.
    e. **Notes:**
    * a and b can be streamlined even further by using something like bitbucket pipelines where gradle tasks can be scheduled when commiting to master.
    * c and d can be further streamlined using provisioning software like chef 
    * Spring boot -> Docker -> Ec2 -> LoadBalancer -> Internet
    
2. ***Where do you see bottlenecks in your proposed architecture and how would you approach scaling this app starting from 100 reqs/day to 900MM reqs/day over 6 months?***
    Webhooks processor will be the most resource intensive process since we subscribe to many events. Embedded Streams and login will put most of the loads on Twitch Servers. 
    Scaling is quite hard if we follow how I deploy to AWS in #1 (Assumption was there is no scaling necessary). I would propose we make use of Kubernetes as our infrastructure (most service provides offer managed Kubernetes cluster like Amazon EKS or Google Compute Engine). We can implement auto-scaler to create as many pods as needed or delete them if not needed and then use an nginx to maintain sticky sessions.