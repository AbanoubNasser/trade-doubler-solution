CREATE TABLE IF NOT EXISTS products_files (
    id VARCHAR(50) PRIMARY KEY,
    file_name VARCHAR(100) NOT NULL,
    status VARCHAR(25) NOT NULL,
    comment VARCHAR(500) DEFAULT NULL,
    created_date_time TIMESTAMP NOT NULL,
    modified_date_time TIMESTAMP DEFAULT NULL
)