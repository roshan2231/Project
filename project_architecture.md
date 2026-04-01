# Voting System Architecture & Complete Data Flow

This document explains the entire, step-by-step technical workflow of your Spring Boot Voting System. You can save this file natively as a PDF via your browser or Visual Studio Code (File > Print > Save as PDF).

---

## 🏗️ 1. Concept: The Layered Architecture
This project strictly follows the **N-Tier Layered Architecture** design pattern. It separates concerns, making the code incredibly secure and predictable. The layers stack onto each other exactly in this order:

1. **Client / Postman** *(The outside world asking for data)*
2. **Security Layer** *(The Gatekeeper mapping HTTP Filters)*
3. **Controller Layer** *(The Receptionist routing the URL mapping)*
4. **Service Layer** *(The Brain running Business Logic)*
5. **Repository Layer** *(The Librarian fetching Database records)*
6. **Database** *(MySQL Storage)*

---

## 🔗 2. Data Connectivity & Database
Your project instantly connects to a local server behind the scenes. 
Inside your `application.properties` file, Spring Boot looks for `spring.datasource.url` which points to exactly your MySQL Server. 
Spring Boot uses **Hibernate** (an Object-Relational Mapping framework). Because of Hibernate, you never have to write raw SQL strings (like `INSERT INTO Voting...`). Instead, Spring Data JPA automatically converts your Java Objects (`@Entity` classes) into mapped MySQL tables automatically.

---

## 🚦 3. The Complete Flow: Step-By-Step Example 
*To understand how everything fits together, let's trace exactly what happens when you press **Send** on Postman to **cast a vote**.*

### Step 1: The Client (Postman)
You enter `POST http://localhost:8080/api/votes`, insert your `Bearer Token` in the Authorization tab, attach a JSON body containing your vote, and hit Send. The network request travels as an HTTP packet to the Spring Boot embedded Tomcat Server running on port 8080.

### Step 2: The Security Layer (`JwtFilter` & `SecurityConfig`)
Before the application even looks at the URL you requested, Spring Security intercepts the packet! 
1. The **`JwtFilter`** intercepts the request and pulls out the `Bearer Token` from the header.
2. It uses `JwtUtil` to rip apart the token cryptographically and verify it hasn't expired and it actually belongs to a `voterId` using your secret key signature. 
3. It creates an `Authentication` session.
4. Next, the **`SecurityConfig`** looks at your URL (`/api/votes`) and verifies your session has `hasRole("USER")`. If everything is valid, it opens the gate!

### Step 3: The Controller (`VotingController.java`)
The request safely reaches the Controller. Specifically, it hits the `@PostMapping` method marked with `@RequestMapping("/api/votes")`. 
1. The Controller acts strictly as a receptionist. It grabs the raw JSON String sent from Postman.
2. Because of your `@RequestBody VoteDto dto` mapping, Spring dynamically transforms that raw JSON text into a usable Java `VoteDto` Object automatically.

### Step 4: The Service (`VotingServiceImpl.java` & Converters)
The Controller instantly passes the `VoteDto` down to the Service Layer using `@Autowired`. This is the "Brain" of the application.
1. Spring triggers `castVote(VoteDto dto)`. 
2. The Service runs **Business Logic**. For example, it checks: *Has this user already voted for this position before?*
3. To do that, the Service asks the Repository Layer: `voteRepository.existsByUserIdAndPositionId(...)`.
4. If they already voted, the Service throws a `DuplicateVoteException` instantly! Your `GlobalExceptionHandler` intercepts it and blasts a clean JSON Error Exception back up to Postman.
5. If the logic is safe, it asks the **EntityDtoConverter** to translate the superficial `VoteDto` object into a heavy Database-linked Entity Object (`Vote.java`).

### Step 5: The Repository (`VoteRepository.java`)
The Service hands the heavy `Vote` object to `VoteRepository.java`.
Because your Repository `extends JpaRepository<Vote, Long>`, you didn't have to code the saving logic! Spring Data JPA natively understands this interface. You just invoke `.save(vote)`.

### Step 6: Database (MySQL Execution)
Inside the `save(vote)` call, **Hibernate** jumps into action. It dynamically generates a `INSERT INTO Vote (user_id, position_id, ...)` query under the hood, opens a fast connection pool strictly onto MySQL on Port 3306, executes it securely, and returns the successfully committed Database Object (wrapped with its new unique auto-incremented primary Key ID) back to the Repository layer. 

### Step 7: The Return Trip
1. The `VoteRepository` hands the saved entity back up to your `VotingServiceImpl`.
2. The Service converts the heavy Database Model back into a lightweight, safe `VoteDto` object and returns it to the `VotingController`.
3. The Controller returns it as a generic `ResponseEntity<VoteDto>`.
4. The embedded Tomcat server parses this `ResponseEntity` dynamically back into raw JSON text, and throws the packet over the network directly back to **Postman**, yielding a pretty format and a `200 OK` or `201 Created` green response! 

*Total execution time behind the scenes: ~12 milliseconds.*
