# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20161203052947) do

  create_table "choices", id: false, force: :cascade do |t|
    t.string  "employee_id", null: false
    t.string  "course_id",   null: false
    t.integer "grade"
    t.string  "apply_time"
    t.integer "state",       null: false
  end

  create_table "course_plans", id: false, force: :cascade do |t|
    t.integer "plan_id",     null: false
    t.string  "course_id",   null: false
    t.integer "course_type"
  end

  create_table "courses", primary_key: "course_id", id: :string, force: :cascade do |t|
    t.string  "name",       null: false
    t.string  "teacher_id", null: false
    t.integer "hours",      null: false
    t.index ["course_id"], name: "sqlite_autoindex_courses_1", unique: true
  end

  create_table "departments", force: :cascade do |t|
    t.string "name", null: false
    t.index ["id"], name: "sqlite_autoindex_departments_1", unique: true
  end

  create_table "employees", primary_key: "user_id", id: :string, force: :cascade do |t|
    t.string  "name",          null: false
    t.string  "gender",        null: false
    t.integer "join_date",     null: false
    t.string  "location",      null: false
    t.integer "salary",        null: false
    t.integer "bonus",         null: false
    t.integer "department_id", null: false
    t.index ["user_id"], name: "sqlite_autoindex_employees_1", unique: true
  end

  create_table "exams", id: false, force: :cascade do |t|
    t.string "course_id",  null: false
    t.string "type",       null: false
    t.string "date",       null: false
    t.string "room",       null: false
    t.string "grade_time", null: false
  end

  create_table "managers", primary_key: "user_id", id: :string, force: :cascade do |t|
    t.string  "name",          null: false
    t.string  "gender",        null: false
    t.string  "location",      null: false
    t.string  "tel",           null: false
    t.string  "email",         null: false
    t.integer "department_id", null: false
    t.index ["user_id"], name: "sqlite_autoindex_managers_1", unique: true
  end

  create_table "plans", force: :cascade do |t|
    t.integer "department_id", null: false
  end

  create_table "teachers", primary_key: "user_id", id: :string, force: :cascade do |t|
    t.string "name",   null: false
    t.string "gender", null: false
    t.string "tel",    null: false
    t.string "email",  null: false
    t.index ["user_id"], name: "sqlite_autoindex_teachers_1", unique: true
  end

  create_table "users", primary_key: "user_id", id: :string, default: "", force: :cascade do |t|
    t.string   "encrypted_password",  default: "", null: false
    t.datetime "remember_created_at"
    t.integer  "type",                             null: false
    t.index ["user_id"], name: "index_users_on_user_id", unique: true
    t.index ["user_id"], name: "sqlite_autoindex_users_1", unique: true
  end

end
