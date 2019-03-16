DROP TABLE IF EXISTS question;
CREATE TABLE question(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  question_body varchar(200) NOT NULL,
  category varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
);
INSERT INTO question (question_body, category) VALUES
('Przetłumacz: orange','ENGLISH'),
('Przetłumacz: cat','ENGLISH'),
('Przetłumacz: winter','ENGLISH'),
('Oblicz: 3*4','MULTIPLICATION'),
('Oblicz: 4*5','MULTIPLICATION');

DROP TABLE IF EXISTS answer;
CREATE TABLE answer (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  answer_body varchar(200) NOT NULL,
  is_correct tinyint(1) DEFAULT 0,
  question_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_question_answer FOREIGN KEY (question_id) REFERENCES question (id)
);

INSERT INTO answer (answer_body, is_correct, question_id) VALUES
('pomarańczowy',1,1),
('zielony',0,1),
('czerwony',0,1),
('żółty',0,1),
('kot',1,2),
('pies',0,2),
('ryba',0,2),
('żółw',0,2),
('zima',1,3),
('lato',0,3),
('wiosna',0,3),
('jesień',0,3),
('12',1,4),
('13',0,4),
('20',1,5),
('18',0,5);
