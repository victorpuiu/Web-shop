DROP TABLE IF EXISTS public.category CASCADE;
DROP TABLE IF EXISTS public.supplier CASCADE;
DROP TABLE IF EXISTS public.product CASCADE;
DROP TABLE IF EXISTS public.cart CASCADE;
DROP TABLE IF EXISTS public.product_in_cart CASCADE;
DROP TABLE IF EXISTS public.customer CASCADE;
DROP TABLE IF EXISTS public.order_history CASCADE;

CREATE TABLE public.category
(
    id   serial  NOT NULL PRIMARY KEY,
    name varchar NOT NULL
);

CREATE TABLE public.supplier
(
    id   serial  NOT NULL PRIMARY KEY,
    name varchar NOT NULL
);

CREATE TABLE public.product
(
    id          serial  NOT NULL PRIMARY KEY,
    name        varchar NOT NULL,
    description varchar,
    price       decimal,
    currency    varchar NOT NULL,
    category_id integer NOT NULL,
    supplier_id integer NOT NULL
);

CREATE TABLE public.cart
(
    id      serial NOT NULL PRIMARY KEY,
    user_id integer,
    payed   bool   NOT NULL
);

CREATE TABLE public.product_in_cart
(
    id         serial  NOT NULL PRIMARY KEY,
    cart_id    integer NOT NULL,
    product_id integer NOT NULL
);

CREATE TABLE public.customer
(
    id       serial  NOT NULL PRIMARY KEY,
    name     varchar NOT NULL,
    email    varchar NOT NULL,
    password varchar NOT NULL,
    address  varchar,
    city     varchar,
    state    varchar,
    zip_code varchar
);

CREATE TABLE public.order_history
(
    id            serial    NOT NULL PRIMARY KEY,
    user_id       integer,
    order_history varchar   NOT NULL,
    order_date    timestamp NOT NULL,
    order_status  varchar   NOT NULL
);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES public.category (id),
    ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES public.supplier (id);

ALTER TABLE ONLY public.product_in_cart
    ADD CONSTRAINT fk_cart_id FOREIGN KEY (cart_id) REFERENCES public.cart (id),
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES public.product (id);

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.customer (id);

ALTER TABLE ONLY order_history
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.customer (id);

INSERT INTO supplier (name)
VALUES ('Jim Shore'),
       ('Department 56'),
       ('Possible Dreams'),
       ('Willow Tree'),
       ('Fontanini'),
       ('ChuWi'),
       ('Christmas heart');

INSERT INTO category (name)
VALUES ('Tablet'),
       ('Console'),
       ('Cell Phone'),
       ('Smart Watch'),
       ('Computer'),
       ('Camera'),
       ('Television');

INSERT INTO product (name, price, currency, description, category_id, supplier_id)
VALUES ('Laptop Apple MacBook Air 13-inch', 812, 'USD',' ',5, 6),
       ('Watch Ultra Titanium', 880, 'USD',
        '',
        4, 1),
       ('Samsung Galaxy Smart Watch', 369, 'USD',
        '',
        4, 3),
       ('Watch 8, GPS, Cellular', 719, 'USD',
        '', 4, 2),
       ('Samsung Tab A8', 212, 'USD',
        '',
        1, 3),
       ('Huawei MatePad', 135, 'USD',
        '',
        1, 1),
       ('iPad 9 (2021), 10.2 ', 516, 'USD',
        '',
        1, 2),
       ('Game Station NINTENDO SWITCH', 270, 'USD',
        '', 2, 4),
       ('Nintendo  Console', 131, 'USD',
        '', 2, 5),
       ('Game Station PS', 835, 'USD',
        '',
        2, 4),
       ('Hand Console', 156, 'USD',
        '',
        2, 5),
       ('Nintendo Switch', 109, 'USD',
        '',
        2, 5),
       ('1Phone i13 Pro Max', 1500, 'USD',
        'Unlocked Cell Phones I13pro Max Cell Phones 4G 5G Dual SIM Cards Support T Card (12GB RAM+512GB ROM) Android 10.0 Smartphone',
        3, 2),
       ('Samsong S20', 854, 'USD',
        'In 2021, a newly launched 6.7-inch 3K HD screen 16+512GB fingerprint smartphone (champagne gold, psychedelic blue, dark night green)',
        3, 3),
       ('OUKITEL WP12 Pro, Android 11', 198, 'USD',
        '2021 Fashion New Handy Smartphone P60 Pro 5G with 16+768GB Large Memory 48+64MP HD camara Dual Sim Cards Bluetooth Wifi 6000mA/h battery Mobile Phone Android 10.0 Ten Core',
        3, 1),
       ('Samsung S21 U+', 823, 'USD',
        '2021 Brand New S21+ Ultra 5G 6.1Inch HD Screen 16+512GB Unlocked Global Version Smartphone (Black/Blue/Green)',
        3, 3),
       ('Samsung Note30 Plus', 15600, 'USD',
        'New Note30 Plus Smartphone the thinest 6.1 Inches Large Memory 12GB+512GB Android 10.0 Face Unlock Dual Card Phone Supports T-card Smartphone',
        3, 3),
       ('Game Station 4 pro', 754, 'USD',
        '',
        2, 4),
       ('CANON EOS 2000D,24.1 MP', 628, 'USD',
        'Digital Camera 16X Focus Zoom Design Camera1280x720 Supported 32GB Card Portable Digital Camera for Travel Photo Taking',
        6, 7),
       ('Canon EOS 4000D,18.0 MP', 10878, 'USD',
        'Full HD Digital Camera, 33MP Digital DSLR Camera 0.5X Auto Focus Wide Angle Lens Kit, Rechargeable 24X Optical Zoom Multifunctional Portable Camera, Best Gift For Photography Lovers',
        6, 7),
       ('Samsung 43AU7092, 108 cm`', 554, 'USD',
        '',
        7, 3),
       ('Samsung FULL HD 112`', 600, 'USD',
        '',
        7, 3);

INSERT INTO public.customer (name, email, password)
VALUES ('admin', 'admin@test.com', '11111')