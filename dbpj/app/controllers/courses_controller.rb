class CoursesController < ApplicationController
  skip_before_filter :verify_authenticity_token, only: [:grade]
  before_action :set_course, only: [:show, :edit, :update, :destroy]
  #load_and_authorize_resource
  
  # GET /courses
  # GET /courses.json
  def index
    @courses = Course.all
  end

  # GET /courses/1
  # GET /courses/1.json
  def show
    @choices = Choice.where(course_id: @course.id)
  end

  #POST /courses/grade
  def grade
    course_id = params['course']
    grades = params['grades']
    if grades != nil
      grades.each do |em_id,grade|
        grade = grade.to_i
        choices = Choice.where(employee_id: em_id, course_id: course_id)
        if choices[0].state == 0
          if grade >= 60
            choices.update_all(grade: grade,state: 1)
          else
            choices.update_all(grade: grade,state: 2)
          end

        elsif choices[0].state == 4
          if grade >= 60
            choices.update_all(grade: grade,state: 5)
          else
            choices.update_all(grade: grade,state: 6)
          end
        end

        Exam.where(course_id: course_id).update_all(grade_time: TimeHelper.now)

        em = Employee.find(em_id)
        choices = em.choices
        succ = true
        choices.each do |choice|
          if choice.state != 1 and choice.state != 5
            succ = false
            break
          end
        end
        if (succ)
          em.bonus += 10
          em.save
        end
      end
    end

    redirect_to users_me_path, notice: '评分成功.'
  end

  # GET /courses/new
  def new
    @course = Course.new
    @course.teacher_id = params['teacher_id']
  end

  # GET /courses/1/edit
  def edit
  end

  # POST /courses
  # POST /courses.json
  def create
    @course = Course.new(course_params)

    respond_to do |format|
      if @course.save
        format.html { redirect_to @course, notice: 'Course was successfully created.' }
        format.json { render :show, status: :created, location: @course }
      else
        format.html { render :new }
        format.json { render json: @course.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /courses/1
  # PATCH/PUT /courses/1.json
  def update
    respond_to do |format|
      if @course.update(course_params)
        format.html { redirect_to @course, notice: 'Course was successfully updated.' }
        format.json { render :show, status: :ok, location: @course }
      else
        format.html { render :edit }
        format.json { render json: @course.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /courses/1
  # DELETE /courses/1.json
  def destroy
    @course.destroy
    respond_to do |format|
      format.html { redirect_to courses_url, notice: 'Course was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_course
      @course = Course.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def course_params
      params.require(:course).permit(:course_id, :name, :teacher_id, :hours)
    end
end
