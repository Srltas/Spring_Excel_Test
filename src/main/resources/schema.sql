DROP TABLE IF EXISTS sample CASCADE;
DROP TABLE IF EXISTS work_time CASCADE;

CREATE TABLE sample
(
    seq                 bigint      NOT NULL AUTO_INCREMENT,        -- PK
    `date`              date        NOT NULL,                       -- 날짜(년-월-일)
    `name`              varchar(10) NOT NULL,                       -- 이름
    begin_work          int         NOT NULL,                       -- 출근 시간
    end_work            int         NOT NULL,                       -- 퇴근 시간
    total_work          int         NOT NULL,                       -- 근로 시간
    night_work          int         NOT NULL,                       -- 야근 근로 시간
    holiday_work        int         NOT NULL,                       -- 휴일 근로 시간
    `leave`             int         NOT NULL,                       -- 휴가
    holiday_check       char(1)     NOT NULL,                       -- 휴일 체크(O or X)
    PRIMARY KEY (seq)
);

CREATE TABLE total_legal_work_time
(
    seq                 bigint      NOT NULL AUTO_INCREMENT,        -- PK
    `date`              date        NOT NULL,                       -- 날짜(년-월-1)
    quarter             char(1)     NOT NULL,                       -- 분기
    total_work_time     int         NOT NULL,                       -- 총 근무시간
    legal_work_time     int         NOT NULL,                       -- 법정 근로시간
    PRIMARY KEY (seq)
);

CREATE TABLE users
(
    seq                 bigint      NOT NULL AUTO_INCREMENT,        -- PK
    `name`              varchar(50) NOT NULL,                       -- 이름
    PRIMARY KEY (seq)
);

CREATE TABLE holiday_calc
(
    seq                 bigint      NOT NULL AUTO_INCREMENT,        -- PK
    `date`              date        NOT NULL,                       -- 날짜(년-월-일)
    `name`              varchar     NOT NULL,                       -- 이름
    holiday_holiday     int         NOT NULL,                       -- 공휴일, 공휴일
    holiday_weekday     int         NOT NULL,                       -- 공휴일, 평일
    weekday_holiday     int         NOT NULL,                       -- 평일, 공휴일
    holiday_8H_Over     int         NOT NULL,                       -- 공휴일 8시간 초과
    PRIMARY KEY (seq)
);