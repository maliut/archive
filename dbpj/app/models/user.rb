class User < ApplicationRecord
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable#:recoverable, #:registerable, #, :trackable, :validatable
  devise :database_authenticatable, :rememberable
  validates :user_id, :user_type, presence: true
  validates :user_id, uniqueness: true

  def role
    if user_type == 2  # teacher
      Teacher.find(user_id)
    elsif user_type == 3 # manager
      Manager.find(user_id)
    elsif user_type == 4 # employee
      Employee.find(user_id)
    end
  end

  def self.create(user)
    if user.user_type == 2  # teacher
      teacher = Teacher.new(user_id: user.user_id)
      ApplicationRecord.transaction do
        user.save!
        teacher.save!(validate: false)
      end
      return teacher
    elsif user.user_type == 3 # manager
      manager = Manager.new(user_id: user.user_id)
      ApplicationRecord.transaction do
        user.save!
        manager.save!(validate: false)
      end
      return manager
    elsif user.user_type == 4 # employee
      employee = Employee.new(user_id: user.user_id)
      ApplicationRecord.transaction do
        user.save!
        employee.save!(validate: false) if !Employee.exists?(user.user_id)
      end
      return employee
    end
  end

  def self.update(user, user_params)
    role = nil
    if user.user_type == 2  # teacher
      role = Teacher.find(user.user_id)
    elsif user.user_type == 3 # manager
      role = Manager.find(user.user_id)
    elsif user.user_type == 4 # employee
      role = Employee.find(user.user_id)
    end
    ApplicationRecord.transaction do
        user.update!(user_params)
        role.update_attribute(:user_id, user.user_id)
    end
    return user.user_id == user_params["user_id"]
  end

  def self.destroy(user)
    if user.user_type == 2
      teacher = Teacher.find(user.user_id)
      ApplicationRecord.transaction do
        user.destroy
        teacher.destroy
      end
    elsif user.user_type == 3
      manager = Manager.find(user.user_id)
      ApplicationRecord.transaction do
        user.destroy
        manager.destroy
      end
    elsif user.user_type == 4
      employee = Employee.find(user.user_id)
      ApplicationRecord.transaction do
        user.destroy
        employee.destroy
      end
    end
  end

  protected
    def email_required?
      false
    end

    def email_changed?
      false
    end
end
