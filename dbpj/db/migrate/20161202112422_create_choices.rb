class CreateChoices < ActiveRecord::Migration[5.0]
  def change
    create_table :choices, id: false do |t|
      t.string :employee_id
      t.string :course_id
      t.integer :grade
      t.string :apply_time
      t.integer :state

      # t.timestamps
    end
  end
end
