Fullstack E-commerce (complete starter)
======================================

This package contains a runnable starter with:
- backend/ : Spring Boot (Java 17, Maven) with JWT auth, product search, orders (dummy payment).
- frontend/: React (create-react-app) simple UI with login/register, product listing, cart, checkout.
- docker-compose.yml to run MySQL, backend, frontend.

Quick start (Docker, recommended):
1. Install Docker & Docker Compose.
2. From the project root run:
   docker compose up --build
3. Frontend will be at http://localhost:3000 and backend at http://localhost:8080

Quick start (local dev):
Backend:
  - Requires Java 17 and MySQL running (create DB 'ecommerce' or let docker create it).
  - cd backend
  - mvn spring-boot:run

Frontend:
  - cd frontend
  - npm install
  - npm start

Notes:
- JWT secret is set in docker-compose environment. Change it before production.
- This is a starter; add better validation, error handling, logging, and tests before production.
