BEGIN TRANSACTION;
CREATE TABLE "users" (
	`user_id`	varchar NOT NULL DEFAULT '',
	`encrypted_password`	varchar NOT NULL DEFAULT '',
	`remember_created_at`	datetime,
	`type`	integer NOT NULL,
	PRIMARY KEY(user_id)
);
CREATE TABLE "teachers" (
	`user_id`	varchar NOT NULL UNIQUE,
	`name`	varchar NOT NULL,
	`gender`	varchar NOT NULL,
	`tel`	varchar NOT NULL,
	`email`	varchar NOT NULL,
	PRIMARY KEY(user_id),
	FOREIGN KEY(`user_id`) REFERENCES users
);
CREATE TABLE "plans" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`department_id`	integer NOT NULL,
	FOREIGN KEY(`department_id`) REFERENCES departments
);
CREATE TABLE "managers" (
	`user_id`	varchar NOT NULL UNIQUE,
	`name`	varchar NOT NULL,
	`gender`	varchar NOT NULL,
	`location`	varchar NOT NULL,
	`tel`	varchar NOT NULL,
	`email`	varchar NOT NULL,
	`department_id`	integer NOT NULL,
	PRIMARY KEY(user_id),
	FOREIGN KEY(`user_id`) REFERENCES users,
	FOREIGN KEY(`department_id`) REFERENCES departments
);
CREATE TABLE "exams" (
	`course_id`	varchar NOT NULL,
	`type`	varchar NOT NULL,
	`date`	varchar NOT NULL,
	`room`	varchar NOT NULL,
	`grade_time`	varchar NOT NULL,
	FOREIGN KEY(`course_id`) REFERENCES courses
);
CREATE TABLE "employees" (
	`user_id`	varchar NOT NULL UNIQUE,
	`name`	varchar NOT NULL,
	`gender`	varchar NOT NULL,
	`join_date`	integer NOT NULL,
	`location`	varchar NOT NULL,
	`salary`	integer NOT NULL,
	`bonus`	integer NOT NULL,
	`department_id`	integer NOT NULL,
	PRIMARY KEY(user_id),
	FOREIGN KEY(`user_id`) REFERENCES users,
	FOREIGN KEY(`department_id`) REFERENCES departments
);
CREATE TABLE "departments" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name`	varchar NOT NULL
);
CREATE TABLE "courses" (
	`course_id`	varchar NOT NULL UNIQUE,
	`name`	varchar NOT NULL,
	`teacher_id`	varchar NOT NULL,
	`hours`	integer NOT NULL,
	PRIMARY KEY(course_id),
	FOREIGN KEY(`teacher_id`) REFERENCES teachers
);
CREATE TABLE "course_plans" (
	`plan_id`	integer NOT NULL,
	`course_id`	varchar NOT NULL,
	`type`	integer NOT NULL,
	FOREIGN KEY(`plan_id`) REFERENCES plans,
	FOREIGN KEY(`course_id`) REFERENCES courses
);
CREATE TABLE "choices" (
	`employee_id`	varchar NOT NULL,
	`course_id`	varchar NOT NULL,
	`grade`	integer,
	`apply_time`	varchar,
	`state`	integer NOT NULL,
	FOREIGN KEY(`employee_id`) REFERENCES employees,
	FOREIGN KEY(`course_id`) REFERENCES courses
);
CREATE UNIQUE INDEX "index_users_on_user_id" ON "users" ("user_id")

;
COMMIT;
