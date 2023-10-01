create table attach_file (
    attach_file_id bigint not null auto_increment,
    attach_entity varchar(255),
    attach_type varchar(255),
    entity_id bigint,
    file_name varchar(255),
    file_path varchar(255),
primary key (attach_file_id)) engine=InnoDB