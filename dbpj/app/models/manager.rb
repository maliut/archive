class Manager < ApplicationRecord
  has_one :user, foreign_key: "user_id", dependent: :destroy
  belongs_to :department
  validates :name, :gender, :location, :tel, :email, presence: true
  validates :email, format: { with: /\A[^@\s]+@[^@\s]+\z/ }
  validates :tel, format: { with: /\A\d+\z/ }
end
