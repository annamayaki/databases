use erecruit;


/* --------------------------------- Tables added/altered for part B -------------------------------------------- */


CREATE TABLE total_score(
job_id INT(4) NOT NULL,
cand_id VARCHAR(12) NOT NULL,
tscore FLOAT,
ranking INT(4),
total_cands INT,
PRIMARY KEY (job_id, cand_id),
CONSTRAINT score_cand FOREIGN KEY (cand_id) REFERENCES candidate (username) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT score_job FOREIGN KEY (job_id) REFERENCES job (id) ON DELETE CASCADE ON UPDATE CASCADE
)engine=InnoDB;

ALTER TABLE user ADD user_type ENUM('candidate', 'recruiter', 'admin')  NOT NULL;


/* --------------------------------- Inserts/Updates to tables for part 2 --------------------------------------- */


/* Administrator */
insert into `user` (username, `password`, `name`, surname, reg_date, email, user_type) values
('boss','imtheboss1','Mr','Boss',NOW(),'thebosshere@gmail.com','admin');