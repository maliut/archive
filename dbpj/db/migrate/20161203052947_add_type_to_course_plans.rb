class AddTypeToCoursePlans < ActiveRecord::Migration[5.0]
  def change
    add_column :course_plans, :course_type, :integer
  end
end
