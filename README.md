# 🛒 E-Commerce Product Management API

A RESTful backend API built using Java and Spring Boot for managing products and categories in an e-commerce system.

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Postman (for API testing)

---

## 📦 Features

### ✅ Product Module
- Create, Update, Delete Product
- Get Product by ID
- Get All Products (with Pagination, Sorting)
- Filter by Category
- Filter by Price Range

### ✅ Category Module
- Create, Update, Delete Category
- Get All Categories
- Integrated with Product

### ✅ Other
- Consistent API Responses using `ApiResponse<T>`
- DTO Layer: `ProductDto`, `CategoryDto`, `ProductResponse`
- Clean architecture (Controller → Service → Repository)

---

## 📂 API Endpoints

### Product APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/products` | Create product |
| PUT    | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| GET    | `/api/products/{id}` | Get product by ID |
| GET    | `/api/products` | Get all products (pagination + sorting) |
| GET    | `/api/products/category/{categoryId}` | Get products by category |
| GET    | `/api/products/price-range?min=5000&max=10000` | Filter by price |

### Category APIs
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/categories` | Create category |
| PUT    | `/api/categories/{id}` | Update category |
| DELETE | `/api/categories/{id}` | Delete category |
| GET    | `/api/categories` | Get all categories |
| GET    | `/api/categories/{id}` | Get category by ID |

---


