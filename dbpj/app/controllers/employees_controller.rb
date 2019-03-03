class EmployeesController < ApplicationController
  skip_before_filter :verify_authenticity_token, only: [:patch_create, :course_selected]
  before_action :set_employee, only: [:show, :edit, :update, :destroy]
  #load_and_authorize_resource

  # GET /employees
  # GET /employees.json
  def index
    @employees = []
    if current_user.user_type <= 1
      @employees = Employee.all
    elsif current_user.user_type == 3   # manager
      @employees = Employee.where(department_id: current_user.role.department_id)
    end
  end

  # GET /employees/1
  # GET /employees/1.json
  def show
    @choices = Choice.where(employee_id: @employee.id)
  end

  #GET /employees/course_select
  def course_select
    @plans = Plan.where(department_id:current_user.role.department_id)
    @courses = @plans.empty? ? nil : @plans[0].courses
    if @courses != nil
      @otherCourses = Course.all - @courses #need to skip @courses
    else
      @otherCourses = Course.all
    end
  end

  #POST /employees/course_select
  #has chosen courses
  def course_selected
    courses = params['courses']
    employee = current_user.role
    courses.each do |course|
      Choice.create(employee_id: employee.id, course_id: course, state: 0)
    end
    redirect_to users_me_path
  end

  #GET /employees/apply
  def apply_make_up
    course_id = params['course_id']
    time = Exam.find(course_id).grade_time
    now = TimeHelper.now
    delta = TimeHelper.delta(now,time)
    choice = Choice.where(employee_id: current_user.role.id, course_id: course_id)
    choice.update_all(apply_time: now)
    if delta > 15
      choice.update_all(state: 6)#fail
      redirect_to users_me_path, notice: 'time out, you fail.'
    elsif delta > 7
      choice.update_all(state: 3)
      redirect_to users_me_path, notice: 'pay 100 successfully.'
    elsif delta > 0
      choice.update_all(state: 3)
      redirect_to users_me_path, notice: 'pay 50 successfully.'
    else
      choice.update_all(state: 3)
      redirect_to users_me_path, notice: "apply successfully(don't need pay)."
    end
  end

  #GET /employees/pay
  def pay

    #format.html { redirect_to users_me_path, notice: 'pay successfully.' }
  end

  # GET /employees/search?key=
  def search
    begin
      @employee = Employee.find(params[:key])
      if current_user.user_type <= 1
        redirect_to @employee 
        return
      end
      # remain must be manager (or no auth)
      if @employee.department_id == current_user.role.department_id
        redirect_to @employee
        return
      else
        redirect_to employees_path, notice: 'No employee found in your department.'
        return
      end
    rescue
      # no user, find by name
      @employees = Employee.where(name: params[:key])
      redirect_to employees_path, notice: 'No user found in your department.' if @employees.empty?
      @employees
    end
  end

  # GET /employees/new
  def new
    @employee = Employee.new
  end

  # GET /employees/1/edit
  def edit
  end

  # POST /employees
  # POST /employees.json
  def create
    @employee = Employee.new(employee_params)

    respond_to do |format|
      if @employee.save
        format.html { redirect_to @employee, notice: 'Employee was successfully created.' }
        format.json { render :show, status: :created, location: @employee }
      else
        format.html { render :new }
        format.json { render json: @employee.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /employees/1
  # PATCH/PUT /employees/1.json
  def update
    respond_to do |format|
      if @employee.update(employee_params)
        format.html { redirect_to @employee, notice: 'Employee was successfully updated.' }
        format.json { render :show, status: :ok, location: @employee }
      else
        format.html { render :edit }
        format.json { render json: @employee.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /employees/1
  # DELETE /employees/1.json
  def destroy
    @employee.destroy
    respond_to do |format|
      format.html { redirect_to employees_url, notice: 'Employee was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_employee
      @employee = Employee.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def employee_params
      params.require(:employee).permit(:user_id, :name, :gender, :join_date, :location, :salary, :bonus, :department_id)
    end
end
