class TeachersController < ApplicationController
  before_action :set_teacher, only: [:show, :edit, :update, :destroy]
  load_and_authorize_resource

  # GET /teachers
  # GET /teachers.json
  def index
    @teachers = Teacher.all
  end

  # GET /teachers/1
  # GET /teachers/1.json
  def show
    @courses = Course.where(teacher_id: @teacher.id)
  end

  # GET /teachers/new
  def new
    redirect_to :back
  end

  # GET /teachers/1/edit
  def edit
  end

  # POST /teachers
  # POST /teachers.json
  def create
    render :index
  end

  #get /teachers/check
  def check
    eid = params['employee_id']
    cid = params['course_id']
    re = params['reply'].to_i
    Choice.where(employee_id:eid,course_id:cid).update_all(state: (re == 1) ? 4 : 6)
    redirect_to users_me_path
  end

  # PATCH/PUT /teachers/1
  # PATCH/PUT /teachers/1.json
  def update
    respond_to do |format|
      if @teacher.update(teacher_params)
        format.html { redirect_to @teacher, notice: 'Teacher was successfully updated.' }
        format.json { render :show, status: :ok, location: @teacher }
      else
        format.html { render :edit }
        format.json { render json: @teacher.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /teachers/1
  # DELETE /teachers/1.json
  def destroy
    @teacher.destroy
    respond_to do |format|
      format.html { redirect_to teachers_url, notice: 'Teacher was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_teacher
      @teacher = Teacher.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def teacher_params
      params.require(:teacher).permit(:user_id, :name, :gender, :tel, :email)
    end
end
