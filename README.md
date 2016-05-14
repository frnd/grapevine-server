# ROADMAP

 - Data model validations.
 - Latest messages in resource object.
 - Test web sockets
 - GCM to push updates
 - Messages pagination. Use twitter philosophy
 - Improve message model:
    * coordinates: {"coordinates":[-97.51087576,35.46500176], "type":"Point"}
    * lang: 2 letters
    * embed: link with the embed url
 - Execute tests without database or configure maven to start a testing
  database.

# Prerequisites

This project uses:
 - Java JDK from version 1.7
 - Maven
 - RabbitMQ
 - MongoDB

## Installed utils for development.

### RabbitMQ Management Plugin

Management plugin is available on port of 8081, with the default 
username and password of guest / guest

## Docker

$ mvn -Dmaven.test.skip=true package docker:build
$ docker-compose up