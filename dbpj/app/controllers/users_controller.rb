class UsersController < ApplicationController
  skip_before_filter :verify_authenticity_token, only: [:patch_create]
  @@controllers = [nil, nil, 'teachers', 'managers', 'employees']
  @@user_type_map = {'CEO' => 0, '系统管理员' => 1, '主管' => 3, '教师' => 2, '员工' => 4}
  before_filter :authenticate_user!, except: :sign_out
  before_action :set_user, only: [:show, :edit, :update, :destroy]

  # GET /users
  # GET /users.json
  def index
    @users = User.all
    # if is admin
    @employees_not_active = Employee.all - User.where(user_type: 4).collect { |user| Employee.find(user.user_id) }
    # session[:employees_not_active] = @employees_not_active
  end

  # GET /users/not_active_employees
  def not_active_employees
    redirect_to root_url if current_user.user_type != 1
    @employees = Employee.all - User.where(user_type: 4).collect { |user| Employee.find(user.user_id) }
  end

  # GET /users/1
  # GET /users/1.json
  def show
  end

  # GET /users/new
  def new
    @user = User.new
  end

  # GET /users/1/edit
  def edit
  end

  # GET /employees/patch
  # redirect to patch create users page
  def patch
    redirect_to root_url if current_user.user_type != 1
  end

  # POST /employees/patch
  # do creating work
  # receive params
  def patch_create
    redirect_to root_url if current_user.user_type != 1
    params[:patch].split.each_slice(3).each do |data|
      begin
        user_params = { user_id: data[0], password: data[1], user_type: @@user_type_map[data[2]] }
        user = User.new(user_params)
        if user.user_type == 0 || user.user_type == 1   # admin or CEO
          user.save
        else
          User.create(user)
        end
      rescue
      end
    end
    redirect_to users_path, notice: 'Patch create compelete.'
  end

  # POST /users
  def create
    redirect_to root_url if current_user.user_type != 1
    @user = User.new(user_params)
    if @user.user_type == 0 || @user.user_type == 1   # admin or CEO
      if @user.save
        redirect_to @user, notice: 'User was successfully created.'
      else
        redirect_to users_path
      end
    else  # teacher or manager or employee 
      role = User.create(@user)
      if role.persisted?
        redirect_to controller: @@controllers[@user.user_type], action: 'edit', id: role.user_id, notice: 'User was successfully created.'
      else 
        redirect_to users_path
      end
    end
  end

  # PATCH/PUT /users/1
  def update
    if @user.user_type == 0 || @user.user_type == 1   # admin or CEO
      if @user.update(user_params)
        redirect_to @user, notice: 'User was successfully updated.' 
      else
        render :edit 
      end
    else
      if User.update(@user, user_params)
        redirect_to @user, notice: 'User was successfully updated.' 
      else
        render :edit 
      end
    end
  end

  # DELETE /users/1
  # DELETE /users/1.json
  def destroy
    redirect_to root_url if current_user.user_type != 1
    if @user.user_type == 0 || @user.user_type == 1   # admin or CEO
      @user.destroy
    else 
      User.destroy(@user)
    end
    respond_to do |format|
      format.html { redirect_to users_url, notice: 'User was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  # GET /users/search?key=
  def search
    begin
      @user = User.find(params[:key])
      redirect_to @user if @user
    rescue
      # no user, find by name
      @users = []
      [Employee, Manager, Teacher].each { |model| model.where(name: params[:key]).each { |e| @users << e.user } }
      redirect_to users_path, notice: 'No user found.' if @users.empty?
      @users
    end
  end

  # GET /users/me
  def me
    if current_user.user_type == 0 || current_user.user_type == 1   # admin or CEO
      redirect_to current_user
    else 
      redirect_to current_user.role
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user
      @user = User.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def user_params
      params.require(:user).permit(:user_id, :password, :user_type)
    end

end