json.array!(@exams) do |exam|
  json.extract! exam, :id, :course_id, :type, :date, :room, :grade_time
  json.url exam_url(exam, format: :json)
end
