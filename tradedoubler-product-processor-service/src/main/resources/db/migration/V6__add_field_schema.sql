CREATE TABLE IF NOT EXISTS field
(
    id UUID NOT NULL,
    created_date_time TIMESTAMP NOT NULL,
    modified_date_time TIMESTAMP,
    product_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    CONSTRAINT field_pkey PRIMARY KEY (id),
    CONSTRAINT product_id_fkey FOREIGN KEY (product_id) REFERENCES product(id)
)
