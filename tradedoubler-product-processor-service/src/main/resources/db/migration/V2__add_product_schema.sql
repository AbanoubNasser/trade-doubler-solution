CREATE TABLE IF NOT EXISTS product
(
    id UUID NOT NULL,
    brand VARCHAR(255) ,
    description VARCHAR(255) ,
    ean VARCHAR(255) ,
    grouping_id VARCHAR(255) ,
    isbn VARCHAR(255) ,
    language VARCHAR(255) ,
    manufacturer VARCHAR(255) ,
    model VARCHAR(255) ,
    mpn VARCHAR(255) ,
    name VARCHAR(255) ,
    promo_text VARCHAR(255) ,
    short_description VARCHAR(255) ,
    size VARCHAR(255) ,
    sku VARCHAR(255) ,
    tech_specs VARCHAR(255) ,
    upc VARCHAR(255) ,
    weight VARCHAR(255) ,
    product_file_id VARCHAR(50),
    created_date_time TIMESTAMP NOT NULL,
    modified_date_time TIMESTAMP,
    CONSTRAINT product_pkey PRIMARY KEY (id)
)