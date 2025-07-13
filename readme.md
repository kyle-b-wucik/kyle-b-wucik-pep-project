# Social Media Blog API

A RESTful backend API for a social media application built with Java, featuring user authentication and message management with full CRUD operations using a custom 3-layer architecture.

## Technologies Used
- **Java** - Core programming language
- **Javalin** - Lightweight web framework for REST API
- **JDBC** - Database connectivity and SQL operations
- **SQLite/H2** - Embedded database for data persistence
- **JSON** - Data serialization with Jackson/Gson
- **Maven** - Dependency management and build tool

## Features
- 🔐 User registration and authentication system
- ✍️ Create, read, update, and delete messages
- 🔍 Retrieve messages by user or message ID
- ✅ Input validation and comprehensive error handling
- 🌐 RESTful API design with proper HTTP status codes
- 🗄️ Custom DAO layer with raw SQL queries
- 🏗️ 3-layer architecture (Controller, Service, DAO)

## API Endpoints

### Authentication
- `POST /register` - Register a new user account
- `POST /login` - Authenticate user credentials

### Messages
- `POST /messages` - Create a new message
- `GET /messages` - Retrieve all messages
- `GET /messages/{message_id}` - Get a specific message by ID
- `PATCH /messages/{message_id}` - Update message text
- `DELETE /messages/{message_id}` - Delete a message
- `GET /accounts/{account_id}/messages` - Get all messages by a specific user

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6+

### Installation & Running
1. Clone the repository
   ```bash
   git clone [your-repo-url]
   cd social-media-blog-api
   ```

2. Compile and run the application
   ```bash
   mvn compile exec:java
   ```

3. The API will be available at `http://localhost:8080`

## Project Structure

```
src/main/java/
├── Controller/         # REST endpoint handlers
├── Service/           # Business logic layer
├── DAO/              # Data access objects
├── Model/            # Entity classes (Account, Message)
└── Main.java         # Application entry point
```

## Database Schema

### Account Table
- `account_id` - Primary key (auto-generated)
- `username` - Unique username (required)
- `password` - User password (minimum 4 characters)

### Message Table
- `message_id` - Primary key (auto-generated)
- `posted_by` - Foreign key to Account
- `message_text` - Message content (max 255 characters)
- `time_posted_epoch` - Timestamp when message was created

## Architecture Highlights

### 3-Layer Architecture
- **Controller Layer**: Handles HTTP requests/responses and routing
- **Service Layer**: Contains business logic and validation rules
- **DAO Layer**: Manages database operations with raw SQL queries

### Custom Implementation
- Hand-written SQL queries for all database operations
- Custom connection management and transaction handling
- Manual JSON serialization/deserialization
- Built without heavy frameworks to demonstrate core Java skills

## Validation Rules

### User Registration
- Username must not be blank and must be unique
- Password must be at least 4 characters long
- Returns 400 for any validation errors

### Message Creation/Updates
- Message text must not be blank
- Message text cannot exceed 255 characters
- User must exist in the database
- Returns 400 for validation failures

### Error Handling
- 200: Success responses
- 400: Client errors (validation failures)
- 401: Unauthorized (login failures)

## Key Learning Outcomes

This project demonstrates proficiency in:
- **Core Java Development** - Object-oriented programming and design patterns
- **RESTful API Design** - Proper HTTP methods, status codes, and resource naming
- **Database Programming** - Raw SQL queries, JDBC, and connection management
- **Architecture Design** - 3-layer architecture and separation of concerns
- **Manual Framework Implementation** - Building web services without Spring Boot
- **JSON Processing** - Manual serialization and API data handling

## Sample API Usage

### Register a New User
```bash
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john_doe","password":"password123"}'
```

### Create a Message
```bash
curl -X POST http://localhost:8080/messages \
  -H "Content-Type: application/json" \
  -d '{"posted_by":1,"message_text":"Hello, World!","time_posted_epoch":1669947792}'
```

### Get All Messages
```bash
curl -X GET http://localhost:8080/messages
```

## Technical Challenges Solved
- Custom routing and HTTP request handling with Javalin
- Manual database connection pooling and transaction management
- Input validation without annotation-based frameworks
- JSON parsing and serialization without Spring Boot auto-configuration
- Error handling and appropriate HTTP status code mapping

---

*Built to demonstrate fundamental Java web development skills and understanding of web service architecture without relying on heavy frameworks like Spring Boot.*
