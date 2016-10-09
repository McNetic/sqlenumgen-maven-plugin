BEGIN TRANSACTION;
CREATE TABLE test (id integer primary key, name varchar);
INSERT INTO test VALUES(1, 'name1');
INSERT INTO test VALUES(2, 'name2');
COMMIT;