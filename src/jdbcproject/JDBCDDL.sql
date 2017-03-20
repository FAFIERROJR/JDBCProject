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
    publisherphone      CHAR(10),
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
('Happy Gilmore-reading ', 'Adam Sandler', 1996, 'Comedy'),
('Silence of the books', 'Anthony Hopkins', 1995, 'Horror'),
('All you need is a book', 'John Lennon', 1966, 'Love'),
('Star Books', 'Luke skyreader', 1970, 'Sci-fi'),
('Lord of the books', 'Frodo Baggins',2001, 'Sci-fi' ),
('The GoodReader', 'Don Vito Corleone', 1972, 'Drama'),
('The Matrix Books', 'Neo', 1999, 'Action'),
('Sleepless in the library', 'Tom Hanks', 1993, 'Romantic-Comedy'),
('Hook on a Book', 'Robin Williams', 1991, 'Fantasy');
INSERT INTO publishers
VALUES ('Pearson', '666 Monopoly Rd', '8668427428', 'aimswebsupport@pearson.com'),
('Penguin Random House', '1734 Broadway Blvd', '8007333000', 'atrandompublicity@randomhouse.com'),
('Anchor', '1234 Dahlia Blvd', '8993526767','anchor1234@gmail.com'   ),
('Chronicle Books', '195 Broadway Blvd', '8002427737', 'chroicleBooks@hotmail.com'),
('Marvel', '283 Marvel Dr ', '8984443355', 'marvel@gmail.com'),
('Houghton Mifflin Harcourt', '288 Mifflin Blvd', '3234559888', 'harcourt288@hotmail.com'),
('Berkley', '888 Berkely Rd', '6768889898', 'berkeley888@gmail.com'),
('British Film Institute', '917 British Blvd', '7869994747', 'british917@yahoo.com'),
('Marco Polo Press', '555 Press Rd', '4559998888', 'marcopolo@yahoo.com'),
('Ballantine Books', '676 Ballantine Rd', '3438889999', 'ballantine@gmail.com');



INSERT INTO books
VALUES ('Harry Potter and the Chamber of Secrets', 'Rowling et al', 'Pearson', 2000, 630 ),
('The Novelization of Billy Madison', 'Happy Gilmore-reading', 'Penguin Random House', 2002, 420 ),
('The Shinning', 'Silence of the books', 'Anchor', 2012,688),
('The Beatles Anthology', 'All you need is a book', 'Chronicle Books', 2000, 368),
('Star Wars-A New Hope', 'Star Books', 'Marvel', 2015, 128),
('The Hobbit', 'Lord of the books','Houghton Mifflin Harcourt', 2012, 300),
('The Godfather', 'The GoodReader', 'Berkley', 2005, 468),
('The Matrix ','The Matrix Books', 'British Film Institute', 2007, 96),
('Sleepless in Seatle', 'Sleepless in the library','Marco Polo Press', 2016, 360),
('Hook', 'Hook on a Book', 'Ballantine Books', 1991, 111);
