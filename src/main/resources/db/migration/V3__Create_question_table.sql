drop table if exists `QUESTION`;
create table `QUESTION` (
  `id` bigint(20) not null auto_increment,
  `title` varchar(50) default null,
  `description` text,
  `gmt_create` bigint(20) default null,
  `gmt_modified` bigint(20) default null,
  `creator` bigint(20) default null,
  `comment_count` int(11) default '0',
  `view_count` int(11) not null default '0',
  `like_count` int(11) default '0',
  `tag` varchar(256) default null,
  primary key (`id`)
)