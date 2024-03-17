CREATE TABLE ENTITY (
                        id BIGSERIAL PRIMARY KEY,
                        name TEXT,
                        type TEXT
);

CREATE TABLE ACCOUNT (
                         id BIGSERIAL PRIMARY KEY,
                         name TEXT,
                         status TEXT,
                         entity_id BIGINT,
                         FOREIGN KEY (entity_id) REFERENCES ENTITY(id)
);

CREATE TABLE WALLET (
                        id BIGSERIAL PRIMARY KEY,
                        account_id BIGINT,
                        balance NUMERIC,
                        asset_type VARCHAR(255),
                        identifier VARCHAR(255),
                        FOREIGN KEY (account_id) REFERENCES ACCOUNT(id)
);

