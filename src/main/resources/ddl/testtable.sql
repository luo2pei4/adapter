CREATE TABLE `adsb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `arr` varchar(16) DEFAULT NULL COMMENT '目的站',
  `dep` varchar(16) DEFAULT NULL COMMENT '始发站',
  `fi` varchar(16) DEFAULT NULL COMMENT '航班号',
  `icao24` varchar(24) DEFAULT NULL COMMENT '机器识别码',
  `an` varchar(16) DEFAULT NULL COMMENT '飞机注册号',
  `alt` varchar(16) DEFAULT NULL,
  `lon` varchar(16) DEFAULT NULL,
  `cas` varchar(32) DEFAULT NULL,
  `lat` varchar(16) DEFAULT NULL,
  `vec` decimal(20,16) DEFAULT NULL,
  `ssr` varchar(16) DEFAULT NULL COMMENT '状态代码',
  `type` varchar(16) DEFAULT NULL,
  `sendTime` datetime DEFAULT NULL COMMENT '发送时间',
  `threadid` varchar(48) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18132 DEFAULT CHARSET=utf8mb4

CREATE TABLE `flightinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `arr` varchar(16) DEFAULT NULL COMMENT '目的站',
  `dep` varchar(16) DEFAULT NULL COMMENT '始发站',
  `fi` varchar(16) DEFAULT NULL COMMENT '航班号',
  `icao24` varchar(24) DEFAULT NULL COMMENT '机器识别码',
  `an` varchar(16) DEFAULT NULL COMMENT '飞机注册号',
  `alt` varchar(16) DEFAULT NULL,
  `lon` varchar(16) DEFAULT NULL,
  `cas` varchar(32) DEFAULT NULL,
  `lat` varchar(16) DEFAULT NULL,
  `vec` decimal(20,16) DEFAULT NULL,
  `ssr` varchar(16) DEFAULT NULL COMMENT '状态代码',
  `type` varchar(16) DEFAULT NULL,
  `sendTime` datetime DEFAULT NULL COMMENT '发送时间',
  `threadid` varchar(48) DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4