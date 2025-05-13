CREATE TABLE complaints
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    content    VARCHAR(1000),
    created_at TIMESTAMP,
    reporter   VARCHAR(255) NOT NULL,
    country    VARCHAR(255),
    counter    INT
);

-- Add unique constraint on product_id + reporter
ALTER TABLE complaints
    ADD CONSTRAINT uq_product_reporter UNIQUE (product_id, reporter);

-- Create index on product_id
CREATE INDEX idx_complaints_product_id ON complaints (product_id);
