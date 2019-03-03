class Plan < ApplicationRecord
    belongs_to :department
    has_many :courses, through: :course_plans

  def courses
    cps = CoursePlan.where(plan_id: id)
    cps.map { |cp| Course.where(course_id: cp.course_id)[0] }
  end
end
