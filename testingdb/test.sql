--sqlite3 test.db < test.sql
.mode column
.headers on


PRAGMA foreign_keys=on;

DROP TABLE IF EXISTS Times;
DROP TABLE IF EXISTS IMAGES;

CREATE TABLE Times(
    tid INTEGER PRIMARY KEY
);


CREATE TABLE IMAGES(
    tid INTEGER,
    iid INTEGER UNIQUE NOT NULL,
    img BLOB,
    PRIMARY KEY (tid, iid),
    FOREIGN KEY (tid) REFERENCES TIMES(tid) ON DELETE CASCADE
);


INSERT INTO TIMES VALUES(1);
INSERT INTO IMAGES(tid, img) VALUES(1, "e");
INSERT INTO IMAGES(tid, img) VALUES(1, "f");

SELECT * FROM TIMES;
SELECT * FROM IMAGES;

DELETE FROM TIMES;

SELECT * FROM TIMES;
SELECT * FROM IMAGES;