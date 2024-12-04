CREATE TABLE IF NOT EXISTS "Category"
(
    "id"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"              VARCHAR(255) NOT NULL UNIQUE,
    "icon"              VARCHAR(1000) NOT NULL,
    "createdAt"         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "Currency"
(
    "id"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"              VARCHAR(255) NOT NULL UNIQUE,
    "tag"               VARCHAR(10) NOT NULL UNIQUE,
    "rate"              FLOAT NOT NULL,
    "createdAt"         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "Settings"
(
    "id"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"              VARCHAR(255) NOT NULL,
    "value"             VARCHAR(255) NOT NULL,
    "createdAt"         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "Ride"
(
    "id"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "categoryId"    BIGINT REFERENCES "Category"("id"),
    "currencyId"    BIGINT NOT NULL REFERENCES "Currency"("id"),
    "amount"        FLOAT NOT NULL,
    "distance"      FLOAT NOT NULL,
    "passengers"    INTEGER NOT NULL,
    "tripType"      VARCHAR(255) NOT NULL,
    "createdAt"     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "uuid"          VARCHAR(36) NOT NULL
);
