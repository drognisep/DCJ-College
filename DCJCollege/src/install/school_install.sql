drop user school cascade;
create user school identified by school;
grant dba to school;


----students----

CREATE TABLE school.students(
     student_id varchar2(7)  constraint stud_stdid_pk PRIMARY KEY,
     last_name  varchar2(15) not null,
     first_name varchar2(15) not null,
     class      varchar2(10) not null,
     phone      number(10)   not null, -- !!!No dashes!!!
     street     varchar2(15) not null,
     city       varchar2(15) not null,
     state      varchar2(2)  not null,
     zip        number(5)    not null,
     degree     varchar2(15) not null,
     password   varchar2(64) default 0000,
	 fees_paid  number(5)  	 not null
);


     

----departments----
     
CREATE TABLE school.departments(
     dept_id   number(2)    constraint dept_deptid_pk primary key,
     dept_name varchar2(30) not null,
     college   varchar(30)  not null
);


     


     
---instructors----

CREATE TABLE school.instructors(
     last_name  varchar2(15) not null,
     first_name varchar2(15) not null,
     dept_id    number(2)    constraint instruct_deptid_fk references school.departments(dept_id) on delete set null,
     office     number(5)    not null,
     email      varchar2(20) not null,
     phone      number(10)   not null, -- !!!No dashes!!!
     instr_id   varchar2(6)  constraint instruct_intrid_pk PRIMARY KEY,
     password   varchar2(64) default 0000
);
     

    
---courses----

CREATE TABLE school.courses(
     course_id   varchar2(3)  constraint crs_crsid_pk PRIMARY KEY,
     course_name varchar2(30) not null,
     hours       number(1)    default 3,
     dept_id     number(2)    constraint courses_deptid_fk references school.departments(dept_id) on delete set null,
     fees        number(5)    default 300
);
     

   
---schedule---

CREATE TABLE school.schedule(
     schedule_id number(2) constraint sched_schedid_pk PRIMARY KEY,
     mon_start   number(4) default 0,
     mon_end     number(4) default 0,
     tues_start  number(4) default 0,
     tues_end    number(4) default 0,
     wed_start   number(4) default 0,
     wed_end     number(4) default 0,
     thur_start  number(4) default 0,
     thur_end    number(4) default 0,
     fri_start   number(4) default 0,
     fri_end     number(4) default 0
);
     

     



---sections---

CREATE TABLE school.sections(
     term        number(5)   not null,
     section_id  varchar2(4) constraint sections_sectid_pk PRIMARY KEY,
     course_id   varchar2(3) constraint sections_courseno_fk references school.courses(course_id) on delete set null,
     room        number(5)   not null,
     schedule_id number(2)   constraint sections_schedid_fk references school.schedule(schedule_id)on delete set null,
     instr_id    varchar2(6) constraint sections_instrid_fk references school.instructors(instr_id)on delete set null,
     capacity    number(2)   default 25,
     no_enrolled number(2)   default 0
);
     




     
---enrollment----

CREATE TABLE school.enrollment(
     student_id varchar2(7) constraint enroll_studid_fk references school.students(student_id)on delete set null,
	 course_id varchar2(3) constraint enroll_courseid_fk references school.courses(course_id)on delete set null,
     section_id varchar2(4) constraint enroll_sectid_fk references school.sections(section_id)on delete set null,
     grade      varchar2(2)   default null,
     hours      number(1)   default 3,
	 term		number(5)	not null 
);
     

     

     


---insert into students----

Insert into school.students values('s111111', 'Nett'      , 'Chelsea' , 'junior'   , 6605412405, 'Lincoln'   , 'Blue Springs' ,'MO', 64014, 'Chemistry'     , 's111111', 0);
Insert into school.students values('s222222', 'Saylor'    , 'Doug'    , 'sophomore', 7474839293, 'Jefferson' , 'Independence' ,'MO', 64016, 'Software Arch' , 's222222', 0);
Insert into school.students values('s333333', 'Wu'        , 'Jennifer', 'senior'   , 9872647364, 'Washington', 'Overland Park','KS', 64032, 'Poli Sci'      , 's333333', 0);
Insert into school.students values('s444444', 'Chung'     , 'Jina'    , 'freshman' , 7364538273, 'Monroe'    , 'Grain Valley' ,'MO', 64021, 'Textile Design', 's444444', 0);
Insert into school.students values('s555555', 'Jones'     , 'Aaron'   , 'sophomore', 2394983474, 'Jackson'   , 'Gardner'      ,'KS', 63473, 'Music Eduction', 's555555', 0);
Insert into school.students values('s666666', 'Smith'     , 'Curtis'  , 'senior'   , 2398473847, 'Madison'   , 'Lees Summit'  ,'MO', 63748, 'Engineering'   , 's666666', 0);
Insert into school.students values('s777777', 'Bettis'    , 'Jasmine' , 'freshman' , 7578384902, 'Adams'     , 'Kansas City'  ,'MO', 67482, 'Marketing'     , 's777777', 0);
Insert into school.students values('s888888', 'Grandfield', 'Justin'  , 'junior'   , 7384730893, 'Buchanan'  , 'Gladstone'    ,'MO', 54827, 'Elem Ed'       , 's888888', 0);
Insert into school.students values('s999999', 'Mary Alex' , 'Smithu'  , 'junior'   , 5364738293, 'Grant'     , 'Platte City'  ,'MO', 63748, 'Physics'       , 's999999', 0);
Insert into school.students values('s000000', 'Gober'     , 'John'    , 'senior'   , 2435465768, 'Johnson'   , 'Kansas City'  ,'KS', 64968, 'Management'    , 's000000', 0);

commit;




---insert into departments--

Insert into school.departments values(10, 'Communication/Fine Arts', 'School of Arts and Sciences');
Insert into school.departments values(20, 'Math/Sciences'          , 'School of Arts and Sciences');
Insert into school.departments values(30, 'Technology'             , 'School of Business');
Insert into school.departments values(40, 'Education'              , 'School of Education');
Insert into school.departments values(50, 'Marketing/Management'   , 'School of Business');

commit;




--insert into instructors---

Insert into school.instructors values ('Srinath' ,'Shree'  , 30, 40000, 'shree@school.edu'  , 7565748576, 'i11111', 'i11111');
Insert into school.instructors values ('Byrd'    ,'Melody' , 10, 41000, 'melody@school.edu' , 7483748394, 'i22222', 'i22222');
Insert into school.instructors values ('Benson'  ,'Jami'   , 50, 42000, 'jami@school.edu'   , 7384094738, 'i33333', 'i33333');
Insert into school.instructors values ('Schrag'  ,'Amanda' , 10, 43000, 'amanda@school.edu' , 5738493849, 'i44444', 'i44444');
Insert into school.instructors values ('Roberts' ,'Deborah', 20, 44000, 'deborah@school.edu', 7598495859, 'i55555', 'i55555');
Insert into school.instructors values ('Zubal'   ,'Dave'   , 30, 45000, 'dave@school.edu'   , 7563849384, 'i66666', 'i66666');
Insert into school.instructors values ('Brinson' ,'Steven' , 50, 46000, 'steven@school.edu' , 5759485748, 'i77777', 'i77777');
Insert into school.instructors values ('Pottorff','Starla' , 20, 47000, 'starla@school.edu' , 6493847586, 'i88888', 'i88888');
Insert into school.instructors values ('Carter'  ,'Andrea' , 40, 48000, 'andrea@school.edu' , 7487584758, 'i99999', 'i99999');
Insert into school.instructors values ('Stafford','Carl'   , 40, 49000, 'carl@school.edu'   , 3874837483, 'i00000', 'i00000');

commit;





---insert into courses---

Insert into school.courses values('A10','Chemistry 101'           , 3, 20, 300);
Insert into school.courses values('B20','Technology 101'          , 3, 30, 300);
Insert into school.courses values('C30','Elementary Education 101', 3, 40, 300);
Insert into school.courses values('D40','Public Speaking'         , 3, 10, 300);
Insert into school.courses values('E50','Fashion Design'          , 3, 10, 300);
Insert into school.courses values('F60','Marketing 101'           , 3, 50, 300);
Insert into school.courses values('G70','Programming 101'         , 3, 30, 300);
Insert into school.courses values('H80','Education Practicum'     , 3, 40, 300);
Insert into school.courses values('I90','Biology 101'             , 3, 20, 300);
Insert into school.courses values('J00','Management 101'          , 3, 50, 300);

commit;




---insert into schedule---

Insert into school.schedule values( 1,    8,  9,     0,    0,    8,  9,     0,    0,    8,  9);
Insert into school.schedule values( 2,   10, 11,     0,    0,   10, 11,     0,    0,   10, 11);
Insert into school.schedule values( 3,   12, 13,     0,    0,   12, 13,     0,    0,   12, 13);
Insert into school.schedule values( 4,    9, 10,     0,    0,    9, 10,     0,    0,    9, 10);
Insert into school.schedule values( 5,   11, 12,     0,    0,   11, 12,     0,    0,   11, 12);
Insert into school.schedule values( 6,    13,  14,     0,    0,    13,  14,     0,    0,    13,  14);
Insert into school.schedule values( 7,    14,  15,     0,    0,    14,  15,     0,    0,    14,  15);
Insert into school.schedule values( 8,    0,  0,     8,  9.5,    0,  0,     8,  9.5,    0,  0);
Insert into school.schedule values( 9,    0,  0,   9.5,   11,    0,  0,   9.5,   11,    0,  0);
Insert into school.schedule values(10,    0,  0,    11, 12.5,    0,  0,    11, 12.5,    0,  0);
Insert into school.schedule values(11,    0,  0,    15, 16.5,    0,  0,    15, 16.5,    0,  0);
Insert into school.schedule values(12,    0,  0,     17,    20,    0,  0,     0,    0,    0,  0);
Insert into school.schedule values(13,    0,  0,     0,    0,    17,  20,     0,    0,    0,  0);
Insert into school.schedule values(14,    17,  20,     0,    0,    0,  0,     0,    0,    0,  0);

commit;




--insert into sections---

Insert into school.sections values(20161,'AB12','A10', 50000,  1, 'i55555', 25, 12);
Insert into school.sections values(20161,'CD34','A10', 50000,  4, 'i88888', 25,  6);
Insert into school.sections values(20161,'EF56','B20', 51000,  8, 'i11111', 25,  8);
Insert into school.sections values(20161,'GH78','B20', 51000,  2, 'i66666', 25, 21);
Insert into school.sections values(20161,'IJ90','C30', 52000,  3, 'i00000', 25, 10);
Insert into school.sections values(20161,'KL12','C30', 52000,  7, 'i99999', 25, 16);
Insert into school.sections values(20161,'MN34','D40', 53000, 12, 'i22222', 25,  9);
Insert into school.sections values(20161,'OP56','D40', 53000, 10, 'i44444', 25, 20);
Insert into school.sections values(20161,'QR78','E50', 54000,  9, 'i44444', 25, 12);
Insert into school.sections values(20161,'ST90','E50', 54000,  6, 'i22222', 25,  9);
Insert into school.sections values(20161,'UV12','F60', 55000, 11, 'i33333', 25, 15);
Insert into school.sections values(20161,'WX34','F60', 55000,  6, 'i77777', 25, 16);
Insert into school.sections values(20161,'YZ56','G70', 56000,  5, 'i66666', 25,  8);
Insert into school.sections values(20161,'AB78','G70', 56000,  3, 'i11111', 25,  9);
Insert into school.sections values(20161,'CD90','H80', 57000, 13, 'i00000', 25, 14);
Insert into school.sections values(20161,'EF12','H80', 57000, 14, 'i99999', 25, 19);
Insert into school.sections values(20161,'GH34','I90', 58000,  4, 'i55555', 25, 13);
Insert into school.sections values(20161,'IJ56','I90', 59000,  1, 'i88888', 25,  7);
Insert into school.sections values(20161,'KL78','J00', 60000,  7, 'i33333', 25, 16);
Insert into school.sections values(20161,'MN90','J00', 60000,  8, 'i77777', 25, 23);

commit;



     
---insert into enrollment---

Insert into school.enrollment values('s111111', 'A10', 'AB12', 'B+', 3, 20161);
Insert into school.enrollment values('s111111', 'I90', 'GH34', 'A', 3, 20161);
Insert into school.enrollment values('s111111', 'J00', 'KL78', 'B-', 3, 20161);
Insert into school.enrollment values('s111111', 'D40', 'MN34', 'A-', 3, 20152);

Insert into school.enrollment values('s222222', 'B20', 'EF56', 'B-', 3, 20161);
Insert into school.enrollment values('s222222', 'G70', 'YZ56', 'A-', 3, 20161);
Insert into school.enrollment values('s222222', 'E50', 'QR78', 'C+', 3, 20161);
Insert into school.enrollment values('s222222', 'A10', 'AB12', 'A-', 3, 20152);

Insert into school.enrollment values('s333333', 'D40', 'MN34', 'C+', 3, 20161);
Insert into school.enrollment values('s333333', 'J00', 'KL78', 'B-', 3, 20161);
Insert into school.enrollment values('s333333', 'E50', 'QR78', 'B', 3, 20161);
Insert into school.enrollment values('s333333', 'F60', 'UV12', 'B+', 3, 20152);

Insert into school.enrollment values('s444444', 'E50', 'ST90', 'B+', 3, 20161);
Insert into school.enrollment values('s444444', 'D40', 'OP56', 'C+', 3, 20161);
Insert into school.enrollment values('s444444', 'H80', 'EF12', 'A', 3, 20152);

Insert into school.enrollment values('s555555', 'I90', 'IJ56', 'B', 3, 20161);
Insert into school.enrollment values('s555555', 'D40', 'MN34', 'C+', 3, 20161);
Insert into school.enrollment values('s555555', 'H80', 'CD90', 'A', 3, 20161);
Insert into school.enrollment values('s555555', 'B20', 'EF56', 'B', 3, 20152);

Insert into school.enrollment values('s666666', 'G70', 'AB78', 'A', 3, 20161);
Insert into school.enrollment values('s666666', 'B20', 'EF56', 'A', 3, 20161);
Insert into school.enrollment values('s666666', 'I90', 'IJ56', 'A-', 3, 20161);
Insert into school.enrollment values('s666666', 'J00', 'KL78', 'B+', 3, 20152);

Insert into school.enrollment values('s777777', 'J00', 'KL78', 'B', 3, 20161);
Insert into school.enrollment values('s777777', 'F60', 'WX34', 'A-', 3, 20161);
Insert into school.enrollment values('s777777', 'B20', 'EF56', 'B', 3, 20152);

Insert into school.enrollment values('s888888', 'C30', 'KL12', 'A-', 3, 20161);
Insert into school.enrollment values('s888888', 'H80', 'CD90', 'A-', 3, 20161);
Insert into school.enrollment values('s888888', 'F60', 'WX34', 'B', 3, 20161);
Insert into school.enrollment values('s888888', 'D40', 'OP56', 'A-', 3, 20152);

Insert into school.enrollment values('s999999', 'A10', 'CD34', 'B+', 3, 20161);
Insert into school.enrollment values('s999999', 'I90', 'IJ56', 'A', 3, 20161);
Insert into school.enrollment values('s999999', 'G70', 'YZ56', 'B+', 3, 20152);

Insert into school.enrollment values('s000000', 'J00', 'KL78', 'A', 3, 20161);
Insert into school.enrollment values('s000000', 'D40', 'MN34', 'A-', 3, 20161);
Insert into school.enrollment values('s000000', 'F60', 'WX34', 'B+', 3, 20161);
Insert into school.enrollment values('s000000', 'I90', 'GH34', 'C+', 3, 20152);

commit;


---Student id vs total of fees_due---
create view school.student_fees as
select s.student_id, sum(c.fees) total_fees_due 
from school.students s, school.enrollment e, school.sections sec, school.courses c 
where(s.student_id = e.student_id) 
and (e.section_id = sec.section_id) 
and (sec.course_id = c.course_id) 
Group by s.student_id
order by (s.student_id)
;


---Student id , fees_due, and fees_paid

Create view school.tuition as
Select s.student_id, f.total_fees_due, s.fees_paid
from school.students s, school.student_fees f 
where(s.student_id = f.student_id) 
Order by (s.student_id)
;


---section gpa for each student's section---

CREATE VIEW school.student_section AS 
(select stu.first_name, stu.last_name, e.student_id, e.section_id, e.course_id, c.course_name, c.dept_id, e.hours, e.term,
case grade
when 'A' then 4.00
when 'A-' then 3.70
when 'B+' then 3.33
when 'B' then 3.00
when 'B-' then 2.70
when 'C+' then 2.30
when 'C' then 2.00
when 'C-' then 1.70
when 'D+' then 1.30
when 'D' then 1.00
when 'D-' then .70
when 'F' then 0
when null then null end
as section_gpa 
from school.enrollment e, school.sections s,school.courses c, school.students stu
where (e.section_id = s. section_id) and (s.course_id = c.course_id) and (e.student_id = stu.student_id))
;


---overall_gpa----

CREATE VIEW school.gpa AS
select student_id, 
(sum(section_gpa * hours))/sum(hours) as overall_gpa 
from school.student_section 
group by student_id
;


---transcript---

CREATE VIEW school.transcript AS
(select t.first_name, t.last_name, 
t.student_id, t.section_id, t.course_id, 
t.course_name, t.dept_id, t.hours, t.term, t.section_gpa, g.overall_gpa
from school.student_section t,school.gpa g
where (t.student_id = g.student_id))
;


---instructor/section ---

Create view school.instr_section AS
select i.first_name, i.last_name, i.dept_id, i.instr_id,
s.section_id, c.course_id, c.course_name, s.term, s.schedule_id, c.hours
from school.courses c, school.sections s, school.instructors i
where (i.instr_id = s.instr_id)
and (s.course_id = c.course_id)
;
