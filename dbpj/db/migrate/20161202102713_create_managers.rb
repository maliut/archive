class CreateManagers < ActiveRecord::Migration[5.0]
  def change
    create_table :managers, id: false, primary_key: :user_id do |t|
      t.string :user_id
      t.string :name
      t.string :gender
      t.string :location
      t.string :tel
      t.string :email
      t.integer :department_id

      # t.timestamps
    end
  end
end
