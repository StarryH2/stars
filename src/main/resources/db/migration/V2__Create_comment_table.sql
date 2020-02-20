DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PARENT_ID` bigint(20) NOT NULL,
  `TYPE` int(11) NOT NULL,
  `COMMENTATOR` bigint(20) NOT NULL,
  `GMT_CREATE` bigint(20) NOT NULL,
  `GMT_MODIFIED` bigint(20) NOT NULL,
  `LIKE_COUNT` bigint(20) DEFAULT '0',
  `CONTENT` varchar(1024) DEFAULT NULL,
  `COMMENT_COUNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
)