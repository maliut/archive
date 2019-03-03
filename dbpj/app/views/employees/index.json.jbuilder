json.array!(@employees) do |employee|
  json.extract! employee, :id, :user_id, :name, :gender, :join_date, :location, :salary, :bonus, :department_id
  json.url employee_url(employee, format: :json)
end
