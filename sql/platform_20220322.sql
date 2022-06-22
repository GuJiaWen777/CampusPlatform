-- ----------------------------
-- 创建数据库
-- ----------------------------
create database if not exists platform;
use platform;
-- ----------------------------
-- 1、用户表
-- ----------------------------
DROP TABLE IF EXISTS user;
CREATE TABLE user(
    user_id INT(16) NOT NULL AUTO_INCREMENT  COMMENT '用户号' ,
    user_name VARCHAR(32) NOT NULL   COMMENT '用户名' ,
    user_gen TINYINT(1)   DEFAULT 0 COMMENT '性别' ,
    user_pwd VARCHAR(16)  NOT NULL DEFAULT '123456' COMMENT '密码' ,
    user_phone VARCHAR(16)   COMMENT '手机号' ,
    user_addr VARCHAR(255) DEFAULT 'NJAU'   COMMENT '地址' ,
    user_num VARCHAR(16)    COMMENT '学号' ,
    user_point INT(32)   DEFAULT 0 COMMENT '积分余额' ,
    user_level INT(32)   DEFAULT 0 COMMENT '用户等级' ,
    user_credit INT(16) DEFAULT 90  COMMENT '信用值（上限100，优秀80~100，良好60~80，<60拉黑）' ,
    logoff_status TINYINT(1)   DEFAULT 0 COMMENT '注销标志（0正常 1注销）;0为正常，1为注销' ,
    block_status TINYINT(1)   DEFAULT 0 COMMENT '角色状态（0正常 1拉黑）;0为正常，1为拉黑' ,
    avatar VARCHAR(255) DEFAULT 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'   COMMENT '头像地址' ,
    create_by VARCHAR(16)   DEFAULT '' COMMENT '创建者' ,
    created_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_by VARCHAR(16)   DEFAULT '' COMMENT '更新者' ,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (user_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '用户表' AUTO_INCREMENT=20000;

-- ----------------------------
-- 初始化-用户表数据
-- ----------------------------
insert into user values(10001 ,'admin' ,0 ,'123456' ,'15888888888' ,'南京农业大学' ,'15111111' ,0 ,0,100,0 ,0 ,'el-icon-user','administrator' ,sysdate() ,'' ,null);
insert into user values(10002 ,'test' ,0 ,'123456' ,'15888888888' ,'南京农业大学' ,'15111111' ,0 ,0,100,0 ,0,'el-icon-user','administrator' ,sysdate() ,'' ,null);

-- ----------------------------
-- 2、角色表
-- ----------------------------
DROP TABLE IF EXISTS role;
CREATE TABLE role(
    role_id INT(16) NOT NULL AUTO_INCREMENT  COMMENT '角色号' ,
    role_name VARCHAR(16) NOT NULL   COMMENT '角色名称' ,
    role_key VARCHAR(16) NOT NULL   COMMENT '角色权限字符串' ,
    block_status TINYINT(1)   DEFAULT 0 COMMENT '角色状态;0正常 1停用' ,
    delete_status TINYINT(1)   DEFAULT 0 COMMENT '删除标志;0代表存在 1代表删除' ,
    create_by VARCHAR(16)   DEFAULT '' COMMENT '创建者' ,
    created_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_by VARCHAR(16)   DEFAULT '' COMMENT '更新者' ,
    updated_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (role_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '角色表';

-- ----------------------------
-- 初始化-角色表数据
-- ----------------------------
insert into role values(1 ,'管理员' ,'admin' ,0 ,0 ,'administrator' ,sysdate() ,'' ,null);
insert into role values(2 ,'普通用户' ,'common' ,0 ,0 ,'administrator' ,sysdate() ,'' ,null);

-- ----------------------------
-- 3、用户角色表 用户N-1角色
-- ----------------------------
DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role(
    user_id INT(16) NOT NULL   COMMENT '用户号' ,
    role_id INT(16) NOT NULL  DEFAULT 0 COMMENT '角色号' ,
    PRIMARY KEY (user_id,role_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '用户角色表';

-- ----------------------------
-- 初始化-用户角色表
-- ----------------------------
insert into user_role values (10001 ,1);
insert into user_role values (10002 ,2);

-- ----------------------------
-- 4、积分记录表
-- ----------------------------
DROP TABLE IF EXISTS point_record;
CREATE TABLE point_record(    
	id INT(16) NOT NULL AUTO_INCREMENT  COMMENT '记录号' ,
    user_id INT(32) NOT NULL   COMMENT '用户号' ,
    point_num INT(32)   DEFAULT 0 COMMENT '积分更改数量' ,
    alter_description VARCHAR(255)   DEFAULT '' COMMENT '积分更改描述' ,
    record_time DATETIME    COMMENT '记录时间' ,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '积分记录表' AUTO_INCREMENT=3;

-- ----------------------------
-- 初始化-积分记录表
-- ----------------------------
insert into point_record values(1, 10002 ,100,'测试记录',sysdate());

-- ----------------------------
-- 5、任务表
-- ----------------------------
DROP TABLE IF EXISTS task;
CREATE TABLE task(
    task_id INT(32) NOT NULL AUTO_INCREMENT  COMMENT '任务号' ,
    task_title VARCHAR(16) NOT NULL   COMMENT '任务标题' ,
    task_description VARCHAR(255)   DEFAULT null COMMENT '任务描述' ,
    task_points INT(32)   DEFAULT 0 COMMENT '任务奖励积分' ,
    commit_status TINYINT   DEFAULT 0 COMMENT '发布任务状态;0为未发布，1为已发布' ,
    task_status TINYINT   DEFAULT 0 COMMENT '任务运行状态;0为待接受，1为进行中，2为已完成，3为已终止（用户可以看见，但无法接受）' ,
    delete_status TINYINT(1)   DEFAULT 0 COMMENT '是否被删除;0为未删除，1为已删除' ,
    released_by INT(16) NOT NULL   COMMENT '发布人ID' ,
    released_time DATETIME    COMMENT '发布时间' ,
    create_by VARCHAR(16)   DEFAULT '' COMMENT '创建者' ,
    created_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_by VARCHAR(16)   DEFAULT '' COMMENT '更新者' ,
    updated_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (task_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '任务表' AUTO_INCREMENT=5;

-- ----------------------------
-- 建立任务表索引
-- ----------------------------
CREATE INDEX index_taskby ON task(released_by);
-- ----------------------------
-- 初始化-任务表
-- ----------------------------
insert into task values(1 ,'帮忙拿外卖' ,'学校南门处' ,0 ,0 ,0 ,0 ,10001 ,sysdate(),'administrator' ,sysdate() ,'' ,null);
insert into task values(2 ,'帮忙取快递' ,'一舍前' ,0 ,0 ,0 ,0 ,10001 ,sysdate(),'administrator' ,sysdate() ,'' ,null);
insert into task values(3 ,'帮忙拿外卖' ,'学校南门处' ,0 ,2 ,1 ,0 ,10001 ,sysdate(),'administrator' ,sysdate() ,'' ,null);
insert into task values(4 ,'帮忙取快递' ,'一舍前' ,0 ,2 ,2 ,0 ,10001 ,sysdate(),'administrator' ,sysdate() ,'' ,null);

-- ----------------------------
-- 6、任务接受表
-- ----------------------------
DROP TABLE IF EXISTS task_received;
CREATE TABLE task_received(
    user_id INT(16) NOT NULL   COMMENT '用户号' ,
    task_id INT(16) NOT NULL   COMMENT '任务号' ,
    task_received_time DATETIME    COMMENT '接受任务时间' ,
    PRIMARY KEY (user_id,task_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '任务接受表';

-- ----------------------------
-- 初始化-任务接受表
-- ----------------------------
insert into task_received values(10002 ,3 ,sysdate());

-- ----------------------------
-- 7、任务完成表
-- ----------------------------
DROP TABLE IF EXISTS task_done;
CREATE TABLE task_done(
    user_id INT(16) NOT NULL   COMMENT '用户号' ,
    task_id INT(16) NOT NULL   COMMENT '任务号' ,
    task_done_time DATETIME    COMMENT '完成任务时间' ,
    PRIMARY KEY (user_id,task_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '任务完成表';

-- ----------------------------
-- 初始化-任务完成表
-- ----------------------------
insert into task_done values(10002 ,4 ,sysdate());

-- ----------------------------
-- 8、奖励品表
-- ----------------------------
DROP TABLE IF EXISTS reward;
CREATE TABLE reward(
    reward_id INT(32) NOT NULL AUTO_INCREMENT  COMMENT '奖励品号' ,
    reward_name VARCHAR(32) NOT NULL   COMMENT '奖励品名称' ,
    reward_num INT(32)   DEFAULT 0 COMMENT '奖励品库存' ,
    reward_over TINYINT(1)   DEFAULT 0 COMMENT '是否为空;0为空，1为非空' ,
    reward_point INT(32)   DEFAULT 0 COMMENT '奖励品所需积分' ,
    picture  VARCHAR(255) DEFAULT ''   COMMENT '奖励图片地址' ,
    create_by VARCHAR(16)   DEFAULT '' COMMENT '创建者' ,
    created_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_by VARCHAR(16)   DEFAULT '' COMMENT '更新者' ,
    updated_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (reward_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '奖励品表' AUTO_INCREMENT=5;

-- ----------------------------
-- 初始化-奖励品表
-- ----------------------------
insert into reward values(1 ,'纪念品' ,10 ,1 ,10 ,'','administrator' ,sysdate() ,'' ,null);
insert into reward values(2 ,'记事本' ,5 ,1 ,10 ,'','administrator' ,sysdate() ,'' ,null);

-- ----------------------------
-- 9、奖励品兑换表
-- ----------------------------
DROP TABLE IF EXISTS reward_exchange;
CREATE TABLE reward_exchange(
    id INT(32) NOT NULL AUTO_INCREMENT  COMMENT '兑换号' ,
    user_id INT(16) NOT NULL   COMMENT '用户号' ,
    reward_id INT(16) NOT NULL   COMMENT '奖励号' ,
    reward_num INT(32)    COMMENT '奖励数量' ,
    status TINYINT(1)   DEFAULT 0 COMMENT '是否交付;0为未交付，1未已交付' ,
    ingest_time DATETIME    COMMENT '获取奖励时间' ,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '奖励品兑换表' AUTO_INCREMENT=3;

-- ----------------------------
-- 建立奖励品兑换表索引
-- ----------------------------
CREATE INDEX index_rewardby ON reward_exchange(user_id);

-- ----------------------------
-- 初始化-奖励品兑换表
-- ----------------------------
insert into reward_exchange values(1 ,10002 ,1 ,1 ,0 ,sysdate());

-- ----------------------------
-- 10、成就表
-- ----------------------------
DROP TABLE IF EXISTS achievement;
CREATE TABLE achievement(
    achv_id INT(16) NOT NULL AUTO_INCREMENT  COMMENT '成就号' ,
    achv_name VARCHAR(32) NOT NULL   COMMENT '成就名称' ,
    achv_description VARCHAR(255)    COMMENT '成就描述' ,
    picture  VARCHAR(255) DEFAULT ''   COMMENT '
    ' ,
    create_by VARCHAR(16)   DEFAULT '' COMMENT '创建者' ,
    created_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_by VARCHAR(16)   DEFAULT '' COMMENT '更新者' ,
    updated_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (achv_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '成就表' AUTO_INCREMENT=3;

-- ----------------------------
-- 初始化-成就表
-- ----------------------------
insert into achievement values(1 ,'助人为乐·一' ,'完成任务数量达到5','','administrator' ,sysdate() ,'' ,null);
insert into achievement values(2 ,'助人为乐·二' ,'完成任务数量达到10','','administrator' ,sysdate() ,'' ,null);

-- ----------------------------
-- 11、成就记录表
-- ----------------------------
DROP TABLE IF EXISTS achv_record;
CREATE TABLE achv_record(
    user_id INT(16) NOT NULL   COMMENT '用户号' ,
    achv_id INT(16) NOT NULL   COMMENT '成就号' ,
    achv_time DATETIME    COMMENT '获得成就时间' ,
    PRIMARY KEY (user_id,achv_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '成就记录表';

-- ----------------------------
-- 初始化-成就记录表
-- ----------------------------
insert into achv_record values(10002 ,1,sysdate());

-- ----------------------------
-- 12、校园信息表
-- ----------------------------
DROP TABLE IF EXISTS school_info;
CREATE TABLE school_info(
    info_id INT(32) NOT NULL AUTO_INCREMENT  COMMENT '信息号' ,
    info_title VARCHAR(32) NOT NULL   COMMENT '信息标题' ,
    info_description VARCHAR(255)   DEFAULT null COMMENT '信息描述' ,
    released_by INT(16) NOT NULL   COMMENT '发布者ID' ,
    released_time DATETIME    COMMENT '信息发布时间' ,
    info_status TINYINT(1)   DEFAULT 0 COMMENT '信息状态;0为未发布，1未已发布' ,
    create_by VARCHAR(16)   DEFAULT '' COMMENT '创建者' ,
    created_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_by VARCHAR(16)   DEFAULT '' COMMENT '更新者' ,
    updated_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (info_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '校园信息表' AUTO_INCREMENT=3;

-- ----------------------------
-- 初始化-校园信息表
-- ----------------------------
insert into school_info values(1 ,'开学日期' ,'新学期开学日期为2022年9月1日' ,10001 ,sysdate(),0 ,'administrator' ,sysdate() ,'' ,null);


-- ----------------------------
-- 13、评论表
-- ----------------------------
DROP TABLE IF EXISTS review;
CREATE TABLE review(
    review_id INT(32) NOT NULL AUTO_INCREMENT  COMMENT '评论号' ,
    info_id INT(16) NOT NULL   COMMENT '信息号' ,
    user_id INT(16) NOT NULL   COMMENT '用户号' ,
    review_description VARCHAR(255) NOT NULL   COMMENT '评论内容' ,
    review_time DATETIME NOT NULL   COMMENT '评论时间' ,
    delete_status TINYINT(1)   DEFAULT 0 COMMENT '是否被删除;0为未删除，1未已删除' ,
    PRIMARY KEY (review_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '评论表' AUTO_INCREMENT=3;

-- ----------------------------
-- 初始化-评论表
-- ----------------------------
insert into review values(1 ,1 ,10002 ,'新学期，新气象' ,sysdate() ,0);

-- ----------------------------
-- 14、通知公告表
-- ----------------------------
DROP TABLE IF EXISTS notice;
CREATE TABLE notice(
    notice_id INT(16) NOT NULL AUTO_INCREMENT  COMMENT '公告号' ,
    notice_title VARCHAR(32) NOT NULL   COMMENT '公告标题' ,
    notice_content VARCHAR(16)   DEFAULT null COMMENT '公告内容' ,
    notice_status TINYINT(1)   DEFAULT 0 COMMENT '公告状态;0正常 1关闭' ,
    create_by VARCHAR(16)   DEFAULT '' COMMENT '创建者' ,
    created_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_by VARCHAR(16)   DEFAULT '' COMMENT '更新者' ,
    updated_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (notice_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '通知公告表' AUTO_INCREMENT=3 ;

-- ----------------------------
-- 初始化-通知公告表
-- ----------------------------
insert into notice values(1 ,'系统消息' ,'系统维护' ,0 ,'administrator' ,sysdate() ,'' ,null);

-- ----------------------------
-- 15、留言表
-- ----------------------------
DROP TABLE IF EXISTS message;
CREATE TABLE message(
    message_id INT(16) NOT NULL AUTO_INCREMENT  COMMENT '留言号' ,
    user_id INT(16) NOT NULL  COMMENT '发布人' ,
    type TINYINT(4)   DEFAULT 0 COMMENT '问题类型' ,
    description VARCHAR(255)   DEFAULT '' COMMENT '情况说明' ,
    contact VARCHAR(16)   DEFAULT '' COMMENT '联系方式' ,
    submit_time DATETIME    COMMENT '提交时间' ,   
    status TINYINT(1)   DEFAULT 0 COMMENT '处理状态;0待回复，1已回复' ,
    reply VARCHAR(255)   DEFAULT '' COMMENT '客服回复' ,
    reply_time DATETIME    COMMENT '客服回复时间' ,
    PRIMARY KEY (message_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '留言表';

-- ----------------------------
-- 16、文件表
-- ----------------------------

DROP TABLE IF EXISTS picture_file;
CREATE TABLE picture_file(
	id INT(16) NOT NULL AUTO_INCREMENT  COMMENT '图片ID' ,
    name VARCHAR(32)   DEFAULT NULL COMMENT '文件名称' ,
    type VARCHAR(255)   DEFAULT NULL COMMENT '文件类型' ,
    size BIGINT(32) DEFAULT 0 COMMENT '文件大小' ,
    url VARCHAR(255)   DEFAULT NULL COMMENT '链接' ,
    md5 VARCHAR(255)    COMMENT 'md5' ,
    delete_status TINYINT(1)   DEFAULT 0 COMMENT '是否被删除;0为未删除，1未已删除' ,
    enable_status TINYINT(1)   DEFAULT 1 COMMENT '是否可用;0为不可用，1可用' ,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '文件表';

-- ----------------------------
-- 17、菜单表
-- ----------------------------

DROP TABLE IF EXISTS menu;
CREATE TABLE menu(
    id INT(16) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    name VARCHAR(255)  NOT NULL  COMMENT '菜单名称',
    pid INT(16)  DEFAULT 0 COMMENT '父菜单ID',
    sort_num INT(16)  DEFAULT 0 COMMENT '显示顺序',
    path VARCHAR(255)  DEFAULT NULL COMMENT '路由地址',
    page_path VARCHAR(255)  DEFAULT NULL COMMENT '组件路径',
    icon VARCHAR(255)  DEFAULT '' COMMENT '菜单图标',
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT = '菜单表';

-- ----------------------------
-- 初始化-菜单表
-- ----------------------------

insert into menu values(1 ,'主页',NULL,100 ,'/home','Home','el-icon-s-home');
    
insert into menu values(2 ,'系统管理',NULL,200 ,NULL,NULL,'el-icon-menu');
insert into menu values(3 ,'用户管理',2,202 ,'/user','User','el-icon-user-solid');
insert into menu values(4 ,'任务管理',2,203 ,'/task','Task','el-icon-document');
insert into menu values(5 ,'信息管理',2,204 ,'/info','Info','el-icon-message');
insert into menu values(6 ,'评论管理',2,205 ,'/review','Review','el-icon-chat-line-round');
insert into menu values(7 ,'奖励管理',2,206 ,'/reward','Reward','el-icon-box');

insert into menu values(8 ,'用户中心',NULL,300 ,NULL,NULL,'el-icon-s-custom');
insert into menu values(9 ,'个人信息',8,301 ,'/person','Person','el-icon-date');

insert into menu values(10 ,'任务中心',NULL,400 ,NULL,NULL,'el-icon-s-data');
insert into menu values(11 ,'全部任务',8,401 ,'/tasklist','TaskList','el-icon-collection');
insert into menu values(12 ,'我的任务',8,402 ,'/mytask','MyTask','el-icon-files');

insert into menu values(13 ,'校园信息',NULL,500 ,'/infolist','InfoList','el-icon-menu');

insert into menu values(14 ,'奖励中心',NULL,600 ,NULL,NULL,'el-icon-present');
insert into menu values(15 ,'奖励商城',14,601 ,'rewardlist' ,'RewardList','el-icon-coin');
insert into menu values(16 ,'我的奖励',14,602 ,'myreward','MyReward','el-icon-box');