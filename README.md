# GMDb (Global Movie Database) - Java Console Application

## Introduction

GMDb is a Java console application that simulates an online movie database system. It allows users to view available movies, purchase them, submit reviews, and allows administrative tasks such as adding, updating, and deleting movies.

This project uses Java with Hibernate as the Object Relational Mapping (ORM) tool to manage interactions with a MySQL database.

## Functionalities

### 1. CRUD Operations

- **Movies**: Admins can create, read, update, and delete movies. They can also view all available movies.
- **Users**: Admins can manage users and see which movies a user has purchased.
- **Purchases**: Users can purchase movies.
- **Reviews**: Users can leave reviews and ratings for movies they have purchased.

### 2. Adding a New Movie

Admin users can insert new movies into the database by providing details such as the movie name, director, actors, runtime, and genres.

### 3. User Registration

New users can register with the system, providing their username, email, and password.

### 4. Purchasing a Movie

Users can purchase movies from the available list of movies.

### 5. Submitting a Review

Users who have purchased a movie can submit reviews including a rating (out of 10) and a review description.

### 6. Calculating Average Movie Rating

The system calculates the average rating of a movie based on user reviews.

## Non-Functional Requirements

### 1. Data Persistence

Data persistence is ensured using Hibernate ORM, which maps Java objects to relational database tables and handles CRUD operations.

### 2. Console Application

The application is implemented as a Java console application, facilitating user interactions through the console interface.

## Setup Instructions

### Prerequisites

1. NetBeans IDE 8.2
2. MySQL database (version 8.2.0 used for development)
3. Hibernate ORM

### Database Setup

1. Create a MySQL database named `GMDb`.
2. Execute the following SQL script to create the necessary tables:

```sql
CREATE DATABASE GMDb;

USE GMDb;

CREATE TABLE Genre (
    genre_id INT AUTO_INCREMENT PRIMARY KEY,
    genre_name VARCHAR(50) NOT NULL
);

CREATE TABLE Movie (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_name VARCHAR(255) NOT NULL,
    director VARCHAR(255),
    actors TEXT,
    runtime INT
);

CREATE TABLE Movie_Genre (
    movie_id INT,
    genre_id INT,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY (movie_id) REFERENCES Movie(movie_id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES Genre(genre_id) ON DELETE CASCADE
);

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(191) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Purchase (
    purchase_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    movie_id INT,
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES Movie(movie_id) ON DELETE CASCADE
);

CREATE TABLE Review (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    movie_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 10),
    review_description TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES Movie(movie_id) ON DELETE CASCADE
);

CREATE TABLE Admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(191) NOT NULL UNIQUE, 
    password VARCHAR(255) NOT NULL
); 
