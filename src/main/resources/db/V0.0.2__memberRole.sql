create table member_roles (
    role_id bigint not null,
    roles varchar(255),
    member_id bigint,
    primary key (role_id),
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member(member_id))
engine=InnoDB