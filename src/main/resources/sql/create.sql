CREATE TABLE TEST(
      ID INT PRIMARY KEY
    , NAME VARCHAR(255)
    );

CREATE TABLE USER(
      IDX INT PRIMARY KEY
    , ID VARCHAR2(255)
    , PASSWD VARCHAR(255)
    , NAME VARCHAR2(255)
    , TYPE VARCHAR2(255)
    );

ALTER TABLE USER 
  ADD CONSTRAINT USER_UNIQUE UNIQUE(ID, TYPE);

    
