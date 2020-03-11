# coding=utf8
from Api import *
import json
from requests.packages.urllib3.exceptions import InsecureRequestWarning
from interval import Interval
import threading
import time
from decimal import *


class Monitoring():

    def get_latest_price(self, check_type, sourceURL):
        requests.packages.urllib3.disable_warnings(InsecureRequestWarning)
        try:
            resp = requests.get(sourceURL, verify=False, timeout=5)
        except Exception as e:
            return {'code': 0, 'message': 'Failed', 'data': 'Latest API Time Out!'}
        if resp.status_code == 200:
            if check_type == 'eth_cnyt':
                latest_ethusdt = json.loads(resp.text).get('last')
                requests.packages.urllib3.disable_warnings(InsecureRequestWarning)
                resp_usdtcnyx = requests.get('https://data.gateio.io/api2/1/ticker/usdt_cnyx', verify=False)
                latest_usdtcnyx = json.loads(resp_usdtcnyx.text).get('last').encode('utf-8')
                getcontext().prec = 2
                latest_price = Decimal(latest_ethusdt) * Decimal(latest_usdtcnyx)
            elif check_type == 'wkb_cnyt':
                latest_price = json.loads(resp.text).get('info').get('new_price')
            elif check_type == 'sc_cnyt':
                latest_scbtc = json.loads(resp.text).get('lastPrice').encode('utf-8')
                requests.packages.urllib3.disable_warnings(InsecureRequestWarning)
                resp_btccnyt = requests.get('https://data.gateio.io/api2/1/ticker/btc_cnyx', verify=False)
                latest_btccnyt = json.loads(resp_btccnyt.text).get('last').encode('utf-8')
                getcontext().prec = 5
                latest_price = Decimal(latest_scbtc) * Decimal(latest_btccnyt)
            elif check_type == 'ae_cnyt':
                latest_aeusdt = json.loads(resp.text).get('last')
                requests.packages.urllib3.disable_warnings(InsecureRequestWarning)
                resp_usdtcnyx = requests.get('https://data.gateio.io/api2/1/ticker/usdt_cnyx', verify=False)
                latest_usdtcnyx = json.loads(resp_usdtcnyx.text).get('last').encode('utf-8')
                getcontext().prec = 3
                latest_price = Decimal(latest_aeusdt) * Decimal(latest_usdtcnyx)
            elif check_type == 'xdag_cnyt':
                str_data = resp.text.replace('[','').replace(']','')
                list_data = str_data.split('},{')
                for data in list_data:
                    if not data.startswith('{'):
                        data = '{' + data
                    if not data.endswith('}'):
                        data += '}'
                    judgeData = json.loads(data).get('currency')
                    if judgeData == 'XDAG':
                        latest_price = json.loads(data).get('new_price')
            return {'code': 1, 'message': 'Success', 'data': latest_price}
        else:
            return {'code': 0, 'message': 'Failed',
                    'data': 'Failure to get the latest price! Response:' + str(resp.status_code)}

    def clean_data(self, data_type, demand_type):
        clean_data = {}
        data_API = Api()
        resp = data_API.entrust(data_type, demand_type)
        json_data = json.loads(resp).get('data')
        clean_data['type'] = demand_type
        clean_data['transaction_list'] = json_data.get('entrutsCur')
        return clean_data

    def check_depth(self, symbol):
        data_API = Api()
        resp = data_API.depth(symbol)
        json_data = json.loads(resp).get('data')
        sell = json_data['depth']['bids']
        buy = json_data['depth']['asks']
        return {"buyOne": buy[0][0] if buy else 0,"sellOne":sell[0][0] if sell else 0}

    def check(self, demand_type, latest_price, minimum_limit, maximum_limit, min_count, max_count, count_float, price_float):
        data_type = 1
        price_temp = []
        buy_temp = []
        sell_temp = []
        demand_type = demand_type
        data_API = Api()
        check_data = self.clean_data(data_type, demand_type).get('transaction_list')
        depth_data = self.check_depth(demand_type)
        # print '================================================================================'
        # print depth_data
        # print '================================================================================'
        # exit()
        zoom_min_sell = float(latest_price) + float(minimum_limit)
        zoom_max_sell = float(latest_price) + float(maximum_limit)
        zoom_sell = Interval(zoom_min_sell, zoom_max_sell)

        zoom_min_buy = float(latest_price) - float(minimum_limit)
        zoom_max_buy = float(latest_price) - float(maximum_limit)
        zoom_buy = Interval(zoom_min_buy, zoom_max_buy)
        # print('zoom_sell',zoom_sell)
        # print('zoom_buy',zoom_buy)


        # 买卖单数量
        buycountnum = 0
        sellcountnum = 0

        for data in check_data:
            if data.get('types') == u'买单':
                buycountnum += 1
                if data.get('price') not in zoom_buy:
                    new_price = round(random.uniform(zoom_min_buy, zoom_max_buy), price_float)
                    if new_price not in price_temp:
                        price_temp.append(new_price)
                    times = 0
                    while new_price in price_temp:
                        times += 1
                        new_price = round(random.uniform(zoom_min_buy, zoom_max_buy), price_float)
                        if new_price not in price_temp:
                            price_temp.append(new_price)
                            break
                        if times > 10:
                            print '无法获取到区间内唯一价格'
                            return
                    amount = round(random.uniform(min_count, max_count), count_float)
                    all_price = self.clean_data(data_type, demand_type).get('transaction_list')
                    translist = []
                    for trans_price in all_price:
                        if trans_price.get('types') == u'卖单':
                            translist.append(trans_price.get('price'))
                    if depth_data.get('sellOne') not in translist:
                        if new_price < depth_data.get('sellOne'):
                            print '================================================================================'
                            print demand_type + '即将取消id：' + str(data.get('id'))
                            time.sleep(0.2)
                            cancel = data_API.cancel(data.get('id'))
                            cancel_json = json.loads(cancel)
                            if cancel_json.get('code') == 200:
                                print cancel.encode('utf-8')
                                print demand_type + '即将上架价格：' + str(new_price) + '，数量：' + str(amount) + '，类型：buy。'
                                for one_price in all_price:
                                    if one_price.get('types') == u'卖单':
                                        sell_temp.append(one_price.get('price'))
                                times = 0
                                while new_price in sell_temp:
                                    times += 1
                                    new_price = round(random.uniform(zoom_min_buy, zoom_max_buy), price_float)
                                    if new_price not in sell_temp:
                                        break
                                    if times > 10:
                                        print '撤单成功，挂单时无法获取到区间内唯一价格'
                                        return
                                time.sleep(0.2)
                                place = data_API.place(new_price, amount, 'buy', demand_type)
                                place_json = json.loads(place)
                                if place_json.get('code') == 200:
                                    print place.encode('utf-8')
                                else:
                                    print demand_type + '买单委托失败。'
                            else:
                                print demand_type + '买单取消失败。'
                                print cancel_json.get('msg').encode('utf-8')
                    else:
                        print '================================================================================'
                        print demand_type + '即将取消id：' + str(data.get('id'))
                        time.sleep(0.2)
                        cancel = data_API.cancel(data.get('id'))
                        cancel_json = json.loads(cancel)
                        if cancel_json.get('code') == 200:
                            print cancel.encode('utf-8')
                            print demand_type + '即将上架价格：' + str(new_price) + '，数量：' + str(amount) + '，类型：buy。'
                            for one_price in all_price:
                                if one_price.get('types') == u'卖单':
                                    sell_temp.append(one_price.get('price'))
                            times = 0
                            while new_price in sell_temp:
                                times += 1
                                new_price = round(random.uniform(zoom_min_buy, zoom_max_buy), price_float)
                                if new_price not in sell_temp:
                                    break
                                if times > 10:
                                    print '撤单成功，挂单时无法获取到区间内唯一价格'
                                    return
                            time.sleep(0.2)
                            place = data_API.place(new_price, amount, 'buy', demand_type)
                            place_json = json.loads(place)
                            if place_json.get('code') == 200:
                                print place.encode('utf-8')
                            else:
                                print demand_type + '买单委托失败。'
                        else:
                            print demand_type + '买单取消失败。'
                            print cancel_json.get('msg').encode('utf-8')
            elif data.get('types') == u'卖单':
                sellcountnum += 1
                if data.get('price') not in zoom_sell:
                    new_price = round(random.uniform(zoom_min_sell, zoom_max_sell), price_float)
                    if new_price not in price_temp:
                        price_temp.append(new_price)
                    times = 0
                    while new_price in price_temp:
                        times += 1
                        new_price = round(random.uniform(zoom_min_sell, zoom_max_sell), price_float)
                        if new_price not in price_temp:
                            price_temp.append(new_price)
                            break
                        if times > 10:
                            print '无法获取到区间内唯一价格'
                            return
                    amount = round(random.uniform(min_count, max_count), count_float)
                    all_price = self.clean_data(data_type, demand_type).get('transaction_list')
                    translist = []
                    for trans_price in all_price:
                        if trans_price.get('types') == u'卖单':
                            translist.append(trans_price.get('price'))
                    if depth_data.get('buyOne') not in translist:
                        if new_price > depth_data.get('buyOne'):
                            print '================================================================================'
                            print demand_type + '即将取消id：' + str(data.get('id'))
                            time.sleep(0.2)
                            cancel = data_API.cancel(data.get('id'))
                            cancel_json = json.loads(cancel)
                            if cancel_json.get('code') == 200:
                                print cancel.encode('utf-8')
                                print demand_type + '即将上架价格：' + str(new_price) + '，数量：' + str(amount) + '，类型：sell。'
                            
                                for one_price in all_price:
                                    if one_price.get('types') == u'买单':
                                        buy_temp.append(one_price.get('price'))
                                times = 0
                                while new_price in buy_temp:
                                    times += 1
                                    new_price = round(random.uniform(zoom_min_sell, zoom_max_sell), price_float)
                                    if new_price not in buy_temp:
                                        break
                                    if times > 10:
                                        print '撤单成功，挂单时无法获取到区间内唯一价格'
                                        return
                                time.sleep(0.2)
                                place = data_API.place(new_price, amount, "sell", demand_type)
                                place_json = json.loads(place)
                                if place_json.get('code') == 200:
                                    print place.encode('utf-8')
                                else:
                                    print demand_type + '卖单委托失败。'
                            else:
                                print demand_type + '卖单取消失败。'
                                print cancel_json.get('msg').encode('utf-8')
                    else:
                        print '================================================================================'
                        print demand_type + '即将取消id：' + str(data.get('id'))
                        time.sleep(0.2)
                        cancel = data_API.cancel(data.get('id'))
                        cancel_json = json.loads(cancel)
                        if cancel_json.get('code') == 200:
                            print cancel.encode('utf-8')
                            print demand_type + '即将上架价格：' + str(new_price) + '，数量：' + str(amount) + '，类型：sell。'
                        
                            for one_price in all_price:
                                if one_price.get('types') == u'买单':
                                    buy_temp.append(one_price.get('price'))
                            times = 0
                            while new_price in buy_temp:
                                times += 1
                                new_price = round(random.uniform(zoom_min_sell, zoom_max_sell), price_float)
                                if new_price not in buy_temp:
                                    break
                                if times > 10:
                                    print '撤单成功，挂单时无法获取到区间内唯一价格'
                                    return
                            time.sleep(0.2)
                            place = data_API.place(new_price, amount, "sell", demand_type)
                            place_json = json.loads(place)
                            if place_json.get('code') == 200:
                                print place.encode('utf-8')
                            else:
                                print demand_type + '卖单委托失败。'
                        else:
                            print demand_type + '卖单取消失败。'
                            print cancel_json.get('msg').encode('utf-8')
        print demand_type + ' check finished.\n'
        if buycountnum < 8:
            new_price = round(random.uniform(zoom_min_buy, zoom_max_buy), price_float)
            if new_price not in price_temp:
                price_temp.append(new_price)
            times = 0
            while new_price in price_temp:
                times += 1
                new_price = round(random.uniform(zoom_min_buy, zoom_max_buy), price_float)
                if new_price not in price_temp:
                    price_temp.append(new_price)
                    break
                if times > 10:
                    print '无法获取到区间内唯一价格'
                    return
            amount = round(random.uniform(min_count, max_count), count_float)
            print demand_type + '数量小,即将上架价格：' + str(new_price) + '，数量：' + str(amount) + '，类型：buy。'
            all_price = self.clean_data(data_type, demand_type).get('transaction_list')
            for one_price in all_price:
                if one_price.get('types') == u'卖单':
                    sell_temp.append(one_price.get('price'))
            times = 0
            while new_price in sell_temp:
                times += 1
                new_price = round(random.uniform(zoom_min_buy, zoom_max_buy), price_float)
                if new_price not in sell_temp:
                    break
                if times > 10:
                    print '数量小，挂单时无法获取到区间内唯一价格'
                    return
            time.sleep(0.2)
            place = data_API.place(new_price, amount, 'buy', demand_type)
            place_json = json.loads(place)
            if place_json.get('code') == 200:
                print place.encode('utf-8')
            else:
                print demand_type + '数量小买单委托失败。'
            print '买单数量完成检查.\n'
        if sellcountnum < 8:
            new_price = round(random.uniform(zoom_min_sell, zoom_max_sell), price_float)
            if new_price not in price_temp:
                price_temp.append(new_price)
            times = 0
            while new_price in price_temp:
                times += 1
                new_price = round(random.uniform(zoom_min_sell, zoom_max_sell), price_float)
                if new_price not in price_temp:
                    price_temp.append(new_price)
                    break
                if times > 10:
                    print '无法获取到区间内唯一价格'
                    return
            amount = round(random.uniform(min_count, max_count), count_float)
           
            print demand_type + '数量小，即将上架价格：' + str(new_price) + '，数量：' + str(amount) + '，类型：sell。'
            all_price = self.clean_data(data_type, demand_type).get('transaction_list')
            for one_price in all_price:
                if one_price.get('types') == u'买单':
                    buy_temp.append(one_price.get('price'))
            times = 0
            while new_price in buy_temp:
                times += 1
                new_price = round(random.uniform(zoom_min_sell, zoom_max_sell), price_float)
                if new_price not in buy_temp:
                    break
                if times > 10:
                    print '数量小，挂单时无法获取到区间内唯一价格'
                    return
            time.sleep(0.2)
            place = data_API.place(new_price, amount, "sell", demand_type)
            place_json = json.loads(place)
            if place_json.get('code') == 200:
                print place.encode('utf-8')
            else:
                print demand_type + '数量小，卖单委托失败。'
            print '卖单数量完成检查.\n'
            



if __name__ == '__main__':
    monitoring = Monitoring()
    print 'Start time:' + str(time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))) + '\n'
    check_types = ['eth_cnyt','wkb_cnyt','xdag_cnyt']
    # check_types = ['xdag_cnyt']
    # check_types = ['eth_cnyt']
    for check_type in check_types:
        if check_type == 'eth_cnyt':
            get_latest_URL = 'https://data.gateio.io/api2/1/ticker/eth_usdt'
            minimum_limit = 3  # eth波动区间
            maximum_limit = 13
            min_count = 0.01  # eth数量区间及精度
            max_count = 1
            count_float = 3
            price_float = 2  # eth价格精度
        elif check_type == 'wkb_cnyt':
            get_latest_URL = 'http://www.wkj.link/ajax/getMarketInfo/coin/wkb'
            minimum_limit = 0.0005  # wkb波动区间
            maximum_limit = 0.005
            min_count = 10  # wkb数量区间及精度
            max_count = 30
            count_float = 1
            price_float = 4  # wkb价格精度
        elif check_type == 'sc_cnyt':
            get_latest_URL = 'https://www.binance.co/api/v1/ticker/24hr?symbol=SCBTC'
            minimum_limit = 0.00002  # sc波动区间
            maximum_limit = 0.001
            min_count = 100  # sc数量区间及精度
            max_count = 3000
            count_float = 1
            price_float = 5  # sc价格精度
        elif check_type == 'ae_cnyt':
            get_latest_URL = 'https://data.gateio.io/api2/1/ticker/ae_usdt'
            minimum_limit = 0.0003  # ae波动区间
            maximum_limit = 0.005
            min_count = 2  # ae数量区间及精度
            max_count = 30
            count_float = 2
            price_float = 4  # ae价格精度
        elif check_type == 'xdag_cnyt':
            get_latest_URL = 'https://api.vbitex.com/Usdmark/tickers'
            minimum_limit = 0.00001  # xdag波动区间
            maximum_limit = 0.0008
            min_count = 20  # xdag数量区间及精度
            max_count = 80
            count_float = 0
            price_float = 6  # xdag价格精度
        get_price = monitoring.get_latest_price(check_type, get_latest_URL)
        # print get_price
        # print get_price.get('data')
        # exit()
        if get_price.get('code') == 1:
            latest_price = get_price.get('data')
            print check_type + ' price:' + str(latest_price)
            monitoring.check(check_type, latest_price, minimum_limit, maximum_limit, min_count, max_count, count_float, price_float)
        else:
            print(get_price.get('data'))
    # json_data = monitoring.check_depth('eth_cnyt')
    # print json_data
