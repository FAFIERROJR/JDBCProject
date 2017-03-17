CREATE TABLE writinggroups(
    groupname   VARCHAR(50) NOT NULL,
    headwriter  VARCHAR(50),
    yearformed  INTEGER,
    subject     VARCHAR(50),
    CONSTRAINT writinggroups_pk  PRIMARY KEY (groupname)
);

CREATE TABLE publishers(
    publishername       VARCHAR(50) NOT NULL,
    publisheraddress    VARCHAR(50),
    publisherphone      CHAR(12),
    publisheremail      VARCHAR(50),
    CONSTRAINT publishers_pk  PRIMARY KEY (publishername)
);


CREATE TABLE books(
    booktitle       VARCHAR(50) NOT NULL,
    groupname       VARCHAR(50) NOT NULL REFERENCES writinggroups(groupname),
    publishername   VARCHAR(50) NOT NULL REFERENCES publishers(publishername),
    yearpublished   INTEGER,
    numberpages     INTEGER,

    CONSTRAINT books_pk PRIMARY KEY (booktitle, groupname, publishername),
    CONSTRAINT books_ibfk_1 FOREIGN KEY (groupname) REFERENCES writinggroups (groupname),
    CONSTRAINT books_ibfk_2 FOREIGN KEY (publishername) REFERENCES publishers (publishername)
);


INSERT INTO writinggroups
VALUES ('Rowling et al', 'JK Rowling', 1996, 'Fantasy'),
('Happy Gilmore', 'Adam Sandler', 1996, 'Comedy');

INSERT INTO publishers
VALUES ('Pearson', '666 Monopoly Rd', '866-842-7428', 'aimswebsupport@pearson.com'),
('Penguin Random House', '1734 Broadway Blvd', '800-733-3000', 'atrandompublicity@randomhouse.com');

INSERT INTO books
VALUES ('Harry Potter and the Chamber of Secrets', 'Rowling et al', 'Pearson', 2000, 630 ),
('The Novelization of Billy Madison', 'Happy Gilmore', 'Penguin Random House', 2002, 420 );