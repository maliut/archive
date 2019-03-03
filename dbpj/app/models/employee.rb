class Employee < ApplicationRecord
  has_one :user, foreign_key: "user_id", dependent: :destroy
  belongs_to :department
  has_many :courses, through: :choices
  has_many :choices
  validates :name, :gender, :join_date, :location, :salary, :bonus, presence: true

end
