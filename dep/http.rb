require 'logger'
require 'open-uri'
require 'net/http'
if $0 == __FILE__
	$url = 'https://nextcoin.vip'
	uri = URI($url)
	res = Net::HTTP.get(uri) # => String
	puts res
end
