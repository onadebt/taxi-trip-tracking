--
-- Department table definition
--
DELETE TABLE IF EXISTS  "Department"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `number`    VARCHAR(10) NOT NULL UNIQUE,
    `name`      VARCHAR(50) NOT NULL,
    `createdAt` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

--
-- Employee table definition
--
DELETE TABLE IF EXISTS "Employee"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `number`       VARCHAR(10)  NOT NULL UNIQUE,
    `firstName`    VARCHAR(150) NOT NULL,
    `lastName`     VARCHAR(150) NOT NULL,
    `birthDate`    DATE         NOT NULL,
    `departmentId` BIGINT REFERENCES "Department"(`id`),
    `createdAt`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
