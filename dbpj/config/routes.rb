Rails.application.routes.draw do
  get 'users/me', to: 'users#me'
  get 'users/search', to: 'users#search'
  get 'employees/search', to: 'employees#search'
  get 'employees/course_select', to: 'employees#course_select'
  post 'employees/course_select', to: 'employees#course_selected'
  get 'employees/apply', to: 'employees#apply_make_up'
  get 'employees/pay', to: 'employees#pay'
  post 'courses/grade', to: 'courses#grade'
  get 'users/patch', to: 'users#patch'
  post 'users/patch', to: 'users#patch_create'
  get 'users/not_active_employees', to: 'users#not_active_employees'
  get 'teachers/check', to: 'teachers#check'
  devise_for :users
  resources :users
  resources :exams
  resources :courses
  resources :plans
  resources :teachers
  resources :managers
  resources :employees
  resources :departments
  root to: 'users#me'
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
