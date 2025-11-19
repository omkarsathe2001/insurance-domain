**Insurance Domain – Spring Boot Backend**

I’ll now prepare the full, enterprise-level, GitHub-ready README.md with Swagger screenshots.
It will include:
   1. Overview
   2. Tech stack
   3. Architecture
   4. ERD
   5. Request lifecycle
   6. JWT flow
   7. Role-based authorization
   8. Validation
   9. Custom exceptions
   10. ApiResponse wrapper
   11. API endpoints
   12. Swagger screenshots (placeholder paths like /images/swagger-customers.png)
   13. Step-by-step setup & run
   14. Postman testing
   15. Deployment guide (Railway, Render)


**Insurance Domain – Spring Boot Backend**
A fully featured, production-grade Spring Boot backend for managing an Insurance Domain with modules:
- Customer Management
- Policy Management
- Policy Purchase
- Claim Management
- Payment Management
- JWT Authentication
- Role-Based Authorization (Admin & Customer)
- Validation + Custom Exceptions
- Consistent API Response Format

**📌 Features**
   1. **✅ Customer**
       - Register
       - Login(JWT)
       - View profile
       - View purchases
       - View claims
   
   2.  **✅ Admin**
       - Manage policies (CRUD)
       - View all customers
       - View all claims
       - View all payments
       - Approve/Reject claims
   
   3.  **✅ Policy**
       - Create, update, delete (Admin only)
       - Fetch all & by ID
   
   4.  **✅ Policy Purchase**
       - Customers can purchase policies
       - Admin can view all
   
   5.  **✅ Claim**
       - Raise claim
       - Update status (Admin)
   
   6.  **✅ Payment**
       - Make payment
       - View payments by purchase
   
   7.  **🔒 Security**
       - JWT Authentication
       - Role-based access (@PreAuthorize)
       - Password encryption
   
   8.  **🛠 Enterprise Practices**
       - DTO Layer
       - Service Layer
       - Repository Layer
       - Global Exception Handling
       - Validation
       - ApiResponse wrapper
       - Swagger UI documentation


**🏛 Architecture**
Controller → Service → Repository → Database
         ↓
      DTO + Entity
         ↓
    Exceptions + Validation
         ↓
        Security (JWT)


**🗂 Project Structure**
src/main/java/com/insurance
│
├── controller
├── dto
├── entity
├── exception
├── repository
├── response
├── security
├── service
│   └── impl
└── InsuranceBackendApplication.java



**🔐 JWT Authentication Flow**
 - Client → /auth/login → validate credentials → generate JWT
 - Client stores JWT → sends Authorization: Bearer <token>
 - JWT filter validates token → sets authentication → allow request  

**Role-Based Authorization (RBAC)**
| Role              | Permissions                                                       |
| ----------------- | ----------------------------------------------------------------- |
| **ROLE_ADMIN**    | Create/update/delete policies, view all purchases/claims/payments |
| **ROLE_CUSTOMER** | View own profile, purchases, claims, raise claim, make payment    |

**Implemented with:**
  - @PreAuthorize("hasRole('ADMIN')")
and
   - @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")


**🛢 Database Schema (ERD)**
  - **Entities:**
      - Customer
      - Policy
      - Policy
      - Purchase
      - Claim
      - Payment


<img width="1536" height="1024" alt="erd-diagram" src="https://github.com/user-attachments/assets/991a1228-b429-4116-bb9d-3d5558c19b67" />
