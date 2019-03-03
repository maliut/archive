json.array!(@teachers) do |teacher|
  json.extract! teacher, :id, :user_id, :name, :gender, :tel, :email
  json.url teacher_url(teacher, format: :json)
end
