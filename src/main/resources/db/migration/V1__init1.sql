drop table inventory if exists ;
create table inventory(sku_id bigint, product_name varchar(255),product_label varchar(255), inventory_on_hand int, min_Qty_Req int,price double(50),primary key (sku_id));



