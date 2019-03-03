json.array!(@courses) do |course|
  json.extract! course, :id, :course_id, :name, :teacher_id, :hours
  json.url course_url(course, format: :json)
end
