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
    "rate"              NUMERIC NOT NULL,
    "createdAt"         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "Settings"
(
    "id"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"              VARCHAR(255) NOT NULL UNIQUE,
    "value"             VARCHAR(255) NOT NULL,
    "createdAt"         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "Ride"
(
    "id"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "categoryId"    BIGINT REFERENCES "Category"("id"),
    "currencyId"    BIGINT NOT NULL REFERENCES "Currency"("id"),
    "amount"        NUMERIC NOT NULL,
    "distance"      FLOAT NOT NULL,
    "passengers"    INTEGER NOT NULL,
    "tripType"      VARCHAR(255) NOT NULL,
    "createdAt"     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "uuid"          VARCHAR(36) NOT NULL
);

MERGE INTO "Settings" AS target
    USING (SELECT 'DefaultDistanceUnit' AS "name", 'KILOMETER' AS "value") AS source
    ON target."name" = source."name"
    WHEN NOT MATCHED THEN
        INSERT ("name", "value") VALUES (source."name", source."value");

MERGE INTO "Settings" AS target
    USING (SELECT 'DefaultCurrency' AS "name", 'EUR' AS "value") AS source
    ON target."name" = source."name"
    WHEN NOT MATCHED THEN
        INSERT ("name", "value") VALUES (source."name", source."value");

MERGE INTO "Currency" AS target
    USING (SELECT 'Euro' AS "name", 'EUR' AS "tag", 1.0 AS "rate") AS source
    ON target."tag" = source."tag"
    WHEN NOT MATCHED THEN
        INSERT ("name", "tag", "rate") VALUES (source."name", source."tag", source."rate");
