# Reactive Fakebook (Facebook?!) part 2

Demo of Spring WebFlux using the functional web framework API.

This is a completely different way of writing web applications with Spring, using a functional programming style API.

This is part 2. Part 1 is the same application, but then using the Spring WebFlux controller annotations API.

## Running MongoDB in a Docker container

This webapp requires a MongoDB database.

A quick and easy way to get MongoDB running is by running it in a Docker container.
If you have Docker installed on your computer, you can start MongoDB with the following commands:

To pull the official MongoDB Docker image from the central Docker repository (you need to do this only once):

    docker pull mongo

To create and start a MongoDB Docker container:

    docker run --name mongo-demo -p 127.0.0.1:27017:27017 -d mongo

To see if the container is running:

    docker ps

To stop and delete the container:

    docker stop mongo-demo
    docker rm mongo-demo
