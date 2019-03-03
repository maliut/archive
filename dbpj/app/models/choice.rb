class Choice < ApplicationRecord

  @@states = ['课程中','正常通过', '补考未申请','补考待审核','补考审核通过','已通过','未通过']
  def state_string
    @@states[state]
  end

end
