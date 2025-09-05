This is a minimal fullstack e-commerce starter.

Backend:
  - Location: backend/
  - Run with: mvn spring-boot:run (requires Java 17 and MySQL running on localhost:3306 with user=root password=root)
  - Seeds sample products via src/main/resources/data.sql

Frontend:
  - Location: frontend/
  - Run with: npm install && npm start

Notes:
  - JWT secret is in application.properties for dev. Change for production.
  - This is a minimal starter to get you going. Extend features (images, cart UI, validations) as needed.
