# Core Source Code Explanation (Line-By-Line)

This completely breaks down the most critical files in your Spring Boot application. It highlights the exact lines that make the logic execute securely. You can print this breakdown directly to a PDF for your technical presentation.

---

## 🔐 1. Authentication (`AuthController.java`)
This is the entry point where users ask for their session token.

```java
@PostMapping("/login")
public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
    // 1. Hands the identifier and password to Spring's internal AuthenticationManager.
    // This securely compares the typed string against the BCrypt hash in the Database natively!
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword()));
    
    // 2. If the password matches, we load the heavy User object from the database using your custom CustomUserDetailsService.
    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getIdentifier());
    
    // 3. We pass the verified user object to JwtUtil to securely generate a dynamic JWT String mapped to them.
    final String jwt = jwtUtil.generateToken(userDetails);
    
    // 4. Wrap the JWT inside an AuthResponse JSON and throw it back to Postman with a 200 OK status!
    return ResponseEntity.ok(new AuthResponse(jwt));
}
```

---

## 🧠 2. Loading the User (`CustomUserDetailsService.java`)
This is how Spring Boot talks to your MySQL Database to evaluate identities securely.

```java
@Override
public UserDetails loadUserByUsername(String voterId) throws UsernameNotFoundException {
    // 1. Hits the Repository (MySQL database) seeking exactly the voterId sent from Postman.
    // If empty (.orElseThrow), instantly rejects the login by throwing an Exception!
    User user = userRepository.findByVoterId(voterId).orElseThrow(
            () -> new UsernameNotFoundException("User not found with voterId: " + voterId));

    // 2. Wraps the heavy pure database 'User' entity into a safe 'CustomUserDetails' object.
    // This allows Spring Security to evaluate "ROLE_USER" strings dynamically without exposing your raw User passwords internally.
    return new CustomUserDetails(user);
}
```

---

## 🛡️ 3. The Security Firewall (`SecurityConfig.java`)
Before any request enters your Application bounds, it hits this global network filter.

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // 1. Disables CSRF (Cross-Site Request Forgery) because JWT Tokens intrinsically solve this vulnerability.
    http.csrf(AbstractHttpConfigurer::disable)
            // 2. Begin defining precise Authorization routing borders for every URL.
            .authorizeHttpRequests(auth -> auth
                    // a. The Gateway: /api/auth routes are public so unregistered users can register.
                    .requestMatchers("/api/auth/**").permitAll()
                    // b. Admin Territory: Creating Elections and Candidates requires a strict "ROLE_ADMIN".
                    .requestMatchers("/api/elections/**", "/api/candidates/**").hasRole("ADMIN")
                    // c. User Territory: Casting votes requires a strict "ROLE_USER" token string.
                    .requestMatchers("/api/votes/**").hasRole("USER")
                    // d. Read Only: ALL other requests (like querying Results) just need ANY valid token.
                    .anyRequest().authenticated())
            // 3. Forces Spring Boot to act "Stateless". Meaning every single request MUST contain the JWT, the server remembers nobody in RAM!
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 4. Puts your custom 'jwtFilter' mathematically in front of the default Password Filter, enforcing Token checks first!
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
```

---

## 🗳️ 4. The Business Logic Engine (`VotingServiceImpl.java`)
This is the absolute most complex processing block in the entire Codebase. Here is exactly what happens when you press the Cast Vote button!

```java
public VoteDto castVote(VoteDto dto) {
    // 1. Hooks into the current secure authenticated session (the JWT context established milliseconds earlier)
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    // 2. Extracts the unique voterId encoded silently inside the user's Token.
    String authenticatedVoterId = authentication.getName();

    // 3. SECURE MISMATCH REJECTION: Checks if the ID they put in the JSON Payload matches who they logged in as.
    // This strictly prevents "Alice" from logging in, but sending "Bob's" voterId in the POST JSON!
    if (!dto.getVoterId().equals(authenticatedVoterId)) {
        throw new IllegalArgumentException("Unauthorized voting attempt: voterId mismatch.");
    }

    // 4. DUPLICATE REJECTION: Queries MySQL. "Has this exact User ID already submitted a vote for this specific Position ID before?" 
    // Inherently enforces one-vote-per-position rules directly against the SQL table.
    if (voteRepository.existsByUserIdAndPositionId(dto.getUserId(), dto.getPositionId())) {
        throw new DuplicateVoteException("User has already voted for this position!");
    }

    // 5. Converts the lightweight 'VoteDto' coming from Postman into a heavy, Database-ready 'Vote' Entity Object.
    Vote vote = converter.toVoteEntity(dto);

    // 6. ELECTION STATUS CHECK: Looks through the linked Objects to the Election Status row.
    // If the Election has been marked INACTIVE by an admin, rejects the execution natively.
    if (vote.getElection().getStatus() != Status.ACTIVE) {
        throw new IllegalArgumentException("Cannot vote in an inactive election!");
    }

    // 7. Success! Hands the perfectly validated Entity to the Repository layer, which commands Hibernate to build the SQL INSERT statement and run it.
    Vote saved = voteRepository.save(vote);
    
    // 8. Safely converts the newly saved Database Object back into a simple DTO to throw back down the network as JSON.
    return converter.toVoteDto(saved);
}
```
