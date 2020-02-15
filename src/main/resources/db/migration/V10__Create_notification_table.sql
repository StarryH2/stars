create table notification
(
	id bigint auto_increment,
	notifier bigint,
	receiver bigint,
	outerId int,
	type int not null,
	gmt_create bigint,
	status int default 0,
	constraint notification_pk
		primary key (id)
);