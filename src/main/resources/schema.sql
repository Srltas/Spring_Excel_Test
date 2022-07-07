DROP TABLE IF EXISTS sample CASCADE;
DROP TABLE IF EXISTS total_legal_work_time CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS holiday_calc CASCADE;

CREATE TABLE total_user_data                                         -- 사용자 종합 시간 정보
(
    seq                  bigint      NOT NULL AUTO_INCREMENT,        -- PK
    --`date`               date        NOT NULL,                       -- 날짜(년-월-일)
    quarter              char(1)     NOT NULL,                       -- 분기
    `name`               varchar(10) NOT NULL,                       -- 이름
    quarter_work         int         DEFAULT 0,                      -- 분기 근로시간 (내가 한 분기에 일한 시간??)
    prescribed_over_work int         DEFAULT 0,                      -- 소정근로 연장시간 (일한 시간 > 분기 근로 시간 -> 일한 시간 - 법정 근로 시간 - 분기 근로 시간)
    legal_over_work      int         DEFAULT 0,                      -- 법정근로 연장시간 (일한 시간 - 휴가 > 법정 근로 시간 -> 일한 시간 - 휴가 - 법정 근로 시간)
    night_work           int         DEFAULT 0,                      -- 야근 근로시간
    holiday_work         int         DEFAULT 0,                      -- 휴일 근로시간
    holiday_8H_over      int         DEFAULT 0,                      -- 공휴일 8시간 초과
    `leave`              int         NOT NULL,                       -- 사용한 휴가
    compensation_leave   int         DEFAULT 0,                      -- 보상 휴가 시간 = 소정근로 연장시간 * 1
    quarter_money        int         DEFAULT 0,                      -- 법정근로 연장시간(분 제외) * 1.5 + 야간 근로시간(분 포함) * 0.5 + 휴일 근로시간(분 포함) * 0.5 + 공휴일 8시간 초과(분 포함) * 0.5 = 총 계산 값(분 제외)
    quarter_total        int         DEFAULT 0,                      -- compensation_leave + quarter_money
    PRIMARY KEY (seq)
);

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
    holiday_8H_over     int         NOT NULL,                       -- 공휴일 8시간 초과
    PRIMARY KEY (seq)
);