create database job;

use job;
drop table jobInfo;

create table jobInfo(
id int auto_increment,
primary key(id),
jobName varchar(30),
jobShortDescription varchar(100),
jobLocation varchar(60),
emplymentType varchar(30),
favorited boolean
);

alter table jobinfo  auto_increment = 0;

insert into jobInfo(jobName,jobShortDescription,jobLocation,emplymentType,favorited)
value ('IT','Programming with knon','Matzleinsdorferplatz','Praktikum',false);

insert into jobInfo(jobName,jobShortDescription,jobLocation,emplymentType,favorited)
value ('Banking','Kundenbetreuung','Spengergasse','Praktikum',true);

select jobName from jobinfo where jobName like 'IT';



