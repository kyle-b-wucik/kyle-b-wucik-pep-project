# Social Media API

This project is a backend API for a social media application. It allows users to register, login, create messages, and view messages. This was originally a project to demonstrate API development skills.

## Features

*   **User Registration:** New users can create an account.
*   **User Login:** Existing users can log in.
*   **Create Messages:** Logged-in users can post new messages.
*   **View All Messages:** All messages can be retrieved.
*   **View Message by ID:** Specific messages can be retrieved by their ID.
*   **Delete Messages:** Messages can be deleted by their ID.
*   **Update Messages:** Existing messages can be updated.
*   **View User Messages:** All messages by a specific user can be retrieved.

## Database Tables 

The application uses a relational database with the following schema:

### Account
```
account_id integer primary key auto_increment,
username varchar(255) unique,
password varchar(255)
```

### Message
```
message_id integer primary key auto_increment,
posted_by integer,
message_text varchar(255),
time_posted_epoch long,
foreign key (posted_by) references Account(account_id)
```

## Technologies Used

*   Java
*   Maven
*   H2 Database Engine
*   Javalin
