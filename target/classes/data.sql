INSERT INTO customers (firstName, lastName, email, phone, address) VALUES
('John', 'Doe', 'john.doe@example.com', '1234567890', '123 Main St'),
('Jane', 'Smith', 'jane.smith@example.com', '0987654321', '456 Elm St');

INSERT INTO vehicles (vin, make, model, year, color, owner_id) VALUES
('1HGCM82633A123456', 'Honda', 'Accord', 2020, 'Blue', 1),
('2T1BURHE0JC123456', 'Toyota', 'Corolla', 2019, 'Red', 2);

INSERT INTO registrations (registrationNumber, issueDate, expiryDate, vehicle_vin) VALUES
('REG-123456789', '2025-01-01', '2026-01-01', '1HGCM82633A123456'),
('REG-987654321', '2025-02-01', '2026-02-01', '2T1BURHE0JC123456');