# ShopDue architecture

ShopDue is a small-shop credit ledger.

## Stack
- Frontend: Next.js + TypeScript, deployable on Vercel.
- Backend: Java 21 + Spring Boot 3 + Spring Data JPA.
- Database: PostgreSQL.
- API documentation: Springdoc / Swagger UI.

## Main API
- GET/POST /api/customers
- GET /api/customers/{id}
- GET /api/customers/{id}/transactions
- POST /api/transactions
- DELETE /api/transactions/{id}
- GET /api/dashboard

## Data model
Customer 1 -> many Transaction records.
A CREDIT increases what the customer owes. A PAYMENT records money received. Dashboard outstanding is total credit minus total payments, never below zero.

## Deployment
1. Create a free PostgreSQL database such as Neon.
2. Deploy backend as a Docker web service on a platform supporting Spring Boot containers. Set DATABASE_URL as a JDBC PostgreSQL URL plus DB_USERNAME and DB_PASSWORD.
3. Deploy frontend to Vercel with NEXT_PUBLIC_API_URL pointing to the backend URL.
4. For production, replace the current permissive CORS/security setup with authenticated shop-owner accounts and a restricted frontend origin before exposing customer data publicly.

## Important
This repository is an MVP foundation. Authentication, production-grade authorization, automated WhatsApp sending, payment gateways, and audit logging should be added before real customer data is used.
