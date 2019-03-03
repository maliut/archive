module TimeHelper

  def self.delta(now, old)
    t_now = TimeHelper.format(now)
    t_then = TimeHelper.format(old)
    (t_now - t_then) / 60 / 60 / 24
  end

  def self.format(t)
    arr = t.split('-')
    Time.local(arr[0].to_i, arr[1].to_i, arr[2].to_i)
  end

  def self.now
    time = Time.new
    "#{time.year}-#{time.month}-#{time.day}"
  end
end