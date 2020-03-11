#!/usr/bin/ruby
# coding: utf-8

require 'net/http'
require 'open-uri'

module Tools
  def send_data(url, data)
    url = URI.parse(url)
    req = Net::HTTP::Post.new(url, {'Content-Type' => 'application/json'})
    #puts data
    req.body = data
    res = Net::HTTP.new(url.host, url.port).start {|http| http.request(req)}

    #puts res.body
  end
end


def check_num

end

def add_head_0x(data)
  # 如果不是0x开头的数据, 增加
  data = data[0,2] != '0x' ? '0x' << data : data
  return data
end

def remove_head_0x(data)
  # 如果是0x开头的数据, 删除
  data = data[0,2] == '0x' ? data[2, data.length-2] : data
  return data
end

def add_more_head_0x(*args)
  result = []
  args.each do | row |
    result << add_head_0x(row)
  end
  return result
end

def remove_more_head_0x(*args)
  result = []
  args.each do | row |
    result << remove_head_0x(row)
  end
  return result
end


def is_correct_addr(url)
  # 判断是否是有效的addr
  # reg = Regexp.new('^(\d{1,3}\.){1,3}\d{1,3}:\d{1,5}$')   # 暂时存在一定缺陷, 匹配时无限制
  reg = Regexp.new('^http[s]{0,1}://.*?:.*?@(\d{1,3}\.){1,3}\d{1,3}:\d{1,5}$')   # 暂时存在一定缺陷
  is_url = reg.match(url)
  if is_url
    return true
  else
    return false
  end
end

def is_correct_url(url)
  # 判断是否是有效的addr
  reg = Regexp.new('http[s]{0,1}://(.*?\.){1,5}.*?[/]{0,1}')   # 暂时存在一定缺陷
  is_url = reg.match(url)
  if is_url
    return true
  else
    return false
  end
end



if __FILE__ == $0
  puts add_head_0x('100101')
  puts remove_head_0x('0x100101')
  puts add_more_head_0x('a', 'b', '1')
  a, b, c = remove_more_head_0x('0xa', '0xb', '0x1')
  puts a
  puts b
  puts c
end


