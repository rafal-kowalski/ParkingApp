INSERT INTO users (id, login, password_hash, first_name, last_name, email)
VALUES (1, 'admin', '$2a$10$a7EBdTrtdATGW2CiaTZrxO5DLpIisNxWKqssz6.dyVqXSl1/s7qua', '', '', 'admin@touk.pl'),
(2, 'parkingowner', '$2a$10$a7EBdTrtdATGW2CiaTZrxO5DLpIisNxWKqssz6.dyVqXSl1/s7qua', '', '', 'owner@touk.pl'),
(3, 'parkingoperator', '$2a$10$a7EBdTrtdATGW2CiaTZrxO5DLpIisNxWKqssz6.dyVqXSl1/s7qua', '', '', 'operator@touk.pl'),
(4, 'regulardriver', '$2a$10$a7EBdTrtdATGW2CiaTZrxO5DLpIisNxWKqssz6.dyVqXSl1/s7qua', '', '', 'driver@touk.pl'),
(5, 'vipdriver', '$2a$10$a7EBdTrtdATGW2CiaTZrxO5DLpIisNxWKqssz6.dyVqXSl1/s7qua', '', '', 'vip@touk.pl');

INSERT INTO authorities VALUES ('ROLE_ADMIN'), ('ROLE_USER'), ('ROLE_PARKING_OPERATOR'), ('ROLE_PARKING_OWNER'), ('ROLE_REGULAR_DRIVER'), ('ROLE_VIP_DRIVER');

INSERT INTO user_authority VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(2, 'ROLE_PARKING_OWNER'),
(3, 'ROLE_PARKING_OPERATOR'),
(4, 'ROLE_REGULAR_DRIVER'),
(5, 'ROLE_VIP_DRIVER');

INSERT INTO currency_conversion_rates VALUES ('PLN', 1.0);

INSERT INTO parking_records (license_plate, start_date, driver_id, end_date, pin_code_hash, status, currency, payment) VALUES
('ABC 1234', NOW(), 1, NULL, NULL, 'STARTED', 'PLN', NULL),
('BBC 1235', NOW(), 2, NULL, NULL, 'STARTED', 'PLN', NULL),
('ACC 1236', NOW(), 3, NULL, NULL, 'STARTED', 'PLN', NULL),
('CCC 1237', NOW(), 4, NULL, NULL, 'STARTED', 'PLN', NULL);
