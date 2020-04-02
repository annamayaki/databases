/* ------------------------------------- Queries (Question 3) ---------------------------------------- */


/* 3.a */
select `user`.`name`, `user`.surname, etaireia.name, job.id, job.salary, count(*) as 'applications' 
from job 
inner join recruiter on job.recruiter=recruiter.username
inner join `user` on `user`.username=recruiter.username
inner join applies on job.id=applies.job_id
inner join etaireia on recruiter.firm=etaireia.AFM
where job.salary>1900
group by applies.job_id;


/* 3.b */
select candidate.username, candidate.certificates, count(*) as 'degrees', AVG(has_degree.grade) as 'average grade'
from candidate
inner join has_degree on candidate.username=has_degree.cand_usrname
group by has_degree.cand_usrname having count(*)>1; 


/* 3.c */
select candidate.username, count(*) as 'applications', AVG(job.salary) as 'average salary'
from candidate 
inner join applies on candidate.username=applies.cand_usrname
inner join job on applies.job_id=job.id
group by candidate.username
having AVG(job.salary)>1800;


/* 3.d */
select etaireia.name, job.position, antikeim.title
from job
inner join requires on job.id=requires.job_id
inner join antikeim on requires.antikeim_title=antikeim.title
inner join recruiter on job.recruiter=recruiter.username
inner join etaireia on recruiter.firm=etaireia.AFM
where job.edra='Patra, Greece' and antikeim.title like '%Program%';


/* 3.e */
select recruiter.username, count(job.id) as 'annouced jobs', count(interview.start_time) as 'interviews given', AVG(job.salary) as 'average salary'
from job
inner join  recruiter on recruiter.username=job.recruiter
left join interview on job.id=interview.job
group by job.recruiter having count(job.id)>2
order by AVG(job.salary) desc;


/* ------------------------------------- Triggers, Stored Procedures (Question 4) ---------------------*/


/* 4.a */

/* ALTER TABLE job ADD applicants int(5) -> helper column */
drop procedure if exists populate_applicants;
delimiter $
create procedure populate_applicants()
	begin
		DECLARE i int(4);
		DECLARE j int(4);
		DECLARE cands int(5);
		select count(*) into i from job;
		SET j=1;
		while (j<=i) do
			select count(*) into cands from applies
			inner join job on applies.job_id=job.id
			where job.id=j
			group by job.id;
			update job SET applicants=cands where id=j;
			SET j=j+1;
			do SLEEP(1);
		end while;
	end$
delimiter ;


call populate_applicants();

/* Triggers for applicants */
drop trigger if exists i_applicants;
delimiter $
create trigger i_applicants
	after insert on applies
	for each row
	begin
		DECLARE i int(4);
		DECLARE cands int(5);
		SET i=NEW.job_id;
		select count(*) into cands from applies
		inner join job on applies.job_id=job.id
		where job.id=i
		group by job.id;
		update job SET applicants=cands where id=i;
		DO SLEEP(1);
	end$
delimiter ;


drop trigger if exists d_applicants;
delimiter $
create trigger d_applicants
	after delete on applies
	for each row
	begin
		DECLARE i int(4);
		DECLARE cands int(5);
		SET i=OLD.job_id;
		select count(*) into cands from applies
		inner join job on applies.job_id=job.id
		where job.id=i
		group by job.id;
		update job SET applicants=cands where id=i;
		DO SLEEP(1);
	end$
delimiter ;


/* Helper procedure */
drop procedure if exists job_status;
delimiter $
create procedure job_status(IN i INT(4),INOUT status VARCHAR(25))
begin
DECLARE sub_date DATE;
DECLARE interviews INT;
DECLARE candidates INT;
DECLARE other_scores INT;
select submission_date INTO sub_date from job where id=i;
if (CURRENT_DATE()<sub_date) THEN
	SET status = 'open for submissions';
else
	select applicants INTO candidates from job where id=i;
	select COUNT(DISTINCT candidate_username) INTO interviews 
		from interview where job=i AND personality IS NOT NULL;
	select COUNT(*) INTO other_scores from applies
    	where job_id=i 
    	AND experience IS NOT NULL
    	AND education IS NOT NULL;
	if (interviews=candidates and candidates=other_scores) then
		SET status = 'assessment over';
	else 
		SET status = 'under assessment (closed)';
	end if;
end if;
end$
delimiter ;


/* Actual procedure */
drop procedure if exists job_assessment;
delimiter $
create procedure job_assessment (IN id INT(4))
BEGIN
DECLARE sum1 float;
DECLARE finished_flag INT;
DECLARE usr VARCHAR(12);
DECLARE edu float;
DECLARE exp float;
DECLARE pers float;
DECLARE inter float;
DECLARE rejection VARCHAR(80);
DECLARE result VARCHAR(25);
DECLARE cursor1 cursor for
	select applies.cand_usrname, applies.education, applies.experience, AVG(interview.personality), count(interview.start_time) 
	from interview 
	left join applies on applies.job_id=interview.job and applies.cand_usrname=interview.candidate_username
	where interview.job=id
	group by candidate_username
    order by  applies.education + applies.experience + AVG(interview.personality)  desc;
DECLARE cursor2 cursor for
	select applies.cand_usrname, applies.education, applies.experience, AVG(interview.personality)
	from interview 
	left join applies on applies.job_id=interview.job and applies.cand_usrname=interview.candidate_username
	where interview.job=id
	group by candidate_username
	order by  candidate_username;
DECLARE continue handler for NOT FOUND SET finished_flag=1;
call job_status(id,result);
select result;
if (result LIKE 'assessment over') then
	open cursor1;
	SET finished_flag=0;
	fetch cursor1 INTO usr,edu,exp,pers,inter;
		while (finished_flag=0) do 
			if (edu>0 and exp>0 and pers>0) then
				SET sum1=edu+pers+exp;
				select  usr as 'candidate',edu as 'education',exp as 'experience',pers as 'personality',sum1 as 'total score',inter as 'interviews';
			end if;
			fetch cursor1 INTO usr,edu,exp,pers,inter;
		end while;
		close cursor1;
		select('Rejected Candidates');
        open cursor2;
		SET finished_flag=0;
        fetch cursor2 INTO usr,edu,exp,pers;
        while (finished_flag=0) do 
			if (edu=0 or exp=0 or pers=0) then
				SET rejection='';
				if (edu=0) then
				 	SET rejection='inadequate education';
				end if;
				if (exp=0) then 
					if (rejection='') then
						SET rejection='no prior experience';
					else
						SET rejection=concat_ws(" - ",rejection, 'no prior experience');
					end if;
				end if;
				if (pers=0) then
					if (rejection='') then
						SET rejection='failed the interview';
					else
						SET rejection=concat_ws(" - ",rejection, 'failed the interview');
					end if;
				end if;
				select usr as 'Candidate', rejection as 'Reason(s)';
			end if;
            fetch cursor2 INTO usr,edu,exp,pers;
		end while;
		close cursor2;
end if;
end$
delimiter ;


/* 4.b */
create procedure action_update(IN tbl VARCHAR(100), IN act_tp ENUM('insert','delete','update'), IN suc ENUM('successful','unsuccessful'))
begin
DECLARE usr VARCHAR(40);
DECLARE time_stamp TIMESTAMP;
DECLARE pos INT;
SET usr=session_user();
SET pos=locate("@",usr)-1;
SET usr=mid(usr,1,pos);
SET time_stamp=current_timestamp();
insert into action values
(tbl,act_tp,time_stamp,suc,usr);
end$
delimiter ;

drop procedure if exists  action_success;
delimiter $
create procedure action_success(out priv ENUM('successful','unsuccessful') )
begin
if (row_count()>0) then
SET priv='successful';
else
SET priv='unsuccessful';
end if;
end$
delimiter ;


drop trigger if exists i_candidate ;
delimiter $
create trigger i_candidate
after insert on candidate
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
if (count=0) then
	call action_success(result);
	call action_update('candidate','insert',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists u_candidate ;
delimiter $
create trigger u_candidate
after update on candidate
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('candidate','update',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists d_candidate ;
delimiter $
create trigger d_candidate
after delete on candidate
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('candidate','delete',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists i_recruiter ;
delimiter $
create trigger i_recruiter
after insert on recruiter
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('recruiter','insert',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists u_recruiter ;
delimiter $
create trigger u_recruiter
after update on recruiter
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('recruiter','update',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists d_recruiter ;
delimiter $
create trigger d_recruiter
after delete on recruiter
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('recruiter','delete',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists i_user ;
delimiter $
create trigger i_user
after insert on user
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('user','insert',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists u_user ;
delimiter $
create trigger u_user
after update on user
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('user','update',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists d_user ;
delimiter $
create trigger d_user
after delete on user
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('user','delete',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists i_job ;
delimiter $
create trigger i_job
after insert on job
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('job','insert',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists u_job ;
delimiter $
create trigger u_job
after update on job
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('job','update',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists d_job ;
delimiter $
create trigger d_job
after delete on job
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('job','delete',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists i_etaireia ;
delimiter $
create trigger i_etaireia
after insert on etaireia
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('etaireia','insert',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists d_etaireia ;
delimiter $
create trigger d_etaireia
after delete on etaireia
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('etaireia','delete',result);
    SET count=1;
end if;
end$
delimiter ;


drop trigger if exists u_etaireia ;
delimiter $
create trigger u_etaireia
before update on etaireia
for each row
begin
DECLARE result  ENUM('successful','unsuccessful');
DECLARE count INT;
SET count=0;
SET new.AFM=old.AFM;
SET new.DOY=old.DOY;
SET new.name=old.name;
call action_success(result);
if (count=0) then
	call action_success(result);
	call action_update('etaireia','update',result);
    SET count=1;
end if;
end$
delimiter ;


/* 4.c */
drop trigger if exists d_applies ;
delimiter $
create trigger d_applies
before delete on applies
for each row
begin
DECLARE deadline date;
DECLARE cur_date date;
DECLARE count INT;
SET cur_date=current_date();
select distinct submission_date into deadline
from job 
inner join applies on job.id=applies.job_id
where old.job_id=job.id;
if (deadline<cur_date) then 
	signal SQLSTATE '45000'
SET message_text ='submissions closed for this position';
end if;
end$
delimiter ;
