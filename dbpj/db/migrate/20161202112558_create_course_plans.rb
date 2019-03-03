class CreateCoursePlans < ActiveRecord::Migration[5.0]
  def change
    create_table :course_plans, id: false do |t|
      t.integer :plan_id
      t.string :course_id

      # t.timestamps
    end
  end
end
