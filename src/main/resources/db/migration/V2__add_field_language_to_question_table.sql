alter table question
add language varchar(45) DEFAULT NULL;
update question
set language = 'POLISH';