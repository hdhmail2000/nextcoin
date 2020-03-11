from Sign import paramsSign
from Constants import *
import requests
import time

def doRequest(path,method='post',params={}):
	paramsPrefix = {"host":API_HOST,'method':method.upper(),'uri':path}
	params['AccessKeyId'] = API_KEY
	params['SignatureVersion'] = 2
	params['SignatureMethod'] = 'HmacSHA256'
	params['Timestamp'] = int(time.time())
	params['Signature'] = paramsSign(params,paramsPrefix,API_SECRET)
	url = 'https://'+API_HOST+path

	return doPostRequest(url,params)

def doPostRequest(url,params):
	return requests.post(url,data=params,headers={"oex_lan":"zh_TW"})

def doGetRequest(url,params):
	return False

			


	
