主页 => 个人信息页

- teacher：
  - 个人信息页：teachers/:id：所有自己开的课（指向查看课程信息），发布课程
  - 发布课程页：courses/new
  - 课程信息页：**courses/:id**：展示选课学生，跟着登记分数的表单
- manager：
  - 个人信息页：managers/:id：所有的部门员工（修改，删除），录入员工，制定计划，搜索员工
  - 录入员工：employees/new
  - 修改员工：employees/:id/edit
  - **搜索员工：employees/search?key=xxx //未作**
  - 制定计划：plans/new
  - **批量录入：employees/batch //未作完**
- employee：
  - 个人信息页：employees/:id：展示已选课程（可看成绩，申请补考等），如果没有选课则跳到选课
  - 选课：employees/course_select：展示计划内的课程和可以选修的课程，提供选择的复选框
- ceo：
  - 个人信息页：users/:id：链接至信息展示页面所有教师信息：teachers
  - 所有课程信息：courses
  - 所有成绩信息：**courses/:id**：是 ceo 则显示所有选课的人的成绩
  - 部门主管信息：managers
  - 员工信息：employees
- admin：
  - 个人信息页：users/:id：管理用户
  - 管理用户信息：users：增加用户，搜索用户，接受提醒
  - 搜索用户：users/search?key=xxx
  - 增加用户：users/new
  - 所有提醒（未增加的用户）：users/new_employee
  - **批量增加用户：users/batch//未作完**

  ​

