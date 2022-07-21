INSERT INTO user VALUES (1, "Popescu George", "parola1");
INSERT INTO user VALUES (2, "Ionescu Costel", "parola2");
INSERT INTO user VALUES (3, "Vasilescu Gigel", "parola3");

-- 0 -> RON, 1 -> EUR, 2 -> USD
INSERT INTO account VALUES (1, 4800.00, 0, 1);
INSERT INTO account VALUES (2, 1200.00, 1, 1);
INSERT INTO account VALUES (3, 15000.00, 0, 2);
INSERT INTO account VALUES (4, 1000.00, 1, 2);
INSERT INTO account VALUES (5, 700.00, 2, 2);
INSERT INTO account VALUES (6, 8950.00, 0, 3);
INSERT INTO account VALUES (7, 650.00, 2, 3);
INSERT INTO account VALUES (8, 1450.00, 2, 3);

INSERT INTO transaction VALUES (1, 50.0, '2022-05-10', 1, 1, 3);
INSERT INTO transaction VALUES (2, 75.0, '2022-05-09', 0, 2, 1);
INSERT INTO transaction VALUES (3, 150.0, '2022-05-09', 1, 3, 4);
INSERT INTO transaction VALUES (4, 100.0, '2022-05-10', 0, 7, 8);
INSERT INTO transaction VALUES (5, 70.0, '2022-05-08', 2, 2, 5);
INSERT INTO transaction VALUES (6, 20.0, '2022-05-09', 1, 5, 3);
INSERT INTO transaction VALUES (7, 30.0, '2022-05-10', 2, 6, 8);
