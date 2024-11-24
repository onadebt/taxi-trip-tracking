CREATE TABLE IF NOT EXISTS "Category"
(
    "categoryID"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"              VARCHAR(255) NOT NULL UNIQUE,
    "icon"              VARCHAR(1000) NOT NULL,
    "createdAt"         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "Currency"
(
    "currencyID"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name"              VARCHAR(255) NOT NULL UNIQUE,
    "tag"               VARCHAR(10) NOT NULL UNIQUE,
    "rate"              FLOAT NOT NULL,
    "createdAt"         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "Settings"
(
    "settingsID"        INTEGER NOT NULL UNIQUE,
    "name"              VARCHAR(255) NOT NULL,
    "value"             VARCHAR(255) NOT NULL,
    "createdAt"         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "Ride"
(
    "rideID"        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "categoryID"    BIGINT REFERENCES "Category"("categoryID"),
    "currencyID"    BIGINT NOT NULL REFERENCES "Currency"("currencyID"),
    "amount"        FLOAT NOT NULL,
    "distance"      FLOAT NOT NULL,
    "passengers"    INTEGER NOT NULL,
    "tripType"      INTEGER NOT NULL,
    "createdAt"     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
