drop table if exists enterprise, product, staff, supply_center, rel_supply_center_staff, rel_supply_center_enterprise, rel_supply_center_product, rel_task1, rel_task2_order, rel_task2_contract, rel_contract_order cascade;

CREATE TABLE IF NOT EXISTS supply_center
(
    id   serial PRIMARY KEY,
    name character varying(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS enterprise
(
    id       integer PRIMARY KEY,
    name     character varying(100) NOT NULL UNIQUE,
    country  character varying(64)  NOT NULL,
    city     character varying(64),
    industry character varying(64)  NOT NULL
);

CREATE TABLE IF NOT EXISTS product
(
    id         int PRIMARY KEY,
    number     character varying(10)  NOT NULL,
    model      character varying(128) NOT NULL UNIQUE,
    name       character varying(128) NOT NULL,
    unit_price integer                NOT NULL
);

CREATE TABLE IF NOT EXISTS staff
(
    id            int PRIMARY KEY,
    name          character varying(64) NOT NULL,
    age           integer               NOT NULL,
    gender        character varying(6)  NOT NULL,
    number        integer               NOT NULL UNIQUE,
    mobile_number character varying(11) NOT NULL UNIQUE,
    type          character varying(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS rel_supply_center_enterprise
(
    supply_center_id integer NOT NULL,
    enterprise_id    integer NOT NULL
);

CREATE TABLE IF NOT EXISTS rel_supply_center_product
(
    supply_center_id serial  NOT NULL,
    product_id       integer NOT NULL,
    average_price    integer NOT NULL,
    current_quantity integer NOT NULL,
    sold_quantity    integer NOT NULL
);

CREATE TABLE IF NOT EXISTS rel_supply_center_staff
(
    supply_center_id integer NOT NULL,
    staff_id         integer NOT NULL
);

CREATE TABLE IF NOT EXISTS rel_task1
(
    id             serial PRIMARY KEY,
    supply_center  character varying(64)  NOT NULL,
    product_model  character varying(128) NOT NULL,
    supply_staff   integer                NOT NULL,
    date           date                   NOT NULL,
    purchase_price integer                NOT NULL,
    quantity       integer
);

CREATE TABLE IF NOT EXISTS rel_task2_contract
(
    contract_num     character varying(10)  NOT NULL UNIQUE PRIMARY KEY,
    enterprise_name  character varying(100) NOT NULL,
    contract_manager integer                NOT NULL,
    contract_type    character varying(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS rel_task2_order
(
    id                      serial                 NOT NULL UNIQUE PRIMARY KEY,
    product_model           character varying(128) NOT NULL,
    quantity                integer                NOT NULL,
    contract_date           date                   NOT NULL,
    estimated_delivery_date date                   NOT NULL,
    lodgement_date          date                   NOT NULL,
    salesman_num            integer                NOT NULL
);
CREATE TABLE IF NOT EXISTS rel_contract_order
(
    contract_num character varying(10) NOT NULL,
    order_id     integer               NOT NULL UNIQUE PRIMARY KEY
);



alter table rel_supply_center_enterprise
    add constraint rel_supply_center_enterprise_supply_center_id_fk0 foreign key (supply_center_id) references supply_center (id);
alter table rel_supply_center_enterprise
    add constraint rel_supply_center_enterprise_enterprise_id_fk0 foreign key (enterprise_id) references enterprise (id);

alter table rel_supply_center_staff
    add constraint rel_supply_center_staff_staff_id_fk0 foreign key (staff_id) references staff (id);
alter table rel_supply_center_staff
    add constraint rel_supply_center_staff_supply_center_id_fk0 foreign key (supply_center_id) references supply_center (id);

alter table rel_supply_center_product
    add constraint rel_supply_center_product_supply_center_id_fk0 foreign key (supply_center_id) references supply_center (id);
alter table rel_supply_center_product
    add constraint rel_supply_center_product_product_id_fk0 foreign key (product_id) references product (id);

alter table rel_task1
    add constraint rel_task1_supply_center_fk0 foreign key (supply_center) references supply_center (name);
alter table rel_task1
    add constraint rel_task1_product_model_fk0 foreign key (product_model) references product (model);
alter table rel_task1
    add constraint rel_task1_supply_staff_fk0 foreign key (supply_staff) references staff (number);


alter table rel_task2_contract
    add constraint rel_task2_contract_enterprise_name_fk0 foreign key (enterprise_name) references enterprise (name);
alter table rel_task2_contract
    add constraint rel_task2_contract_contract_manager_fk0 foreign key (contract_manager) references staff (number);

alter table rel_task2_order
    add constraint rel_task2_order_product_model_fk0 foreign key (product_model) references product (model);
alter table rel_task2_order
    add constraint rel_task2_order_salesman_num_fk0 foreign key (salesman_num) references staff (number);

alter table rel_contract_order
    add constraint rel_contract_order_contract_num_fk0 foreign key (contract_num) references rel_task2_contract (contract_num);
alter table rel_contract_order
    add constraint rel_contract_order_order_id_fk0 foreign key (order_id) references rel_task2_order (id);

create user super_user with password '123456';
create user analysis_staff with password '123456';
create user salesman with password '123456';
create user senior_salesman with password '123456';
create user supply_staff with password '123456';

grant all on all sequences in schema public to PUBLIC;
grant select on all tables in schema public to PUBLIC;
grant all on all tables in schema public to super_user;

grant insert on table rel_task1
    to supply_staff;
grant insert on table rel_task2_contract, rel_task2_order, rel_contract_order
    to salesman, senior_salesman;
grant update on table rel_supply_center_product
    to supply_staff, salesman, senior_salesman;
grant delete on table rel_task2_order, rel_contract_order
    to senior_salesman;

create index product_id on product (id);
create index product_mo on product (model);
create index enterprise_id on enterprise (id);
create index supply_center_id on supply_center (id);
create index staff_id on staff (id);

create index rel_sp_s on rel_supply_center_product (supply_center_id);
create index rel_sp_p on rel_supply_center_product (product_id);
create index rel_ss_sc on rel_supply_center_staff (supply_center_id);
create index rel_ss_st on rel_supply_center_staff (staff_id);
create index rel_se_s on rel_supply_center_enterprise (supply_center_id);
create index rel_se_e on rel_supply_center_enterprise (enterprise_id);