class CreateTeachers < ActiveRecord::Migration[5.0]
  def change
    create_table :teachers, id: false, primary_key: :user_id do |t|
      t.string :user_id
      t.string :name
      t.string :gender
      t.string :tel
      t.string :email

      # t.timestamps
    end
  end
end
