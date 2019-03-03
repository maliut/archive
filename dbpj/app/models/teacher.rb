class Teacher < ApplicationRecord
  has_one :user, foreign_key: "user_id", dependent: :destroy
  has_many :courses
  validates :name, :gender, :tel, :email, presence: true
  validates :email, format: { with: /\A[^@\s]+@[^@\s]+\z/ }
  validates :tel, format: { with: /\A\d+\z/ }
end
