class Exam < ApplicationRecord
  belongs_to :course
  validates :exam_type, :date, :room, :grade_time, presence: true

  def id
    course_id
  end

  def self.find(id)
    res = self.where(course_id: id)
    raise if res.empty?
    res[0]
  end
  
end
