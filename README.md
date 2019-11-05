# Profile Microservice

Example implementation of a profile microservice using Lagom

[![MIT License](https://img.shields.io/github/license/danieldirks/lagom-profile-example.svg)](./LICENSE)


## Installation

Make sure you have a running Java environment and [sbt](https://www.scala-sbt.org/) set up.

Clone/download the repository and run `sbt runAll` inside the project root to start the microservice on port `9000`. It will take a while to set up all dependencies on the first run.

**DISCLAIMER:** This repository is for educational purpose only. Do not use this in production as I cannot give any stability or security guarantees.


## Project structure

The API is defined in [ProfileService](./profile-api/src/main/scala/org/example/profile/ProfileService.scala) and implemented in [ProfileServiceImpl](./profile-impl/src/main/scala/org/example/profile/ProfileServiceImpl.scala).

Lagom uses [CQRS](https://en.wikipedia.org/wiki/Command%E2%80%93query_separation#Command_query_responsibility_segregation) and [Event Sourcing](https://en.wikipedia.org/wiki/Domain-driven_design#Relationship_to_other_ideas) to store and execute requests. The entities behavior is defined in [ProfileEntity](./profile-impl/src/main/scala/org/example/profile/ProfileEntity.scala), Commands, Queries (= Read-only commands) and Events in [ProfileCommand](./profile-impl/src/main/scala/org/example/profile/ProfileCommand.scala) and [ProfileEvent](./profile-impl/src/main/scala/org/example/profile/ProfileEvent.scala) and the state is stored in [ProfileState](./profile-impl/src/main/scala/org/example/profile/ProfileState.scala).

The [Profile message](./common/src/main/scala/org/example/protocols/Profile.scala) is located inside an additional subproject to allow usage outside of the microservice. In real cloud platforms this should be a shared dependency to maintain message integrity across all microservices.


## Usage

This should only demonstrate the use of Lagom's REST interface.

| Action | Method | Description | Parameters | Request body | Response body | Code |
| ------ | ------ | ----------- | ---------- | ------------ | ------------- | ---- |
Create profile | PUT | Creates a new profile with <email> and <name> and returns it. | None | [ProfileCreate](./profile-api/src/main/scala/org/example/profile/ProfileCreate.scala) | [Profile](./common/src/main/scala/org/example/protocols/Profile.scala) | [cURL](#create-profile)
Get profile | GET | Returns the profile | UUID | None | [Profile](./common/src/main/scala/org/example/protocols/Profile.scala) | [cURL](#get-profile)
Update profile | POST | Updates the profile (id and email are currently immutable) | UUID | [Profile](./common/src/main/scala/org/example/protocols/Profile.scala) | Done | [cURL](#update-profile)
Delete profile | DELETE | Deletes the profile | UUID | None | Done | [cURL](#delete-profile)
Ping | GET | Use for health checks | None | None | Done | [cURL](#ping)

### cURL

#### Create Profile
```
curl -X PUT -d '{"name":"<name>","email":"<email>"}' localhost:9000/v1/profile
```

#### Get profile
```
curl localhost:9000/v1/profile/<uuid>
```

#### Update profile
```
curl -X POST -d '{"id":"<uuid>","email":"<email>","name":"<name>"}' localhost:9000/v1/profile/<uuid>
```

#### Delete profile
```
curl -X DELETE localhost:9000/v1/profile/<uuid>
```

#### Ping
```
curl localhost:9000/ping
```


## License

The project is licensed under the [MIT License](./LICENSE).
