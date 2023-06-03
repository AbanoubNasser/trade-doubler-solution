CREATE TABLE IF NOT EXISTS public.product_category
(
    product_id UUID NOT NULL,
    category_id UUID NOT NULL,
    CONSTRAINT product_category_pkey PRIMARY KEY (product_id, category_id),
    CONSTRAINT product_id_fkey FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT category_id_fkey FOREIGN KEY (category_id) REFERENCES category (id)
)
