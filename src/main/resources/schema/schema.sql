DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user
(
    id    bigint      NOT NULL,
    name  VARCHAR(30) NULL,
    age   int         NULL,
    email VARCHAR(50) NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO user (id, name, age, email)
VALUES (1, 'Jone', 18, 'test1@qq.com'),
       (2, 'Jack', 20, 'test2@qq.com'),
       (3, 'Tom', 28, 'test3@qq.com'),
       (4, 'Sandy', 21, 'test4@qq.com'),
       (5, 'Billie', 24, 'test5@qq.com');


DROP TABLE IF EXISTS area;
CREATE TABLE IF NOT EXISTS area
(
    id        bigint      NOT NULL,
    area_name VARCHAR(30) NULL,
    PRIMARY KEY (`id`)
);


INSERT INTO area (id, area_name)
VALUES (1, 'areaOne'),
       (2, 'areaTwo'),
       (3, 'areaThree');

