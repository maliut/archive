class Department < ApplicationRecord
  has_one :manager
  has_many :employees
  has_many :plans
  validates :name, presence: true

  def self.selects
    s = []
    Department.all.each { |d| s << [d.name, d.id] }
    return s
  end

end
