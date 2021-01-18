-- used in tests that use HSQL
create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONGVARBINARY,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication LONGVARBINARY
);

create table oauth_code (
  code VARCHAR(256), authentication LONGVARBINARY
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
  appId VARCHAR(256) PRIMARY KEY,
  resourceIds VARCHAR(256),
  appSecret VARCHAR(256),
  scope VARCHAR(256),
  grantTypes VARCHAR(256),
  redirectUrl VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(256)
);

-- - autio generated quert -----------------------------------------------------------------------------
-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- user Table Create SQL
CREATE TABLE IF NOT EXISTS `user`
(
    `userIdx`      INT              NOT NULL    AUTO_INCREMENT COMMENT '유저 인덱스',
    `userKakaoID`  INT      NOT NULL    COMMENT '유저 카카오 아이디',
    `userName`        VARCHAR(64)               NOT NULL    COMMENT '유저 이름',
    `userImage`    VARCHAR(1024)    NULL    COMMENT '유저 이미지',
    `userEmail`    VARCHAR(64)    NOT NULL    COMMENT '유저 이메일',
    `provider`    VARCHAR(64)    NOT NULL    COMMENT '유저 제공자',
    PRIMARY KEY (userIdx)
);

CREATE INDEX user_Index_1 ON `user`
(
    userKakaoID
);

-- ALTER TABLE user COMMENT 'user테이블';


-- user Table Create SQL
-- CREATE TABLE IF NOT EXISTS todo
-- (
--     `userId`     INT              NOT NULL    COMMENT '유저 인덱스',
--     `todoIdx`      INT              NOT NULL   AUTO_INCREMENT  COMMENT '목표 인덱스',
--     `todoTitle`    VARCHAR(45)      NOT NULL    COMMENT '목표이름',
--     `todoContent`  VARCHAR(1024)    NOT NULL    COMMENT '목표내용',
--     `todoDate`     DATE             NOT NULL    COMMENT '목표날짜',
--     `repeatableYN`    TINYINT(1)       NULL    COMMENT '반복유무',
--     `repeatUnit`      TINYINT(1)       NOT NULL    COMMENT '반복단위',
--     `startDate`       DATE             NOT NULL    COMMENT '시작날짜',
--     `endDate`         DATE             NOT NULL    COMMENT '끝날짜',
--     `weekDay`         VARCHAR(10)      NOT NULL    COMMENT '주단위 요일',
--     `monthDay`        INT              NOT NULL    COMMENT '월단위 일자',
--     `finish`          TINYINT(1)          NOT NULL    COMMENT '완료유무',
--     PRIMARY KEY (userId, todoIdx)
-- );
--
-- alter table todo alter column repeatableYN TINYINT(1);
--
-- -- ALTER TABLE todo COMMENT '목표테이블';
--
-- CREATE INDEX todo_Index_2 ON todo
-- (
--     endDate
-- );
--
-- CREATE INDEX todo_Index_1 ON todo
-- (
--     startDate
-- );
--
-- -- ALTER TABLE todo
-- --     ADD CONSTRAINT FK_todo_userIdx_user_userIdx FOREIGN KEY (userIdx)
-- --         REFERENCES user (userIdx) ON DELETE RESTRICT ON UPDATE RESTRICT;
--
--
-- -- user Table Create SQL
-- CREATE TABLE IF NOT EXISTS review
-- (
--     `userId`        INT            NOT NULL    COMMENT '유저 인덱스',
--     `todoDate`    DATE           NOT NULL    COMMENT '목표날짜',
--     `reviewContent`  VARCHAR(45)    NOT NULL    COMMENT '회고내용',
--     `emoticon`       INT            NOT NULL    COMMENT '표정이모티콘',
--
--     PRIMARY KEY (userId, todoDate)
-- );
--
-- -- ALTER TABLE review COMMENT '회고테이블';
--
-- -- ALTER TABLE review
-- --     ADD CONSTRAINT FK_review_todoDate_todo_todoDate FOREIGN KEY (todoDate, userIdx)
-- --         REFERENCES todo (todoDate, userIdx) ON DELETE RESTRICT ON UPDATE RESTRICT;
--
--
-- -- user Table Create SQL
-- CREATE TABLE IF NOT EXISTS seting
-- (
--     `userId`   INT           NOT NULL    COMMENT '유저 인덱스',
--     `stasYN`    TINYINT(1)    NOT NULL    COMMENT '통계 여부',
--     `achiveYN`  TINYINT(1)    NOT NULL    COMMENT '성공통계 여부',
--     `failYN`    TINYINT(1)    NOT NULL    COMMENT '실패 통계 여부',
--     PRIMARY KEY (userId)
-- );
--
-- -- ALTER TABLE seting COMMENT '사용자 설정값';
--
-- -- ALTER TABLE seting
-- --     ADD CONSTRAINT FK_seting_userIdx_user_userIdx FOREIGN KEY (userIdx)
-- --         REFERENCES user (userIdx) ON DELETE RESTRICT ON UPDATE RESTRICT;
--
--
-- -- user Table Create SQL
-- CREATE TABLE IF NOT EXISTS statistics
-- (
--     `userId`    INT     NOT NULL    COMMENT '유저 인덱스',
--     `statisIdx`  INT     NOT NULL  AUTO_INCREMENT  COMMENT '통계인덱스',
--     `regDate`    DATE    NOT NULL    COMMENT '통계날짜',
--     `통계값`        INT     NOT NULL    COMMENT 'endValue',
--     PRIMARY KEY (userId)
-- );
--
-- -- ALTER TABLE statistics COMMENT '통계그래프';
--
-- CREATE INDEX statistics_Index_1 ON statistics
-- (
--     regDate
-- );
--
-- -- ALTER TABLE statistics
-- --     ADD CONSTRAINT FK_statistics_userIdx_user_userIdx FOREIGN KEY (userIdx)
-- --         REFERENCES user (userIdx) ON DELETE RESTRICT ON UPDATE RESTRICT;
--
--
-- -- user Table Create SQL
-- CREATE TABLE IF NOT EXISTS emoticon_info
-- (
--     `id`           INT            NOT NULL    COMMENT '표정이모티콘id',
--     `description`  VARCHAR(45)    NULL        COMMENT '이모티콘설명',
--     PRIMARY KEY (id)
-- );
--
-- -- ALTER TABLE emoticon_info
-- --     ADD CONSTRAINT FK_emoticon_info_id_review_emoticon FOREIGN KEY (id)
-- --         REFERENCES review (emoticon) ON DELETE RESTRICT ON UPDATE RESTRICT;

CREATE TABLE board_level
(
  seq int(11) PRIMARY KEY,
  maps varchar('1024'),
  width int(11),
  height int(11)
);

INSERT INTO board_level(seq, maps, width, height)
VALUES
 (0, '1,1,0,1,1,1,1,0,1,1', 5, 2);
-- new String[][]{{"1", "1", "0", "1", "1"},
--                {"1", "1", "0", "1", "1"}
-- DROP TABLE board_level;

-- SELECT * FROM board_level;

INSERT INTO board_level(seq, maps, width, height)
VALUES
 (1, '1,1,0,1,1,1,1,0,1,1,0,0,0,0,0,1,1,0,1,1,1,1,0,1,1', 5, 5);

 INSERT INTO board_level(seq, maps, width, height)
VALUES
 (-1, '0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', 5, 5);
 INSERT INTO board_level(seq, maps, width, height) VALUES (3, '0,0,0,0,0,0,1,1,1,0,0,1,1,1,0,0,1,1,1,0,0,0,0,0,0', 5, 5);
INSERT INTO board_level(seq, maps, width, height) VALUES (4, '1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0', 5, 5);
INSERT INTO board_level(seq, maps, width, height) VALUES (2, '0,0,0,0,0,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,0,0,0,0,0', 5, 5);
INSERT INTO board_level(seq, maps, width, height) VALUES (5, '1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0', 5, 5);

INSERT INTO board_level(seq, maps, width, height) VALUES (6, '1,0,0,0,1,1,0,0,0,1,0,1,1,1,0,1,0,1,0,1,1,0,1,0,1', 5, 5);
INSERT INTO board_level(seq, maps, width, height) VALUES (7, '0,0,1,0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0,1,0,0', 5, 5);

INSERT INTO board_level(seq, maps, width, height)
VALUES
 (-2, '0,0,0,0,0,0,0,' ||
      '0,0,0,0,0,0,0,' ||
      '0,0,0,0,0,0,0,' ||
      '0,0,0,0,0,0,0,' ||
      '0,0,0,0,0,0,0,' ||
      '0,0,0,0,0,0,0,' ||
      '0,0,0,0,0,0,0', 7, 7);

      INSERT INTO board_level(seq, maps, width, height) VALUES (-3, '0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,0,0,1,1,1,1,1,0,0,1,1,1,1,1,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0', 7, 7);

      INSERT INTO board_level(seq, maps, width, height) VALUES (8, '1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1', 7, 7);

      INSERT INTO board_level(seq, maps, width, height) VALUES (9, '1,1,0,0,0,1,1,1,0,1,0,1,0,1,0,1,0,1,0,1,0,0,1,1,0,1,1,0,0,1,0,1,0,1,0,1,0,0,0,0,0,1,1,0,1,1,1,0,1', 7, 7);

      INSERT INTO board_level(seq, maps, width, height) VALUES (10, '1,1,0,0,0,1,1,0,0,1,0,1,0,0,1,0,0,0,0,0,1,1,0,1,0,1,0,1,1,0,0,0,0,0,1,0,0,1,0,1,0,0,1,1,0,0,0,1,1', 7, 7);

      INSERT INTO board_level(seq, maps, width, height) VALUES (11, '1,1,0,1,0,1,1,0,0,1,1,1,0,0,1,1,1,0,1,1,1,0,0,1,1,1,0,0,1,1,1,0,1,1,1,0,0,1,1,1,0,0,1,1,0,1,0,1,1', 7, 7);

