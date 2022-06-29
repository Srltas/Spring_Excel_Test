DROP TABLE IF EXISTS sample CASCADE;

CREATE TABLE sample
(
    seq                 bigint      NOT NULL AUTO_INCREMENT,        -- PK
    `date`              date        NOT NULL,                       -- 날짜
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