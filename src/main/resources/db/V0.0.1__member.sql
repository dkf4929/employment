
create table member (
    member_id bigint not null auto_increment,
    birthday date,
    edit_yn varchar(255),
    email varchar(255),
    login_id varchar(255),
    member_name varchar(255),
    password varchar(255),
    phone_number varchar(255),
    school_name varchar(255),
    social_login_yn varchar(255),
    primary key (member_id)
) engine=InnoDB
