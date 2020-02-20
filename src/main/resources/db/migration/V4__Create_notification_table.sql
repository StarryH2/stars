drop table if exists `NOTIFICATION`;
create table `NOTIFICATION` (
  `id` bigint(20) not null auto_increment,
  `notifier` bigint(20) default null,
  `receiver` bigint(20) default null,
  `outerid` bigint(20) default null,
  `type` int(11) default null,
  `gmt_create` bigint(20) default null,
  `status` int(11) default '0',
  `notifier_name` varchar(100) default null,
  `outer_title` varchar(256) default null,
  primary key (`id`)
)