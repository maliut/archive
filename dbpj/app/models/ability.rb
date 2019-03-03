class Ability
  include CanCan::Ability

  def initialize(user)
    # Define abilities for the passed in user here. For example:
    #
    user ||= User.new # guest user (not logged in)
    can :me, User
    can [:read, :update], User, user_id: user.user_id
    if user.user_type == 0
      can :read, [Teacher, Manager, Employee, Course]
      can :search, :all
    elsif user.user_type == 1
      can :manage, :all
    elsif user.user_type == 2
      can :create, [Course, Exam]
      can [:read, :update, :destroy], Course, teacher_id: user.user_id
      can [:read, :update, :check], Teacher, user_id: user.user_id
    elsif user.user_type == 3
      can :create, [Employee, Plan]
      can [:read, :update, :destroy], Employee, department_id: user.role.department_id
      can :read, Course
      can [:read, :update], Manager, user_id: user.user_id
    elsif user.user_type == 4
      can :read, Plan, department_id: user.role.department_id
      can [:read, :update, :course_select, :course_selected], Employee, user_id: user.user_id
    end
    #
    # The first argument to `can` is the action you are giving the user 
    # permission to do.
    # If you pass :manage it will apply to every action. Other common actions
    # here are :read, :create, :update and :destroy.
    #
    # The second argument is the resource the user can perform the action on. 
    # If you pass :all it will apply to every resource. Otherwise pass a Ruby
    # class of the resource.
    #
    # The third argument is an optional hash of conditions to further filter the
    # objects.
    # For example, here the user can only update published articles.
    #
    #   can :update, Article, :published => true
    #
    # See the wiki for details:
    # https://github.com/ryanb/cancan/wiki/Defining-Abilities
  end
end
