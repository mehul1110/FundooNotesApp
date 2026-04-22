# Fundoo Notes App 📝

A feature-rich, high-performance Spring Boot application for managing personal notes, organized with a clean N-Tier architecture and advanced observability.

## 🚀 Overview

Fundoo Notes is designed to provide a robust backend for note-taking, implementing essential features like secure user authentication, note lifecycle management (Pin, Archive, Trash), and automated system monitoring.

## 🛤️ Architecture & Strategy

- **N-Tier Design:** Clear separation of concerns across Controller, Service, and Repository layers.
- **Branching Strategy:** Systematic development using a `develop` branch for integration and dedicated feature branches for each Use Case.
- **Clean History:** Maintains a readable Git history with meaningful commit messages and structured merges.

## 🛠️ Key Technologies

- **Spring Boot 3.x:** Modern microservice-ready framework.
- **Java 17:** Long-term support version with modern features.
- **Spring Security & JWT:** Stateless authentication for secure API access.
- **Spring Data JPA:** Efficient database persistence with Hibernate.
- **AOP (Aspect-Oriented Programming):** Decoupled logging and performance profiling for enterprise-grade observability.

## ✨ Core Features

- **User Authentication:** Registration and login with JWT-based authorization.
- **Note Management:** Full CRUD operations with support for pinning, archiving, and trashing notes.
- **Observability:** Automatic logging of service execution and performance metrics via AOP.
- **Exception Handling:** Centralized global exception management for consistent API responses.

---
*Maintained with a focus on Clean Code and Enterprise Standards.*
