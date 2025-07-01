
# Vehicle Registration System

## Overview

The Vehicle Registration System is a web-based application designed to manage the registration of vehicles. It allows users to register vehicles, view registered vehicles, and search for existing registrations. This system uses Java, Hibernate ORM, SQLite3, and a front-end built with HTML, CSS, and JavaScript.

## Technologies Used

- **Backend**: Java, Hibernate ORM, SQLite3
- **Frontend**: HTML, CSS, JavaScript
- **Development Environment**: VS Code, Maven
- **Database**: SQLite3 (via Hibernate ORM)

## Features

- **Vehicle Registration**: Register a vehicle by providing necessary details like vehicle information and customer details.
- **Search Vehicle**: Search for previously registered vehicles using various filters.
- **View Registered Vehicles**: View a list of all registered vehicles with detailed information.
- **Notifications**: Provides success/error notifications during interactions like registration and search.

## Project Structure

```
Vehicle_Registration_System_KPIT/
│
├── Vehicle_Registration_System/
│   ├── pom.xml               # Maven project configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── vehicleregistration/
│   │   │   │           ├── dao/
│   │   │   │           │   ├── CustomerDAO.java
│   │   │   │           │   ├── HibernateUtil.java
│   │   │   │           │   ├── RegistrationDAO.java
│   │   │   │           │   └── VehicleDAO.java
│   │   │   │           ├── model/
│   │   │   │           │   ├── Customer.java
│   │   │   │           │   ├── Registration.java
│   │   │   │           │   └── Vehicle.java
│   │   │   │           ├── service/
│   │   │   │           │   └── VehicleRegistrationService.java
│   │   │   │           └── servlet/
│   │   │   │               ├── LocalDateAdapter.java
│   │   │   │               └── RegistrationServlet.java
│   │   │   └── resources/
│   │   │   └── webapp/
│   │   │       ├── index.html
│   │   │       ├── script.js
│   │   │       └── styles.css
│   ├── target/             # Compiled and built project
│
├── README.md               # Project description and setup instructions
```

## Setup and Installation

1. **Clone the repository** (or download the ZIP file).
2. **Install Java** (JDK 8 or higher).
3. **Install Maven** for building the project.
4. **Install SQLite3** for database management.
5. **Run the application**:
   - Open the project in your favorite IDE (e.g., VS Code).
   - Build the project using Maven:  
     ```bash
     mvn clean install
     ```
   - Run the application via the command line or your IDE:
     ```bash
     mvn spring-boot:run
     ```

6. **Access the Web Interface**:
   - Navigate to `localhost:8080` in your web browser.
   - Use the provided interface to register and search vehicles.

## Future Improvements

- **Spare Parts Management**: Extend the system to manage spare parts and their inventory linked with registered vehicles.
- **User Authentication**: Add user authentication to ensure only authorized users can register and manage vehicles.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-name`).
5. Create a new pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
