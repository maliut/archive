json.array!(@managers) do |manager|
  json.extract! manager, :id, :user_id, :name, :gender, :location, :tel, :email, :department_id
  json.url manager_url(manager, format: :json)
end
