CREATE TABLE IF NOT EXISTS price
(
    value NUMERIC(38,2),
    date BIGINT,
    offer_id UUID NOT NULL,
    currency VARCHAR(255) ,
    date_format VARCHAR(255),
    modified_date_time TIMESTAMP,
    created_date_time TIMESTAMP NOT NULL,
    CONSTRAINT price_pkey PRIMARY KEY (offer_id),
    CONSTRAINT offer_id_fkey FOREIGN KEY (offer_id) REFERENCES offer (id)
)