# Cloud Cost Estimator

This project is a full-stack cloud cost estimation tool. It consists of a Spring Boot backend and a React frontend.

## Features

- Estimate cloud resource costs based on region, type, and count.
- Compare prices of each component based on selected region.
- View available regions and resource types.
- See detailed cost breakdowns and checkout prices for resources.
- Responsive and modern UI.

## Project Structure

- `demo/` - Spring Boot backend (Java)
- `CostEstimatorUI/` - React frontend (JavaScript)

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/your-username/cloud_cost_assignment.git
cd cloud_cost_assignment
```

### Backend Database Configuration

Before running the backend, update your database settings in the `application.properties` file:

1. Open `demo/src/main/resources/application.properties`.
2. Change the following properties to match your PostgreSQL setup:

   ```
   spring.datasource.url=jdbc:postgresql://<DB_HOST>:<DB_PORT>/<DB_NAME>
   spring.datasource.username=<DB_USERNAME>
   spring.datasource.password=<DB_PASSWORD>
   ```

   Replace `<DB_HOST>`, `<DB_PORT>`, `<DB_NAME>`, `<DB_USERNAME>`, and `<DB_PASSWORD>` with your actual database host, port, name, username, and password.

### Running the Backend (Spring Boot)

```sh
cd demo
./mvnw spring-boot:run
```

The backend will be available at `http://localhost:8080`.

### Running the Frontend (React)

```sh
cd ../CostEstimatorUI
npm install
npm run dev
```

The frontend will be available at `http://localhost:5173` .

