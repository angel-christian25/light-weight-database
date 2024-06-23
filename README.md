# Lightweight Database

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Usage](#usage)

## Overview

This project is a prototype of a lightweight Database Management System (DBMS) developed using Java. It provides basic functionalities similar to MySQL, supporting a single database, multiple users, and two-factor authentication for secure access. The application is console-based and supports a variety of SQL queries for database management.

## Features

- **User Authentication:** 
  - Two-factor authentication using ID, Password, and Captcha.
  - Support for multiple users.
  - Password hashing using the MD5 algorithm from the Java standard library.

- **SQL Query Support:**
  - Data Definition Language (DDL) commands: `CREATE`.
  - Data Manipulation Language (DML) commands: `SELECT`, `INSERT`, `UPDATE`, `DELETE`.
  - Single transaction handling using `BEGIN TRANSACTION` and `END TRANSACTION`.

- **Data Storage:**
  - Data, user information, and logs are stored in CSV file format.

## Usage

1. **Login:**
   - On starting the application, you will be prompted to enter your User ID, Password, and solve a simple Captcha for authentication.
   
2. **SQL Queries:**
   - After successful login, you can enter SQL queries in the console.
   - Supported queries include:
     - `CREATE TABLE table_name (column1 datatype, column2 datatype, ...)`
     - `INSERT INTO table_name (column1, column2, ...) VALUES (value1, value2, ...)`
     - `SELECT column1, column2, ... FROM table_name WHERE condition`
     - `UPDATE table_name SET column1 = value1, column2 = value2 WHERE condition`
     - `DELETE FROM table_name WHERE condition`

3. **Transactions:**
   - To start a transaction, use `BEGIN TRANSACTION`.
   - To end and commit the transaction, use `END TRANSACTION`.
# light-weight-database
