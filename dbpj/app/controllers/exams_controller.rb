class ExamsController < ApplicationController
  before_action :set_exam, only: [:show, :edit, :update, :destroy]
  # load_and_authorize_resource

  # GET /exams
  # GET /exams.json
  def index
    @exams = Exam.all
  end

  # GET /exams/1
  # GET /exams/1.json
  def show
  end

  # GET /exams/new
  def new
    @exam = Exam.new
    @exam.course_id = params['course_id']
    @exam.grade_time = TimeHelper.now
  end

  # GET /exams/1/edit
  def edit
  end

  # POST /exams
  # POST /exams.json
  def create
    @exam = Exam.new(exam_params)

    respond_to do |format|
      if @exam.save
        format.html { redirect_to @exam, notice: 'Exam was successfully created.' }
        format.json { render :show, status: :created, location: @exam }
      else
        format.html { render :new }
        format.json { render json: @exam.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /exams/1
  # PATCH/PUT /exams/1.json
  def update
    #  if @exam.update(exam_params)
    #    redirect_to @exam, notice: 'Exam was successfully updated.' 
    #  else
    #    render :edit 
    #  end
    redirect_to @exam
  end

  # DELETE /exams/1
  # DELETE /exams/1.json
  def destroy
    # @exam.delete
    # redirect_to exams_url, notice: 'Exam was successfully destroyed.'
    redirect_to @exam
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_exam
      @exam = Exam.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def exam_params
      params.require(:exam).permit(:course_id, :exam_type, :date, :room, :grade_time)
    end
end
