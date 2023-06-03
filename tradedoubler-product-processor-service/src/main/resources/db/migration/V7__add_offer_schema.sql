CREATE TABLE IF NOT EXISTS offer
(
    in_stock INTEGER,
    created_date_time TIMESTAMP NOT NULL,
    feed_id BIGINT,
    modified_date BIGINT,
    modified_date_time TIMESTAMP,
    id UUID NOT NULL,
    product_id UUID NOT NULL,
    availability VARCHAR(255) ,
    condition VARCHAR(255),
    date_format VARCHAR(255) ,
    delivery_time VARCHAR(255),
    offer_id VARCHAR(255) ,
    product_url VARCHAR(255) ,
    program_logo VARCHAR(255) ,
    program_name VARCHAR(255) ,
    shipping_cost VARCHAR(255) ,
    source_product_id VARCHAR(255) ,
    warranty VARCHAR(255) ,
    CONSTRAINT offer_pkey PRIMARY KEY (id),
    CONSTRAINT product_id_fkey FOREIGN KEY (product_id) REFERENCES product (id)
)