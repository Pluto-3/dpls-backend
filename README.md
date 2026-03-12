# DPLS — Digital Permit & License System (Backend)

REST API for digitizing government permit and license workflows. Built with Spring Boot 3 and PostgreSQL.

**Live API:** https://dpls-backend.onrender.com

---

## Tech Stack

- Java 17, Spring Boot 3.2.5
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA + Hibernate
- PostgreSQL (Supabase)
- Docker (deployed on Render)

---

## Running Locally

**Prerequisites:** Java 17, Maven, PostgreSQL

1. Clone the repo
   ```bash
   git clone https://github.com/Pluto-3/dpls
   cd dpls
   ```

2. Create a local PostgreSQL database
   ```sql
   CREATE DATABASE dpls_db;
   ```

3. Copy the example config and fill in your values
   ```bash
   cp src/main/resources/application-example.yml src/main/resources/application.yml
   ```

   ```yaml
   spring.datasource.password: your_postgres_password
   jwt.secret: your_secret_key_at_least_32_chars
   ```

4. Run the app
   ```bash
   ./mvnw spring-boot:run
   ```

API will be available at `http://localhost:8080`

---

## API Endpoints

| Method | Endpoint | Access |
|--------|----------|--------|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| GET | `/api/permits/verify/{code}` | Public |
| GET | `/api/applications/files/{filename}` | Public |
| POST | `/api/applications` | APPLICANT |
| POST | `/api/applications/{id}/submit` | APPLICANT |
| GET | `/api/applications/my` | APPLICANT |
| GET | `/api/applications/{id}` | APPLICANT |
| POST | `/api/applications/{id}/documents` | APPLICANT |
| GET | `/api/applications/submitted` | OFFICER |
| POST | `/api/applications/{id}/approve` | OFFICER |
| POST | `/api/applications/{id}/reject` | OFFICER |
| POST | `/api/applications/{id}/request-correction` | OFFICER |
| POST | `/api/permits/{applicationId}/issue` | OFFICER |
| POST | `/api/departments` | ADMIN |
| POST | `/api/permit-types` | ADMIN |
| GET | `/api/admin/stats` | ADMIN |

Roles: `APPLICANT`, `OFFICER`, `ADMIN`

---

## Deployment

Deployed via Docker on Render, database hosted on Supabase.

**Required environment variables:**

| Variable | Description |
|----------|-------------|
| `DB_URL` | JDBC connection string — `jdbc:postgresql://...` |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key for signing JWT tokens |
| `FRONTEND_URL` | Frontend origin for CORS (e.g. `https://dpls-frontend.onrender.com`) |
| `PORT` | Port to run on (Render sets this automatically) |

To deploy: push to `main` — Render builds the Docker image automatically.
