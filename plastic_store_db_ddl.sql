create schema if not exists plastic_store_db collate utf8mb4_0900_ai_ci;

create table if not exists cart
(
    cartId     int auto_increment
        primary key,
    customerId int not null,
    total      int not null
);

create table if not exists categories
(
    id   int auto_increment
        primary key,
    name varchar(255) null
);

create table if not exists colors
(
    id   int          not null
        primary key,
    code varchar(255) not null,
    name varchar(32)  not null
);

create table if not exists customers
(
    id           int auto_increment
        primary key,
    address      varchar(255) null,
    city         varchar(255) null,
    country      varchar(255) null,
    date_created datetime     null,
    email        varchar(255) null,
    first_name   varchar(255) null,
    last_name    varchar(255) null,
    password     varchar(255) null,
    phone_number varchar(255) null,
    reset_token  varchar(255) null,
    username     varchar(255) null,
    zip_code     int          null
);

create table if not exists guests
(
    id           int auto_increment
        primary key,
    address      varchar(255) null,
    city         varchar(255) null,
    country      varchar(255) null,
    email        varchar(255) null,
    first_name   varchar(255) null,
    last_name    varchar(255) null,
    password     varchar(255) null,
    phone_number varchar(255) null,
    username     varchar(255) null,
    zip_code     int          null
);

create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists messages
(
    id           int          not null
        primary key,
    answered     bit          not null,
    date_created datetime     null,
    email        varchar(255) null,
    name         varchar(255) null,
    subject      varchar(255) null,
    message      varchar(500) null
);

create table if not exists orders
(
    id            int auto_increment
        primary key,
    customer_id   int          not null,
    date_created  datetime     null,
    message       varchar(100) null,
    order_status  varchar(255) null,
    order_total   double       null,
    order_payment varchar(255) null,
    shipping      double       null,
    guest_id      int          null,
    constraint FKiqncvp2qrd60b82jmprx7eyxm
        foreign key (guest_id) references guests (id)
);

create table if not exists persistent_logins
(
    series    varchar(64) not null
        primary key,
    last_used datetime    not null,
    token     varchar(64) not null,
    username  varchar(64) not null
);

create table if not exists reviews
(
    id        int          not null
        primary key,
    comment   varchar(255) null,
    email     varchar(255) null,
    productId int          not null,
    rating    int          not null,
    reviewer  varchar(255) null,
    userId    int          not null
);

create table if not exists roles
(
    roleId int          not null
        primary key,
    role   varchar(255) null
);

create table if not exists spring_session
(
    PRIMARY_ID            char(36)     not null
        primary key,
    SESSION_ID            char(36)     not null,
    CREATION_TIME         bigint       not null,
    LAST_ACCESS_TIME      bigint       not null,
    MAX_INACTIVE_INTERVAL int          not null,
    EXPIRY_TIME           bigint       not null,
    PRINCIPAL_NAME        varchar(100) null,
    constraint SPRING_SESSION_IX1
        unique (SESSION_ID)
);

create index SPRING_SESSION_IX2
    on spring_session (EXPIRY_TIME);

create index SPRING_SESSION_IX3
    on spring_session (PRINCIPAL_NAME);

create table if not exists spring_session_attributes
(
    SESSION_PRIMARY_ID char(36)     not null,
    ATTRIBUTE_NAME     varchar(200) not null,
    ATTRIBUTE_BYTES    blob         not null,
    primary key (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    constraint SPRING_SESSION_ATTRIBUTES_FK
        foreign key (SESSION_PRIMARY_ID) references spring_session (PRIMARY_ID)
            on delete cascade
);

create table if not exists sub_categories
(
    id          int auto_increment
        primary key,
    name        varchar(255) null,
    category_id int          null,
    constraint FKjwy7imy3rf6r99x48ydq45otw
        foreign key (category_id) references categories (id)
);

create table if not exists products
(
    id              int auto_increment
        primary key,
    available       bit          null,
    code            int          null,
    date_created    datetime     null,
    date_updated    datetime     null,
    description     varchar(255) null,
    manufacturer    varchar(255) null,
    name            varchar(255) null,
    sale            bit          null,
    status          bit          null,
    category_id     int          null,
    sub_category_id int          null,
    constraint FKno5p9kcr384tg56cbk8l9l6h2
        foreign key (sub_category_id) references sub_categories (id),
    constraint FKog2rp4qthbtt2lfyhfo32lsw9
        foreign key (category_id) references categories (id)
);

create table if not exists cart_items
(
    id         int auto_increment
        primary key,
    color      varchar(255) null,
    price      int          not null,
    quantity   int          not null,
    size       varchar(255) null,
    totalPrice int          not null,
    cart_id    int          null,
    product_id int          null,
    color_id   int          null,
    constraint FK1re40cjegsfvw58xrkdp6bac6
        foreign key (product_id) references products (id),
    constraint FK99e0am9jpriwxcm6is7xfedy3
        foreign key (cart_id) references cart (cartId),
    constraint FKg4qoihenxb0iroyhs566xehur
        foreign key (color_id) references colors (id)
);

create table if not exists images
(
    id         int auto_increment
        primary key,
    name       varchar(64) not null,
    url        varchar(64) not null,
    product_id int         null,
    constraint FKghwsjbjo7mg3iufxruvq6iu3q
        foreign key (product_id) references products (id)
);

create table if not exists order_items
(
    id            int auto_increment
        primary key,
    color         varchar(255) null,
    price         double       not null,
    quantity      int          not null,
    size          varchar(255) null,
    totalPrice    int          not null,
    order_id      int          null,
    product_id    int          null,
    product_color int          null,
    constraint FKbioxgbv59vetrxe0ejfubep1w
        foreign key (order_id) references orders (id),
    constraint FKheb8bxpvx6lh3eikwrrlslyyu
        foreign key (product_color) references colors (id),
    constraint FKocimc7dtr037rh4ls4l95nlfi
        foreign key (product_id) references products (id)
);

create table if not exists product_attributes
(
    product_id       int          not null,
    discounted_price int          null,
    product_price    int          null,
    product_size     varchar(255) null,
    constraint FKcex46yvx4g18b2pn09p79h1mc
        foreign key (product_id) references products (id)
);

create table if not exists product_color
(
    id         int auto_increment
        primary key,
    code       varchar(255) not null,
    name       varchar(32)  not null,
    product_id int          null,
    constraint FKjs0ht7btbgt5u0jpossmgvfk5
        foreign key (product_id) references products (id)
);

create table if not exists promotions
(
    id          int auto_increment
        primary key,
    category_id int null,
    product_id  int null,
    constraint FK5ukm0jhih3cbin6dhkppos7ot
        foreign key (product_id) references products (id),
    constraint FK909g9g1svefta8r8hvluj7j0n
        foreign key (category_id) references categories (id)
);

create table if not exists user_role
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint UK_it77eq964jhfqtu54081ebtio
        unique (role_id),
    constraint FKftwvf24o6btr5cilynp8cw9ev
        foreign key (user_id) references customers (id),
    constraint FKt7e7djp752sqn6w22i6ocqy6q
        foreign key (role_id) references roles (roleId)
);

create table if not exists wishlist
(
    id        int not null
        primary key,
    productId int not null,
    userId    int not null
);

