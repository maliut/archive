class Course < ApplicationRecord
  has_many :plans, through: :course_plans
  belongs_to :teacher
  has_many :employees, through: :choices
  has_many :exams
  validates :name, :hours, presence: true

  def plans
    cps = CoursePlan.where(course_id: id)
    cps.map { |cp| Plan.where(id: cp.plan_id)[0] }
  end
end
