CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS category
(
    id INT DEFAULT nextval('category_seq') not null primary key,
    description varchar(255),
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS product
(
        id INT DEFAULT nextval('product_seq') PRIMARY KEY,
        description varchar(255),
        name varchar(255),
        available_quantity double precision NOT null,
        price numeric(38, 2),
        category_id integer
                    CONSTRAINT fk_category REFERENCES category
);




