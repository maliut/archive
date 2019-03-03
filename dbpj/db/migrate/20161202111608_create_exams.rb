class CreateExams < ActiveRecord::Migration[5.0]
  def change
    create_table :exams, id: false do |t|
      t.string :course_id
      t.string :type
      t.string :date
      t.string :room
      t.string :grade_time

      # t.timestamps
    end
  end
end
