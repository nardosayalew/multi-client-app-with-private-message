# Advanced Java RMI Calculator

## Overview
This is a GUI-based calculator application implemented in Java using RMI (Remote Method Invocation).  
The project allows a client to perform basic arithmetic operations by communicating with a remote server.

---

## Features
- Addition, Subtraction, Multiplication, Division
- Remote communication using Java RMI
- Simple GUI built with Swing
- Error handling for division by zero
- Fully modular with server, client, and interface separated

---

## Folder Structure
Calculator/
├── src/
│ └── calculator/
│ ├── CalculatorInterface.java
│ ├── CalculatorImpl.java
│ ├── CalculatorServer.java
│ └── CalculatorClient.java
├── bin/ # compiled .class files
└── README.md
---

## How to Run

### 1. Compile the code

From project root:

```bash
javac -d bin src/calculator/*.java

2. Start the RMI Server
java -cp bin calculator.CalculatorServer


You should see:
Calculator RMI Server is running...

3. Start the Client (GUI)

Open another terminal:

   java -cp bin calculator.CalculatorClient

Enter numbers in the text fields
Click +, -, *, or / to perform operations
The result is displayed in the GUI

Technologies Used

Java SE 25 (OpenJDK)
Java RMI
Swing (GUI)
Git & GitHub for version control

Author
Student Nardos Ayalew
ID UGR/35087/16

GitHub: https://github.com/nardosayalew/Calculator

Notes

Make sure the RMI server is running before starting the client.

The client and server are currently configured to run on localhost. for division b cannot be 0.


