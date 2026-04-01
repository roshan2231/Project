# Detailed Logic Review: The Complete Service Layer

This document expands our deep dive into the business logic to cover every other Service currently active in your codebase. These Services handle the data manipulation rules before anything touches the MySQL database.

---

## 👥 1. User Management (`UserServiceImpl.java`)
This service handles creating and mapping the complex User objects securely.

```java
public UserDto registerUser(UserDto userDto) {
    // 1. UNIQUE VALIDATION: First, we query the SQL database via userRepository.
    // If the voterId string already exists in the table, we aggressively reject the transaction 
    // to prevent two users from claiming the exact same voter identity.
    if (userRepository.existsByVoterId(userDto.getVoterId())) {
        throw new IllegalArgumentException("User with voterId " + userDto.getVoterId() + " already exists!");
    }

    // 2. We convert the incoming lightweight DTO from Postman down into the Database Entity.
    User user = converter.toUserEntity(userDto);
    
    // 3. SECURE HASHING: This is critical! We NEVER save plain text passwords.
    // The BCrypt passwordEncoder converts "Password123!" into an irreversible hash string (e.g. $2a$10$wK1...).
    user.setPassword(passwordEncoder.encode(userDto.getPassword())); 

    // 4. We execute the SQL INSERT command via the repository.
    User savedUser = userRepository.save(user);
    
    // 5. We return the saved data (without the password) back up to the Controller.
    return converter.toUserDto(savedUser);
}

public UserDto updateUser(Long id, UserDto userDto) {
    // 1. We must verify the target user actually exists first. 
    // The `.orElseThrow` ensures we instantly stop Execution and return a structured 404 Not Found error if the ID is missing.
    User found = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

    // 2. We individually update safe properties...
    found.setName(userDto.getName());
    found.setEmail(userDto.getEmail());
    found.setConstituency(userDto.getConstituency());
    found.setRole(userDto.getRole());
    
    // 3. We ONLY update the password if the user actually sent a new one in the PUT request!
    // If it is provided, we run it through the BCrypt Encoder again.
    if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
        found.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }

    // 4. Save updates and return cleanly.
    User updatedUser = userRepository.save(found);
    return converter.toUserDto(updatedUser);
}
```

---

## 🏛️ 2. Election & Position Management (`ElectionServiceImp.java`)
This Service is restricted strictly to users with the `ROLE_ADMIN` authority and handles structural setup.

```java
public ElectionDto createElection(ElectionDto dto) {
    Election election = converter.toElectionEntity(dto);
    
    // 1. AUTOMATIC ACTIVATION: When an Admin creates a brand new Election via Postman,
    // the code forcibly maps the Status Enum to ACTIVE natively. 
    // You do not need to pass "status" in the JSON payload!
    election.setStatus(Status.ACTIVE);
    
    Election saved = electionRepository.save(election);
    return converter.toElectionDto(saved);
}

public ElectionDto updateElection(Long id, ElectionDto dto) {
    Election found = electionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + id));

    found.setName(dto.getName());
    found.setDetails(dto.getDetails());
    found.setStartDate(dto.getStartDate());
    found.setEndDate(dto.getEndDate());
    
    // 1. DYNAMIC TOGGLING: This is the logic driving your "Activate/Deactivate" endpoint.
    // If the Admin provides a new "status" (e.g., INACTIVE) in the JSON payload, we map it into the database!
    // If they omit the status, a Ternary Operator (? :) executes ensuring we just keep whatever status was already there natively!
    found.setStatus(dto.getStatus() != null ? dto.getStatus() : found.getStatus());

    return converter.toElectionDto(electionRepository.save(found));
}

public PositionDto createPosition(PositionDto dto) {
    // Standard Object conversion execution for positions linked via Election IDs.
    Position position = converter.toPositionEntity(dto);
    Position saved = positionRepository.save(position);
    return converter.toPositionDto(saved);
}
```

---

## 📊 3. The Analytics Engine (`ResultServiceImpl.java`)
This service contains the dynamic logic to actually calculate voting results in real-time. 

```java
public List<VoteResultDto> getResultsByElectionAndPosition(Long electionId, Long positionId) {
    
    // 1. It hits the Database to ask: "Give me every single Candidate linked to THIS specific Position ID inside THIS specific Election ID"
    // (For example: Who is running for Tech President inside Election 1?)
    List<Candidate> candidates = candidateRepository.findByElectionIdAndPositionId(electionId, positionId);
    
    // 2. We initiate an empty List to dynamically store our custom Results object mapping.
    List<VoteResultDto> results = new ArrayList<>();
    
    // 3. We create a "For Loop" that iterates precisely through every single candidate found.
    for (Candidate c : candidates) {
    
        // 4. THE CALCULATION: For the current loop's specific candidate, we command the SQL Database 
        // to execute an aggregate SELECT COUNT(*) query exclusively against their Candidate ID inside the massive Voting table!
        long count = voteRepository.countByCandidateId(c.getId());
        
        // 5. We construct a brand new VoteResultDto holding the Candidate's Name, their Party, and the Total Number of Votes computed above,
        // and we push it into our Results List!
        results.add(new VoteResultDto(c.getId(), c.getName(), c.getParty(), count));
    }

    // 6. Return the sorted analytics Array payload back down to Postman!
    return results;
}

public long getTotalVotesInElection(Long electionId) {
    // 1. A remarkably fast aggregation query. Instead of downloading all Vote Objects to count them in Java (which is extremely slow),
    // we instruct the MySQL indexing engine natively through Hibernate to just return the RAW INTEGER count of votes assigned into `electionId`.
    return voteRepository.countByElectionId(electionId);
}
```
