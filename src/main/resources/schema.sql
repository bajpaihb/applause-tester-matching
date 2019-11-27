CREATE TABLE DEVICES
(
    device_id INT PRIMARY KEY,
    description VARCHAR(250)
)
AS SELECT * FROM CSVREAD('classpath:testerdata/devices.csv');

CREATE TABLE BUGS
(
    bug_id INT NOT NULL,
    device_id INT NOT NULL,
    tester_id INT NOT NULL
)
AS SELECT * FROM CSVREAD('classpath:testerdata/bugs.csv');

CREATE TABLE TESTER_DEVICE
(
    tester_id INT NOT NULL,
    device_id INT NOT NULL
)
AS SELECT * FROM CSVREAD('classpath:testerdata/tester_device.csv');

CREATE TABLE TESTERS
(
    tester_id INT PRIMARY KEY,
    first_name VARCHAR(250),
    last_name VARCHAR(250),
    country VARCHAR(3),
    last_login TIMESTAMP

)
AS SELECT * FROM CSVREAD('classpath:testerdata/testers.csv');





