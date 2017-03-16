CREATE TABLE writinggroups(
    groupname   VARCHAR(30) NOT NULL,
    headwriter  VARCHAR(30),
    yearformed  INTEGER,
    subject     VARCHAR(30),
    CONSTRAINT writinggroups_pk  PRIMARY KEY (groupname)
);

CREATE TABLE publishers(
    publishername       VARCHAR(30) NOT NULL,
    publisheraddress    VARCHAR(30),
    publisherphone      VARCHAR(20),
    publihseremail      VARCHAR(20),
    CONSTRAINT publishers_pk  PRIMARY KEY (publishername)
);


CREATE TABLE books(
    booktitle       VARCHAR(30) NOT NULL,
    groupname   VARCHAR(30) NOT NULL,
    publishername       VARCHAR(30) NOT NULL,
    yearpublished   INTEGER,
    numberpages     INTEGER,

    CONSTRAINT books_pk PRIMARY KEY (booktitle, groupname, publishername),
    CONSTRAINT books_ibfk_1 FOREIGN KEY (groupname) REFERENCES writinggroups (groupname),
    CONSTRAINT books_ibfk_2 FOREIGN KEY (publishername) REFERENCES publishers (publishername)
);
