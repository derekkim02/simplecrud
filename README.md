# simplecrud

Purpose
- This project is intended as practice to get familiar with Spring Boot by implementing a minimal CRUD backend.

Assignment

Create a simple CRUD controller.

Model
- id (Long)
- name (String)
- email (String)

Endpoints
- POST   /users        — create a user
- GET    /users        — list all users
- GET    /users/{id}   — get user by id
- PUT    /users/{id}   — update user by id
- DELETE /users/{id}   — delete user by id

Storage
- In-memory List or simple concurrent collection (no database). Use AtomicLong for id generation or a ConcurrentHashMap for storage.

Implementation notes
- User model class with fields: id, name, email (getters/setters or Lombok).
- UserService annotated with @Service to manage the in-memory collection and provide: create(User), findAll(), findById(id), update(id, User), delete(id).
- UserController annotated with @RestController to map endpoints to service methods. Accept and return JSON.
- Use dependency injection to inject the service into the controller.
- Recommended HTTP status codes:
  - POST: 201 Created
  - GET: 200 OK
  - PUT: 200 OK or 204 No Content
  - DELETE: 204 No Content
  - 404 Not Found when id does not exist
- Consider thread-safety: Collections.synchronizedList(...), CopyOnWriteArrayList, or ConcurrentHashMap + AtomicLong.

Minimal curl examples
- Create:
  curl -X POST -H "Content-Type: application/json" -d '{"name":"Alice","email":"alice@example.com"}' http://localhost:8080/users
- List:
  curl http://localhost:8080/users
- Get:
  curl http://localhost:8080/users/1
- Update:
  curl -X PUT -H "Content-Type: application/json" -d '{"name":"Alice A.","email":"alice.a@example.com"}' http://localhost:8080/users/1
- Delete:
  curl -X DELETE http://localhost:8080/users/1

Concepts exercised
- Controllers
- Services
- JSON request/response
- Request mappings
- Dependency injection (DI)
- REST endpoints
- Basic CRUD
- Spring Boot application lifecycle