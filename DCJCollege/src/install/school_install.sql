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
	password   varchar2(15) not null
);

--Grant select, insert, update, delete on school.students to school;
	



----departments----
	
CREATE TABLE school.departments(
	dept_id   number(2)    constraint dept_deptid_pk primary key,
	dept_name varchar2(30) not null,
	college   varchar(30)  not null
);

--Grant select, insert, update, delete on school.departments to school;
	


	
---instructors----

CREATE TABLE school.instructors(
	last_name  varchar2(15) not null,
	first_name varchar2(15) not null,
	dept_id    number(2)    constraint instruct_deptid_fk references school.departments(dept_id),
	office     number(5)    not null,
	email      varchar2(20) not null,
	phone      number(10)   not null, -- !!!No dashes!!!
	instr_id   varchar2(6)  constraint instruct_intrid_pk PRIMARY KEY,
	password   varchar2(15) not null
);
	
--Grant select, insert, update, delete on school.instructors to school;
	



---courses----

CREATE TABLE school.courses(
	course_no   varchar2(3)  constraint crs_crsno_pk PRIMARY KEY,
	course_name varchar2(30) not null,
	hours       number(1)    not null,
	dept_id     number(2)    constraint courses_deptid_fk references school.departments(dept_id),
	fees        number(5)    not null
);
	
--Grant select, insert, update, delete on school.courses to school;
	
	
	

	
---schedule---

CREATE TABLE school.schedule(
	schedule_id number(2) constraint sched_schedid_pk PRIMARY KEY,
	mon_start   number(4) default 0 not null,
	mon_end     number(4) default 0 not null,
	tues_start  number(4) default 0 not null,
	tues_end    number(4) default 0 not null,
	wed_start   number(4) default 0 not null,
	wed_end     number(4) default 0 not null,
	thur_start  number(4) default 0 not null,
	thur_end    number(4) default 0 not null,
	fri_start   number(4) default 0 not null,
	fri_end     number(4) default 0 not null
);
	
--Grant select, insert, update, delete on school.schedule to school;
	



---sections---

CREATE TABLE school.sections(
	term        number(5)   not null,
	section_id  varchar2(4) constraint sections_sectid_pk PRIMARY KEY,
	course_no   varchar2(3) constraint sections_courseno_fk references school.courses(course_no),
	room        number(5)   not null,
	schedule_id number(2)   constraint sections_schedid_fk references school.schedule(schedule_id),
	instr_id    varchar2(6) constraint sections_instrid_fk references school.instructors(instr_id),
	capacity    number(2)   not null,
	no_enrolled number(2)   not null
);
	
--Grant select, insert, update, delete on school.sections to school;



	
---enrollment----

CREATE TABLE school.enrollment(
	student_id varchar2(7) constraint enroll_studid_fk references school.students(student_id),
	section_id varchar2(4) constraint enroll_sectid_fk references school.sections(section_id),
	grade      number(3)   not null,
	hours      number(1)   not null
);
	
--Grant select, insert, update, delete on school.enrollment to school;
	

	


---insert into students----

Insert into school.students values('s111111', 'Nett'      , 'Chelsea' , 'junior'   , 6605412405, 'Lincoln'   , 'Blue Springs' ,'MO', 64014, 'Chemistry'     , 's111111');
Insert into school.students values('s222222', 'Saylor'    , 'Doug'    , 'sophomore', 7474839293, 'Jefferson' , 'Independence' ,'MO', 64016, 'Software Arch' , 's222222');
Insert into school.students values('s333333', 'Wu'        , 'Jennifer', 'senior'   , 9872647364, 'Washington', 'Overland Park','KS', 64032, 'Poli Sci'      , 's333333');
Insert into school.students values('s444444', 'Chung'     , 'Jina'    , 'freshman' , 7364538273, 'Monroe'    , 'Grain Valley' ,'MO', 64021, 'Textile Design', 's444444');
Insert into school.students values('s555555', 'Jones'     , 'Aaron'   , 'sophomore', 2394983474, 'Jackson'   , 'Gardner'      ,'KS', 63473, 'Clarinet'      , 's555555');
Insert into school.students values('s666666', 'Smith'     , 'Curtis'  , 'senior'   , 2398473847, 'Madison'   , 'Lees Summit'  ,'MO', 63748, 'Engineering'   , 's666666');
Insert into school.students values('s777777', 'Bettis'    , 'Jasmine' , 'freshman' , 7578384902, 'Adams'     , 'Kansas City'  ,'MO', 67482, 'Marketing'     , 's777777');
Insert into school.students values('s888888', 'Grandfield', 'Justin'  , 'junior'   , 7384730893, 'Buchanan'  , 'Gladstone'    ,'MO', 54827, 'Elem Ed'       , 's888888');
Insert into school.students values('s999999', 'Mary Alex' , 'Smithu'  , 'junior'   , 5364738293, 'Grant'     , 'Platte City'  ,'MO', 63748, 'Physics'       , 's999999');
Insert into school.students values('s000000', 'Gober'     , 'John'    , 'senior'   , 2435465768, 'Johnson'   , 'Kansas City'  ,'KS', 64968, 'Management'    , 's000000');

commit;




---insert into departments--

Insert into school.departments values(10, 'Communication/Fine Arts', 'School of Arts and Sciences');
Insert into school.departments values(20, 'Math/Sciences'          , 'School of Arts and Sciences');
Insert into school.departments values(30, 'Technology'             , 'School of Arts and Sciences');
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

Insert into school.courses values('A10','Chemistry 101'           , 4, 20, 400);
Insert into school.courses values('B20','Technology 101'          , 3, 30, 300);
Insert into school.courses values('C30','Elementary Education 101', 3, 40, 300);
Insert into school.courses values('D40','Public Speaking'         , 3, 10, 300);
Insert into school.courses values('E50','Fashion Design'          , 3, 10, 300);
Insert into school.courses values('F60','Marketing 101'           , 3, 50, 300);
Insert into school.courses values('G70','Programming 101'         , 3, 30, 300);
Insert into school.courses values('H80','Education Practicum'     , 1, 40, 100);
Insert into school.courses values('I90','Biology 101'             , 4, 20, 400);
Insert into school.courses values('J00','Management 101'          , 3, 50, 300);

commit;




---insert into schedule---

Insert into school.schedule values( 1,    8,  9,     0,    0,    8,  9,     2,    3,    8,  9);
Insert into school.schedule values( 2,   10, 11,     0,    0,   10, 11,     0,    0,   10, 11);
Insert into school.schedule values( 3,   12, 13,     0,    0,   12, 13,     0,    0,   12, 13);
Insert into school.schedule values( 4,    9, 10,     2,    3,    9, 10,     0,    0,    9, 10);
Insert into school.schedule values( 5,   11, 12,     0,    0,   11, 12,     0,    0,   11, 12);
Insert into school.schedule values( 6,    1,  2,     0,    0,    1,  2,     0,    0,    1,  2);
Insert into school.schedule values( 7,    2,  3,     0,    0,    2,  3,     0,    0,    2,  3);
Insert into school.schedule values( 8,    0,  0,     8,  9.5,    0,  0,     8,  9.5,    0,  0);
Insert into school.schedule values( 9,    0,  0,   9.5,   11,    0,  0,   9.5,   11,    0,  0);
Insert into school.schedule values(10,    0,  0,    11, 12.5,    0,  0,    11, 12.5,    0,  0);
Insert into school.schedule values(11,    0,  0,    15, 16.5,    0,  0,    15, 16.5,    0,  0);
Insert into school.schedule values(12,    0,  0,     5,    8,    0,  0,     0,    0,    0,  0);
Insert into school.schedule values(13,    0,  0,     0,    0,    5,  6,     0,    0,    0,  0);
Insert into school.schedule values(14,    5,  6,     0,    0,    0,  0,     0,    0,    0,  0);

commit;




--insert into sections---

Insert into school.sections values(12016,'AB12','A10', 50000,  1, 'i55555', 25, 12);
Insert into school.sections values(12016,'CD34','A10', 50000,  4, 'i88888', 25,  6);
Insert into school.sections values(12016,'EF56','B20', 51000,  8, 'i11111', 25,  8);
Insert into school.sections values(12016,'GH78','B20', 51000,  2, 'i66666', 25, 21);
Insert into school.sections values(12016,'IJ90','C30', 52000,  3, 'i00000', 25, 10);
Insert into school.sections values(12016,'KL12','C30', 52000,  7, 'i99999', 25, 16);
Insert into school.sections values(12016,'MN34','D40', 53000, 12, 'i22222', 25,  9);
Insert into school.sections values(12016,'OP56','D40', 53000, 10, 'i44444', 25, 20);
Insert into school.sections values(12016,'QR78','E50', 54000,  9, 'i44444', 25, 12);
Insert into school.sections values(12016,'ST90','E50', 54000,  6, 'i22222', 25,  9);
Insert into school.sections values(12016,'UV12','F60', 55000, 11, 'i33333', 25, 15);
Insert into school.sections values(12016,'WX34','F60', 55000,  6, 'i77777', 25, 16);
Insert into school.sections values(12016,'YZ56','G70', 56000,  5, 'i66666', 25,  8);
Insert into school.sections values(12016,'AB78','G70', 56000,  3, 'i11111', 25,  9);
Insert into school.sections values(12016,'CD90','H80', 57000, 13, 'i00000', 25, 14);
Insert into school.sections values(12016,'EF12','H80', 57000, 14, 'i99999', 25, 19);
Insert into school.sections values(12016,'GH34','I90', 58000,  4, 'i55555', 25, 13);
Insert into school.sections values(12016,'IJ56','I90', 59000,  1, 'i88888', 25,  7);
Insert into school.sections values(12016,'KL78','J00', 60000,  7, 'i33333', 25, 16);
Insert into school.sections values(12016,'MN90','J00', 60000,  8, 'i77777', 25, 23);

commit;



	
---insert into enrollment---

Insert into school.enrollment values('s111111', 'AB12', 89, 4);
Insert into school.enrollment values('s111111', 'GH34', 95, 4);
Insert into school.enrollment values('s111111', 'KL78', 80, 3);
Insert into school.enrollment values('s111111', 'MN34', 91, 3);

Insert into school.enrollment values('s222222', 'EF56', 80, 3);
Insert into school.enrollment values('s222222', 'YZ56', 93, 3);
Insert into school.enrollment values('s222222', 'QR78', 79, 3);
Insert into school.enrollment values('s222222', 'AB12', 90, 4);

Insert into school.enrollment values('s333333', 'MN34', 78, 3);
Insert into school.enrollment values('s333333', 'KL78', 81, 3);
Insert into school.enrollment values('s333333', 'QR78', 83, 3);
Insert into school.enrollment values('s333333', 'UV12', 87, 3);

Insert into school.enrollment values('s444444', 'ST90', 89, 3);
Insert into school.enrollment values('s444444', 'OP56', 77, 3);
Insert into school.enrollment values('s444444', 'EF12', 98, 1);

Insert into school.enrollment values('s555555', 'IJ56', 84, 4);
Insert into school.enrollment values('s555555', 'MN34', 70, 3);
Insert into school.enrollment values('s555555', 'CD90', 97, 1);
Insert into school.enrollment values('s555555', 'EF56', 85, 3);

Insert into school.enrollment values('s666666', 'AB78', 94, 3);
Insert into school.enrollment values('s666666', 'EF56', 96, 3);
Insert into school.enrollment values('s666666', 'IJ56', 91, 4);
Insert into school.enrollment values('s666666', 'KL78', 89, 3);

Insert into school.enrollment values('s777777', 'KL78', 84, 3);
Insert into school.enrollment values('s777777', 'WX34', 91, 3);
Insert into school.enrollment values('s777777', 'EF56', 86, 3);

Insert into school.enrollment values('s888888', 'KL12', 91, 3);
Insert into school.enrollment values('s888888', 'CD90', 93, 1);
Insert into school.enrollment values('s888888', 'WX34', 86, 3);
Insert into school.enrollment values('s888888', 'OP56', 91, 3);

Insert into school.enrollment values('s999999', 'CD34', 89, 4);
Insert into school.enrollment values('s999999', 'IJ56', 99, 4);
Insert into school.enrollment values('s999999', 'YZ56', 89, 3);

Insert into school.enrollment values('s000000', 'KL78', 94, 3);
Insert into school.enrollment values('s000000', 'MN34', 91, 3);
Insert into school.enrollment values('s000000', 'WX34', 87, 3);
Insert into school.enrollment values('s000000', 'GH34', 79, 4);

commit;