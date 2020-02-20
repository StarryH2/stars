drop table if exists `COMMENT`;
create table `COMMENT` (
  `id` bigint(20) not null auto_increment,
  `parent_id` bigint(20) not null,
  `type` int(11) not null,
  `commentator` bigint(20) not null,
  `gmt_create` bigint(20) not null,
  `gmt_modified` bigint(20) not null,
  `like_count` bigint(20) default '0',
  `content` varchar(1024) default null,
  `comment_count` int(11) default null,
  primary key (`id`)
)