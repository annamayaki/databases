/* ------------------------------------- User Creation, Privilege Grant Statements --------------------*/


/* Candidates */
create user cleogeo identified by 'upL34r';
grant all privileges on erecruit.* to cleogeo;
flush privileges;


create user zazahir23 identified by 'zoolhger';
grant all privileges on erecruit.* to zazahir23;
flush privileges;


create user lionarF identified by 'erg2378';
grant all privileges on erecruit.* to lionarF;
flush privileges;


create user liagourma identified by 'sionpass';
grant all privileges on erecruit.* to liagourma;
flush privileges;


create user mnikol identified by 'm@n0lis';
grant all privileges on erecruit.* to mnikol;
flush privileges;


create user abrown identified by 'w1lcoxon';
grant all privileges on erecruit.* to abrown;
flush privileges;


/* Recruiters */
create user msmith identified by 'we3wd';
grant all privileges on erecruit.* to msmith;
flush privileges;


create user varcon82 identified by 'gotop@s$';
grant all privileges on erecruit.* to varcon82;
flush privileges;


create user bettyg identified by 'jUn38@';
grant all privileges on erecruit.* to bettyg;
flush privileges;


create user papad identified by 'pdfr45t';
grant all privileges on erecruit.* to papad;
flush privileges;


create user n_tri identified by 'grt12wX';
grant all privileges on erecruit.* to n_tri;
flush privileges;


create user Giankost identified by 'jUn38@';
grant all privileges on erecruit.* to Giankost;
flush privileges;


create user pavkie identified by 'julie79';
grant all privileges on erecruit.* to pavkie;
flush privileges;


create user annam identified by 'Anna1234';
grant all privileges on erecruit.* to annam;
flush privileges;


create user klelial identified by '1234';
grant all privileges on erecruit.* to klelial;
flush privileges;


/* Administrator */
create user boss identified by 'imtheboss1';
grant all privileges on erecruit.* to boss;
flush privileges;


/* ------------------------------------- Triggers, Stored Procedures added for Part B -----------------*/


/* CREATE TABLE total_score */
drop procedure if exists job_scores;
delimiter $
create procedure job_scores(IN i int(4))
begin
DECLARE score float;
DECLARE finished_flag INT;
DECLARE usr VARCHAR(12);
DECLARE edu float;
DECLARE exp float;
DECLARE pers float;
DECLARE cur1 cursor for
select applies.cand_usrname, applies.education, applies.experience, AVG(interview.personality)
from interview 
left join applies on applies.job_id=interview.job and applies.cand_usrname=interview.candidate_username
where interview.job=i
group by candidate_username
order by  applies.education + applies.experience + AVG(interview.personality)  desc;
DECLARE continue handler for NOT FOUND SET finished_flag=1;
open cur1;
SET finished_flag=0;
fetch cur1 into usr,edu,exp,pers;
while (finished_flag=0) do
    if (edu is NOT NUll and exp is NOT Null and pers is NOT NUll ) then
        SET score=edu+pers+exp;
        insert into total_score(job_id,cand_id,tscore) values(i,usr,score);
    end if;
    fetch cur1 into usr,edu,exp,pers;
end while;
close cur1;
end$
delimiter ;


drop procedure if exists job_rankings;
delimiter $
create procedure job_rankings(IN i int(4))
begin
DECLARE cand_rank int(4);
DECLARE cands INT;
DECLARE finished_flag INT;
DECLARE usr VARCHAR(12);
DECLARE cur1 cursor for
select cand_id
from total_score
where job_id = i
order by tscore desc;
DECLARE continue handler for not found SET finished_flag=1;
select count(*) into cands
from job inner join applies on applies.job_id=job.id
where job.id=i;
open cur1;
SET finished_flag=0;
SET cand_rank=1;
fetch cur1 into usr;
while (finished_flag=0) do
	update total_score SET ranking=cand_rank, total_cands=cands where cand_id=usr and job_id=i;
	fetch cur1 into usr;
	SET cand_rank=cand_rank+1;
end while;
close cur1;
end $
delimiter ;


drop procedure if exists populate_scores;
delimiter $
create procedure populate_scores()
begin
DECLARE i int(4);
DECLARE num_of_jobs int;
DECLARE result varchar(25);
delete from total_score;
select count(*) into num_of_jobs from job;
SET i=1;
while (i<=num_of_jobs) do
	call job_status(i,result);
	if (result LIKE 'assessment over') then
		call job_scores(i);
		call job_rankings(i);
	end if;
	SET i= i+1;
end while;
end$
delimiter ;


call populate_scores();


/* ALTER TABLE user ADD user_type ENUM('candidate', 'recruiter', 'admin')  NOT NULL */
drop procedure if exists update_user_type;
delimiter $
create procedure update_user_type()
begin
DECLARE uname VARCHAR(12);
DECLARE finishedFlag INT;
DECLARE utype ENUM('candidate', 'recruiter','admin');
DECLARE cur cursor for
select user.username 
from user
inner join candidate on user.username=candidate.username;
DECLARE cur1 cursor for
select user.username 
from user
inner join recruiter on user.username=recruiter.username;
DECLARE continue handler for NOT FOUND SET finishedFlag=1;
SET utype='candidate';
open cur;
SET finishedFlag=0;
fetch cur into uname;
while (finishedFlag=0) do
	update user
	SET user_type=utype 
	where username=uname;
	fetch cur into uname;
end while;
close cur;
open cur1;
SET finishedFlag=0;
SET utype='recruiter';
fetch cur1 into uname;
while (finishedFlag=0) do
	update user SET user_type=utype where username=uname;
	fetch cur1 into uname;
end while;
close cur1;
end$
delimiter ;


call update_user_type();