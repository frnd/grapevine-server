Platform to create a conversation from a url link. The place where you get your local
news--from your colleagues or friends.

Side project started for learning purposes. In a very early state.

# ROAD MAP

 - Data model validations.
 - Responses cache.
 - Integrate Embedly or similar.
 - Integrate Archive to take snapshot of link when link is sent.
 - Test web sockets
 - Test GCM to push updates
 - Messages pagination. Use twitter philosophy
 - Improve message model:
    * coordinates: {"coordinates":[-97.51087576,35.46500176], "type":"Point"}
    * lang: 2 letters
    * embed: link with the embed url
 - Create tests and execute tests without database or configure maven to start a testing
  database.
 - HATEOAS

# Know Errors

 - WebSocket connection to 'ws://localhost:8080/ws/775/m3pf3sd2/websocket' failed:
 Error during WebSocket handshake: Sent non-empty 'Sec-WebSocket-Protocol' header but no
 response was received

# Prerequisites

This project uses:
 - Java JDK from version 1.7
 - Maven
 - RabbitMQ
 - MongoDB

## Installed utils for development.

### Docker

You can use docker to start the server and mongodb

$ mvn -Dmaven.test.skip=true package docker:build
$ docker-compose up

### Mongo Express

You can access Mongo Express on http://localhost:8081/

### RabbitMQ Management Plugin

NOTE: At this moment RabbitMQ is not used.

Management plugin is available on http://localhost:8081/, with the default
username and password of guest / guest

