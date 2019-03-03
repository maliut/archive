class CreateEmployees < ActiveRecord::Migration[5.0]
  def change
    create_table :employees, id: false, primary_key: :user_id do |t|
      t.string :user_id
      t.string :name
      t.string :gender
      t.integer :join_date
      t.string :location
      t.integer :salary
      t.integer :bonus
      t.integer :department_id

      # t.timestamps
    end
  end
end
