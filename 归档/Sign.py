# !/usr/local/bin/python
# coding=utf8
# @Date : 2018-02-05
# @Auth : yangxuefeng

import urllib
import base64
import hmac
import hashlib

def paramsSign(params,paramsPrefix,secret):
	"""
		concat params
	"""
	host = paramsPrefix['host'].lower()
	method = paramsPrefix['method'].upper()
	uri = paramsPrefix['uri']
	tempParams = urllib.urlencode(sorted(params.items(),key=lambda d:d[0],reverse=False))
	payload = '\n'.join([method,host,uri,tempParams]).encode(encoding='UTF-8')
	return _sign(payload,secret)

def _sign(message,secret):
	secret = secret.encode(encoding='UTF-8')
	message = message.encode(encoding='UTF-8')
	return base64.b64encode(hmac.new(secret,message,digestmod=hashlib.sha256).digest())

def _signMessage(message,secret):
	"""
		sign data
	"""
	secret = secret.encode(encoding='UTF-8')
	message = message.encode(encoding='UTF-8')
	return base64.b64encode(hmac.new(secret,message,digestmod=hashlib.sha256).digest())

def signBefore():
	pass

def signAfter():
	pass

