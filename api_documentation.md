# Complete Voting System API Reference Documentation

*(Note: Since I am an AI natively operating within your environment, I generated this standardized Markdown document which acts exactly like a printable PDF cheat sheet. You can easily click **File > Print > Save as PDF** natively within your VS Code interface, or use any standard browser!)*

> [!IMPORTANT]
> **Authorization Requirements**:
> - **`/api/auth/**`**: `permitAll` (No Token required).
> - **`/api/elections/**` & `/api/candidates/**`**: Strictly requires an **`ADMIN`** token inside the Postman **Authorization -> Bearer Token** tab.
> - **`/api/votes/**`**: Strictly requires a **`USER`** token inside the Postman **Authorization -> Bearer Token** tab.
> - **All other endpoints** (`/api/users/**`, `/api/results/**`): Accessible by **ANY** logged-in user with a valid token.

---

## 1. Authentication (No tokens required)

### Register User (or Admin)
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/auth/register`
- **Body**: 
```json
{
    "name": "Regular Voter",
    "voterId": "VOTER-12345",
    "email": "voter@test.com",
    "password": "Password123!",
    "constituency": "Global District",
    "role": "USER",
    "status": "ACTIVE"
}
```
*(To register an Admin, simply change `"role": "USER"` to `"role": "ADMIN"`).*

### Login
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/auth/login`
- **Body**: 
```json
{
    "identifier": "VOTER-12345",
    "password": "Password123!"
}
```
*(Outputs the `token` string used for every other API below).*

---

## 2. Elections (Role: ADMIN)

### Create Election
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/elections`
- **Body**: 
```json
{
    "name": "Global Tech Council Election",
    "details": "Annual election for tech board",
    "startDate": "2026-05-01",
    "endDate": "2026-05-30"
}
```

### Activate / Deactivate Election (Update)
- **Method**: `PUT`
- **URL**: `http://localhost:8080/api/elections/1`
- **Body**: 
```json
{
    "name": "Global Tech Council Election",
    "details": "Suspended for Review",
    "startDate": "2026-05-01",
    "endDate": "2026-05-30",
    "status": "INACTIVE"
}
```
*(Change `"status": "ACTIVE"` or `"status": "INACTIVE"` to immediately toggle the state. Voters cannot cast votes on effectively INACTIVE elections).*

### Read All Elections
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/elections`

### Read Single Election
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/elections/1`

### Delete Election
- **Method**: `DELETE`
- **URL**: `http://localhost:8080/api/elections/1`

---

## 3. Positions (Role: ADMIN)

### Create Position
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/elections/positions`
- **Body**: 
```json
{
    "name": "President",
    "constituency": "Global District",
    "electionId": 1
}
```

### Get Positions by Election
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/elections/1/positions`

### Update Position
- **Method**: `PUT`
- **URL**: `http://localhost:8080/api/elections/positions/1`
- **Body**: 
```json
{
    "name": "Vice President",
    "constituency": "Global District"
}
```

### Delete Position
- **Method**: `DELETE`
- **URL**: `http://localhost:8080/api/elections/positions/1`

---

## 4. Candidates (Role: ADMIN)

### Create Candidate
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/candidates`
- **Body**: 
```json
{
    "name": "John Doe",
    "party": "Progressive Tech Party",
    "electionId": 1,
    "positionId": 1
}
```

### Get Candidates by Election & Position
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/candidates/election/1/position/1`

### Update Candidate
- **Method**: `PUT`
- **URL**: `http://localhost:8080/api/candidates/1`
- **Body**: 
```json
{
    "name": "Jane Doe Updated",
    "party": "New Independent Party"
}
```

### Delete Candidate
- **Method**: `DELETE`
- **URL**: `http://localhost:8080/api/candidates/1`

---

## 5. Voting (Role: USER)

### Cast a Vote
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/votes`
- **Body**: 
```json
{
    "userId": 2,          
    "electionId": 1,
    "positionId": 1,
    "candidateId": 1
}
```

---

## 6. Users (Role: ANY Authenticated)

### Activate / Deactivate User (Update)
- **Method**: `PUT`
- **URL**: `http://localhost:8080/api/users/2`
- **Body**: 
```json
{
    "name": "Regular Voter",
    "email": "voter@test.com",
    "constituency": "Global District",
    "role": "USER",
    "status": "INACTIVE"
}
```

### Get All Users
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/users/getAllUsers`

### Get User by ID
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/users/2`

### Delete User
- **Method**: `DELETE`
- **URL**: `http://localhost:8080/api/users/2`

---

## 7. Results (Role: ANY Authenticated)

### Get Results by Position (Candidate Vote Counts)
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/results/election/1/position/1`

### Get Total Votes Cast in Election
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/results/election/1/total`
