from Common import *

import random

class Api(object):
	def __init__(self):
		super(Api, self).__init__()
	
	def depth(self,symbol):
		"""
			depth
		"""
		response = doRequest("/v1/depth","post",{"symbol":symbol})
		if response.status_code == 200:
			return response.text
		else:
			return None
	def blance(self):
		"""
			
		"""
		response = doRequest("/v1/balance",'post')
		if response.status_code == 200:
			return response.text
		else:
			return None

	def entrust(self,type,symbol):
		"""

		"""
		response = doRequest("/v1/order/entrust",'post',{"symbol": symbol,"type":type,"count":16})
		if response.status_code == 200:
			return response.text
		else:
			return None
	def place(self,price,amount,operation='buy', symbol=''):
		"""
			
		"""
		response = doRequest("/v1/order/place",'post',{"symbol":symbol,"tradeAmount":amount,"tradePrice":price,"type":operation})
		if response.status_code == 200:
			return response.text
		else:
			return None
	def cancel(self,id):
		"""
			
		"""
		response = doRequest("/v1/order/cancel",'post',{"id":id})
		if response.status_code == 200:
			return response.text
		else:
			return None

def main():
	api = Api()
	#print(api.entrust(1))
	# print(api.blance())
	#print(api.place(0.60,1,'buy'))
	# print api.place(727.01,0.204,"sell")
	#print(api.cancel(115626))

		
	

if __name__ == '__main__':
	main()
