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


**📸 Swagger Screenshots**
<img width="1901" height="874" alt="swagger-home" src="https://github.com/user-attachments/assets/bd286341-9a53-4366-af4f-90757ff2729a" />

<img width="1827" height="539" alt="swagger-customers" src="https://github.com/user-attachments/assets/c797d565-5eb3-4466-88fb-67f2585536d6" />

<img width="1853" height="427" alt="swagger-policies" src="https://github.com/user-attachments/assets/3cfa08c3-011e-4a74-9fcb-b73e9c918d82" />

<img width="1838" height="276" alt="swagger-purchase" src="https://github.com/user-attachments/assets/5dc7fa71-7239-4d63-a6dc-7323e3e3a112" />

<img width="1884" height="360" alt="swagger-claims" src="https://github.com/user-attachments/assets/ceaf58bc-efcb-4ec2-ab82-c648f9e1854f" />

<img width="1841" height="293" alt="swagger-payments" src="https://github.com/user-attachments/assets/9d43d47c-90ce-4113-8e61-3bb0d2f7596c" />

<img width="1816" height="152" alt="swagger-auth" src="https://github.com/user-attachments/assets/af9b5764-ae7f-42b3-aa99-3f2a72f46db9" />


**🔁 ApiResponse Format**
  **- Every API returns:**
       {
           "success": true,
           "message": "Policy created successfully",
           "data": {},
           "timestamp": "2025-11-18T12:00:00"
        }
On error:
        {
           "success": false,
           "message": "Validation failed",
           "errors": {
                    "email": "Invalid email format"
                     },
           "timestamp": "2025-11-18T12:00:00"
         }


**⚠ Custom Exceptions**
 - NotFoundException
 - BadRequestException
 - UnauthorizedException
 - Handled by GlobalExceptionHandler.

**🧪 API Endpoints Overview**
**🔐 Auth**
- POST /api/auth/login
- POST /api/customers/register

**👤 Customer**
- GET /api/customers
- GET /api/customers/{id}
- GET /api/customers/email/{email}
- DELETE /api/customers/{id}

**🛡 Policy**
- POST /api/policies       (Admin)
- PUT  /api/policies/{id}  (Admin)
- DELETE /api/policies/{id} (Admin)
- GET /api/policies
- GET /api/policies/{id}

**📝 Policy Purchase**
- POST /api/purchases       (Customer)
- GET  /api/purchases/{id}
- GET  /api/purchases/customer/{customerId}

**📄 Claim**
- POST /api/claims
- PUT  /api/claims/{id}/status   (Admin)
- GET  /api/claims/{id}
- GET  /api/claims/purchase/{purchaseId}

**💳 Payment**
- POST /api/payments
- GET  /api/payments/{id}
- GET  /api/payments/purchase/{purchaseId}

**🧪 Postman Testing Flow**
  **1️⃣ Register Customer**
  - POST → /api/customers/register

  **2️⃣ Login**
  - POST → /api/auth/login
  - Copy JWT token

  **3️⃣ Add JWT in Postman**
  - Headers → Authorization → Bearer <token>

  **4️⃣ Test protected endpoints**
  - GET /api/policies


**🚀 Run the Application**
  **1. Configure database in application.properties**
  - spring.datasource.url=jdbc:mysql://localhost:3306/insurance_db
  - spring.datasource.username=root
  - spring.datasource.password=1234

  **2. Install dependencies**
  - mvn clean install

  **3. Run**
  - mvn spring-boot:run

**🌐 Swagger UI**
- http://localhost:8080/swagger-ui/index.html

**☁ Deployment Guide**
  **Option 1: Railway.app**
  ✔ Free
  ✔ Simple
  ✔ Auto-deployment from GitHub

  **Option 2: Render.com**
  ✔ Easy environment setup
  ✔ Supports Java builds

**Guide includes:**
- Create new service
- Connect GitHub
- Add environment variables
- Run mvn package
- Deploy JAR

