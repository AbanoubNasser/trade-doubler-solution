CREATE TABLE IF NOT EXISTS product_image
(
    value VARCHAR(500),
    height integer,
    width integer,
    product_id uuid NOT NULL,
    created_date_time TIMESTAMP NOT NULL,
    modified_date_time TIMESTAMP,
    CONSTRAINT product_image_pkey PRIMARY KEY (product_id),
    CONSTRAINT product_id_fkey FOREIGN KEY (product_id) REFERENCES product(id)
)