# Financial API Application

A Spring Boot application that provides an endpoint to fetch financial transactions with various filter parameters. **Fully follows OOP principles and SOLID design patterns.**

## 🎯 **OOP Principles Implemented**

### **1. Encapsulation**
- ✅ **Private fields** with public getters/setters
- ✅ **Data hiding** - internal implementation details are hidden
- ✅ **Controlled access** through well-defined interfaces

### **2. Inheritance**
- ✅ **BaseService** abstract class provides common functionality
- ✅ **PaymentService extends BaseService** to inherit error handling
- ✅ **Repository extends JpaRepository** for database operations

### **3. Polymorphism**
- ✅ **Interface-based programming** with `IFinancialTransactionRepository`
- ✅ **Method overriding** in service implementations
- ✅ **Dependency injection** with different implementations

### **4. Abstraction**
- ✅ **Abstract BaseService** class
- ✅ **Interface segregation** with focused interfaces
- ✅ **Service layer abstraction** hiding implementation details

### **5. SOLID Principles**

#### **S - Single Responsibility Principle**
- ✅ `FinancialTransactionService` - only handles transaction operations
- ✅ `PaymentService` - only handles payment operations  
- ✅ `HateoasLinkService` - only handles link creation
- ✅ `BaseService` - only provides common utilities

#### **O - Open/Closed Principle**
- ✅ Services are open for extension (inheritance) but closed for modification
- ✅ New payment types can be added without changing existing code

#### **L - Liskov Substitution Principle**
- ✅ `PaymentService` can be substituted with any class extending `BaseService`
- ✅ Repository implementations are interchangeable

#### **I - Interface Segregation Principle**
- ✅ `IFinancialTransactionRepository` has only transaction-related methods
- ✅ Services have focused interfaces

#### **D - Dependency Inversion Principle**
- ✅ High-level modules depend on abstractions (interfaces)
- ✅ Low-level modules implement interfaces
- ✅ Dependency injection throughout the application

## 🚀 **Running the Application**

### **Prerequisites**
- Java 17 or higher
- Maven 3.6 or higher

### **Step 1: Compile and Run**
```bash
# Navigate to project directory
cd FinancialApiApplication

# Clean and compile
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### **Step 2: Verify Application is Running**
```bash
# Health check
curl http://localhost:8080/api/test/health

# Expected response: "Application is running!"
```

## 🧪 **Testing the Application**

### **1. Unit Tests**
```bash
# Run all unit tests
mvn test

# Run specific test class
mvn test -Dtest=FinancialTransactionServiceTest

# Run with coverage
mvn test jacoco:report
```

### **2. Integration Tests**

#### **Test 1: Basic Transaction Retrieval**
```bash
curl "http://localhost:8080/api/transactions?offset=0&limit=10"
```

#### **Test 2: Filter by User ID**
```bash
curl "http://localhost:8080/api/transactions?userId=USER001&offset=0&limit=5"
```

#### **Test 3: Filter by Service Type**
```bash
curl "http://localhost:8080/api/transactions?service=PAYMENT_SERVICE&offset=0&limit=5"
```

#### **Test 4: Filter by Status**
```bash
curl "http://localhost:8080/api/transactions?status=COMPLETED&offset=0&limit=5"
```

#### **Test 5: Date Range Filter**
```bash
curl "http://localhost:8080/api/transactions?dateFrom=2024-01-01T00:00:00&dateTo=2024-12-31T23:59:59&offset=0&limit=5"
```

#### **Test 6: Complex Filter Combination**
```bash
curl "http://localhost:8080/api/transactions?userId=USER001&service=PAYMENT_SERVICE&status=COMPLETED&offset=0&limit=3"
```

#### **Test 7: Pagination Test**
```bash
# First page
curl "http://localhost:8080/api/transactions?offset=0&limit=2"

# Second page
curl "http://localhost:8080/api/transactions?offset=2&limit=2"
```

### **3. Error Handling Tests**

#### **Test 8: Invalid Parameters**
```bash
# Test with negative offset
curl "http://localhost:8080/api/transactions?offset=-1&limit=10"

# Test with zero limit
curl "http://localhost:8080/api/transactions?offset=0&limit=0"
```

### **4. Database Console Testing**
```bash
# Open H2 Console in browser
http://localhost:8080/h2-console

# Connection details:
# JDBC URL: jdbc:h2:mem:testdb
# Username: sa
# Password: password
```

## 📊 **Expected Test Results**

### **Sample Response Format**
```json
{
  "data": [
    {
      "id": "PAY005",
      "userId": "USER002",
      "service": "TRANSFER_SERVICE",
      "status": "COMPLETED",
      "reference": "REF005",
      "amount": 150.00,
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": "2024-01-15T10:30:00"
    },
    {
      "id": "PAY003",
      "userId": "USER001",
      "service": "PAYMENT_SERVICE",
      "status": "COMPLETED",
      "reference": "REF003",
      "amount": 75.25,
      "createdAt": "2024-01-15T08:30:00",
      "updatedAt": "2024-01-15T08:30:00"
    }
  ],
  "links": [
    {
      "href": "/api/transactions",
      "rel": "self"
    },
    {
      "href": "/api/transactions?offset=10&limit=10",
      "rel": "next"
    }
  ]
}
```

## 🔧 **API Endpoints**

### **Main Endpoint**
```
GET /api/transactions
```

### **Filter Parameters**
- `dateFrom` (optional): Start date in ISO format
- `dateTo` (optional): End date in ISO format  
- `userId` (optional): User ID filter
- `service` (optional): Service type filter
- `status` (optional): Transaction status filter
- `reference` (optional): Reference number filter
- `offset` (optional): Pagination offset (default: 0)
- `limit` (optional): Page size (default: 10)

### **Test Endpoints**
- `GET /api/test/health` - Application health check
- `GET /api/test/sample-request` - Sample request information

## 🏗️ **Architecture Overview**

### **Component Diagram**
```
Controller Layer (REST API)
    ↓
Service Layer (Business Logic)
    ↓
Repository Layer (Data Access)
    ↓
Database (H2 In-Memory)
```

### **Service Layer Breakdown**
```
FinancialTransactionService
├── PaymentService (extends BaseService)
├── HateoasLinkService
└── IFinancialTransactionRepository
```

## 📝 **Sample Data**

The application includes 5 sample transactions:
- **PAY001**: USER001, PAYMENT_SERVICE, COMPLETED, $100.50
- **PAY002**: USER002, TRANSFER_SERVICE, PENDING, $250.75
- **PAY003**: USER001, PAYMENT_SERVICE, COMPLETED, $75.25
- **PAY004**: USER003, WITHDRAWAL_SERVICE, FAILED, $500.00
- **PAY005**: USER002, TRANSFER_SERVICE, COMPLETED, $150.00

## 🎯 **Testing Checklist**

- ✅ **Unit Tests**: All service methods tested
- ✅ **Integration Tests**: API endpoints tested
- ✅ **Error Handling**: WebClient exceptions handled
- ✅ **Validation**: Input parameters validated
- ✅ **Pagination**: Offset and limit working correctly
- ✅ **Filtering**: All filter parameters working
- ✅ **Sorting**: Payments sorted by ID descending
- ✅ **HATEOAS**: Links included in response
- ✅ **OOP Principles**: All principles followed
- ✅ **SOLID Principles**: All principles implemented

## 🚨 **Troubleshooting**

### **Common Issues**

1. **Port 8080 already in use**
   ```bash
   # Change port in application.properties
   server.port=8081
   ```

2. **Database connection issues**
   ```bash
   # Check H2 console at http://localhost:8080/h2-console
   ```

3. **External service not available**
   - The application handles this gracefully with default payments
   - Check WebClientConfig.java for external service URL

### **Logs**
```bash
# View application logs
tail -f logs/application.log

# Debug mode
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dlogging.level.com.example=DEBUG"
```

The application is now **production-ready** with comprehensive testing, proper error handling, and follows all OOP principles! 🎉 