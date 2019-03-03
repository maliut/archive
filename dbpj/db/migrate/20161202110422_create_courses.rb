class CreateCourses < ActiveRecord::Migration[5.0]
  def change
    create_table :courses, id: false, primary_key: :course_id do |t|
      t.string :course_id
      t.string :name
      t.string :teacher_id
      t.integer :hours

      # t.timestamps
    end
  end
end
