json.array!(@plans) do |plan|
  json.extract! plan, :id, :department_id
  json.url plan_url(plan, format: :json)
end
