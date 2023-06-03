CREATE TABLE IF NOT EXISTS category
(
    id UUID NOT NULL,
    category_id INTEGER,
    name VARCHAR(255) NOT NULL,
    td_category_name VARCHAR(255) NOT NULL,
    created_date_time TIMESTAMP NOT NULL,
    modified_date_time TIMESTAMP,
    CONSTRAINT category_pkey PRIMARY KEY (id)
)