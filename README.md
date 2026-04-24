# Chat Application (Java)

A multi-user real-time chat application built using Java Sockets and Swing. The application demonstrates client-server communication, multithreading, and a custom graphical user interface with avatar-based messaging.

---

## Features

* Real-time messaging using a client-server architecture
* Multithreaded communication for simultaneous send/receive
* Avatar selection from predefined assets
* Left and right aligned chat messages
* Custom UI built using Java Swing
* Support for custom fonts
* Image icons embedded in chat messages
* Auto-scrolling chat window

---

## Tech Stack

* Language: Java
* Networking: Java Sockets (TCP)
* UI: Java Swing

### Concepts Used

* Multithreading
* Object Serialization
* Event-driven programming
* Client-server architecture

---

## Project Structure

```
ChatApp/
├── src/
│   ├── client/
│   │   ├── ChatClient.java
│   │   ├── ChatUI.java
│   │   └── Main.java
│   │
│   ├── server/
│   │   ├── ChatServer.java
│   │   └── ClientHandler.java
│   │
│   ├── model/
│   │   └── Message.java
│   │
│   └── assets/
│       ├── avo.png
│       ├── cheesy.png
│       ├── choco.png
│       ├── drago.png
│       ├── orange.png
│       └── handwriting.ttf
```

---

## How to Run

### 1. Start the Server

Run:

```
ChatServer.java
```

### 2. Start Clients

Run:

```
Main.java
```

Run multiple instances to simulate multiple users.

---

## How It Works

1. The user enters a username
2. The user selects an avatar from predefined options
3. The client connects to the server using sockets
4. Messages are sent as serialized `Message` objects
5. The server broadcasts messages to all connected clients
6. The UI renders messages with avatar, timestamp, and alignment

---

## What This Project Demonstrates

* Implementation of real-time communication using sockets
* Handling concurrency with threads
* Designing and managing a GUI application in Java
* Structuring a modular client-server system
* Debugging synchronization and UI rendering issues

---

## Possible Enhancements

* Private messaging
* Online user list
* Typing indicator
* Message delivery status
* Web-based interface

---

## Author

Khushu
