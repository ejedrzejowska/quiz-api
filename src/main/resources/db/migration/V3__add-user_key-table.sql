DROP TABLE IF EXISTS user_key;
CREATE TABLE user_key(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  api_key varchar(60) NOT NULL UNIQUE,
  api_user varchar(30) NOT NULL,
  is_active tinyint(1) DEFAULT 0,
  role varchar(15) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_user_key_key
ON user_key (api_key);

CREATE INDEX idx_answer_question_id
  ON answer (question_id);

CREATE INDEX idx_question_id_category
  ON question (category);

CREATE INDEX idx_question_id_language
  ON question (language);