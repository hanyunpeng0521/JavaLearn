DROP TABLE IF EXISTS `article`;
DROP TABLE IF EXISTS `classify`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `user`;


CREATE TABLE `article`
(
  `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title`         varchar(255) NULL,
  `context`       text         NULL,
  `create_time`   timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` datetime     NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted`    tinyint      NULL DEFAULT 0,
  `user_id`       bigint       NULL,
  `user_name`     varchar(255) NULL,
  `clz_id`        bigint       NULL,
  `clz_name`      varchar(255) NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `comment`
(
  `id`          bigint       NOT NULL AUTO_INCREMENT,
  `user_id`     bigint       NULL,
  `user_name`   varchar(255) NULL,
  `article_id`  bigint       NULL,
  `context`     text         NULL COMMENT '评论内容',
  `create_time` timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
  `is_deleted`  tinyint      NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);
CREATE TABLE `classify`
(
  `id`         bigint       NOT NULL AUTO_INCREMENT,
  `user_id`    bigint(11)   NULL,
  `name`       varchar(500) NULL,
  `level`      int          NULL,
  `is_deleted` tinyint      NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);
CREATE TABLE `user`
(
  `id`          bigint       NOT NULL AUTO_INCREMENT,
  `name`        varchar(255) NOT NULL,
  `passwd`      varchar(500) NOT NULL,
  `is_deleted`  tinyint      NULL DEFAULT 0,
  `create_time` timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
  `birthday`    datetime     NULL,
  PRIMARY KEY (`id`)
);



ALTER TABLE `article`
  ADD CONSTRAINT `fk_article_classify_1` FOREIGN KEY (`clz_id`) REFERENCES `classify` (`id`);
ALTER TABLE `article`
  ADD CONSTRAINT `fk_article_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `comment`
  ADD CONSTRAINT `fk_comment_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `comment`
  ADD CONSTRAINT `fk_comment_article_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`);


-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (-2, '游客', '123456789', 0, '2019-12-14 05:00:07', NULL);
INSERT INTO `user`
VALUES (1, 'pingxin', 'QAHlVoUc49w=', 0, '2019-12-14 05:01:06', NULL);

-- ----------------------------
-- Records of classify
-- ----------------------------
INSERT INTO `classify`
VALUES (1, 1, '学习', 1, 0);
INSERT INTO `classify`
VALUES (2, 1, '日常', 2, 0);
INSERT INTO `classify`
VALUES (3, 1, '感悟', 3, 0);
INSERT INTO `classify`
VALUES (4, 1, '新闻', 4, 0);



-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article`
VALUES (1, '山西一中学禁网购发现即没收？教体局：已要求校方核查',
        '新京报讯（记者 李一凡）山西吕梁交口县一中被多名家长网络举报，称该校下发通知禁学生网购，发现后还会将快递没收。今日（12月12日）下午，交口县教育体育科技局（以下简称“交口县教体局”）一男工作人员回应新京报记者称，已要求校方核查，禁学生网购或与该校“不允许学生带手机规定”有关。其还表示，将就此情况对外通报',
        '2019-12-14 05:04:53', '2019-12-14 05:04:53', 0, 1, '平心', 4, '新闻');
INSERT INTO `article`
VALUES (2, 'cookie属性详解',
        '<p>在chrome控制台中的resources选项卡中可以看到cookie的信息。</p>\r\n\r\n<p>一个域名下面可能存在着很多个cookie对象。</p>\r\n\r\n<p>name　　字段为一个cookie的名称。</p>\r\n\r\n<p>value　　字段为一个cookie的值。</p>\r\n\r\n<p>domain　　字段为可以访问此cookie的域名。</p>\r\n\r\n<p>非顶级域名，如二级域名或者三级域名，设置的cookie的domain只能为顶级域名或者二级域名或者三级域名本身，不能设置其他二级域名的cookie，否则cookie无法生成。 顶级域名只能设置domain为顶级域名，不能设置为二级域名或者三级域名，否则cookie无法生成。 二级域名能读取设置了domain为顶级域名或者自身的cookie，不能读取其他二级域名domain的cookie。所以要想cookie在多个二级域名中共享，需要设置domain为顶级域名，这样就可以在所有二级域名里面或者到这个cookie的值了。 顶级域名只能获取到domain设置为顶级域名的cookie，其他domain设置为二级域名的无法获取。</p>\r\n\r\n<p>path　　字段为可以访问此cookie的页面路径。 比如domain是abc.com,path是/test，那么只有/test路径下的页面可以读取此cookie。</p>\r\n\r\n<p>expires/Max-Age 　　字段为此cookie超时时间。若设置其值为一个时间，那么当到达此时间后，此cookie失效。不设置的话默认值是Session，意思是cookie会和session一起失效。当浏览器关闭(不是浏览器标签页，而是整个浏览器) 后，此cookie失效。</p>\r\n\r\n<p>Size　　字段 此cookie大小。</p>\r\n\r\n<p>http　　字段 cookie的httponly属性。若此属性为true，则只有在http请求头中会带有此cookie的信息，而不能通过document.cookie来访问此cookie。</p>\r\n\r\n<p>secure　　 字段 设置是否只能通过https来传递此条cookie</p>\r\n',
        '2019-12-14 05:05:42', '2019-12-14 06:02:21', 0, 1, '平心', 1, '学习');



-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment`
VALUES (1, -2, '于大爷', 2, '去你的吧', '2019-12-14 06:02:59', 0);

