CREATE TABLE IF NOT EXISTS offer
(
    id UUID NOT NULL,
    in_stock INTEGER,
    feed_id BIGINT,
    modified_date BIGINT,
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
    created_date_time TIMESTAMP NOT NULL,
    modified_date_time TIMESTAMP,
    CONSTRAINT offer_pkey PRIMARY KEY (id),
    CONSTRAINT product_id_fkey FOREIGN KEY (product_id) REFERENCES product (id)
)