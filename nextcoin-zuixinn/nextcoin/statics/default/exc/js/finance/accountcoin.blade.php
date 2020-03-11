@extends('layouts/app'))
@section('content')

        <div class="container">
            <div class="row financial-wrap">
                <div class="col-xs-12 financial">
<!-------------------------------- 边栏导航 -------------------------------->
                    <div class="col-xs-3 financial-sidebar">
                        <h2 class="financial-sidebar-tetil">{{__('finance.account-center')}}</h2>
                        <ul class="financial-sidebar-nav">
                            <li class="financial-sidebar-bar">
                                <a href="{{route('commission')}}">{{__('finance.account-recommend')}}</a>
                            </li>
                            <li class="financial-sidebar-bar">
                                <a href="{{route('coinDeposit')}}">{{__('finance.account-deposit')}}</a>
                            </li>
                            <li class="financial-sidebar-bar">
                                <a href="{{route('coinWithdraw')}}">{{__('finance.account-withdraw')}}</a>
                            </li>
                            <li class="financial-sidebar-bar">
                                <a href="{{route('asset')}}">{{__('finance.account-asset')}}</a>
                            </li>
                            <li class="financial-sidebar-bar">
                                <a href="{{route('record')}}" >{{__('finance.account-record')}}</a>
                            </li>
                            <li class="financial-sidebar-bar">
                                <a href="{{route('accountcoin')}}" class="active">{{__('finance.account-account')}}</a>
                            </li>
                            <li class="financial-sidebar-bar">
                                <a href="{{route('finances')}}">{{__('finance.account-income')}}</a>
                            </li>
                        </ul>
                    </div>
<!---------------------------------- 内容详情 ----------------------------->
                    <div class="col-xs-9 financial-data">
                        <h1 class="col-xs-12 financial-data-tetil">{{__('finance.account-account')}}</h1>
                        <div class="col-xs-12 financial-data-nav">
                            @forelse($coinTypeList as $coinItem)

                                @if($coinItem['id'] == $coinType['id'])
                                    <a href="{{route('accountcoin')}}?symbol={{$coinItem['id']}}" class="left financial-data-navbar active">
                                        <span>{{$coinItem['shortname']}}</span>
                                    </a>
                                @else
                                    <a href="{{route('accountcoin')}}?symbol={{$coinItem['id']}}"  class="left financial-data-navbar">
                                        <span>{{$coinItem['shortname']}}</span>
                                    </a>
                                @endif

                            @empty
                            @endforelse
                        </div>

                        <input type="hidden" id="symbol" value="{{$coinType['id']}}">
                        <input type="hidden" id="coinName" value="{{$coinType['shortname']}}">
                        <div class="col-xs-12 funds-form">
                            <div class="funds-form-btn">
                                <button class="js-btn-addPack">{{__('finance.account-add')}}</button>
                            </div>
                            @foreach($fvirtualaddressWithdraws as $addressItem)
                            <div class="funds-form-item">
                                <label for="">
                                    <input type="text"  readonly value="{{$addressItem['fadderess']}}" />
                                    <input type="hidden"  value="{{$addressItem['fid']}}" />
                                </label>
                                <button class="iconfont icon-guanbi funds-item-delete coin-item-del" data-fid="{{$addressItem['fid']}}"></button>
                            </div>
                            @endforeach
                        </div>
                    </div>

                </div>
            </div>
        </div>



        <div  class="model">
            <div class="model-body">
                <div class="model-form-header clearfix">
                    <div class="model-form-tetil">
                        <h2>{{__('finance.account-add')}}</h2>
                    </div>
                    <div class="model-hide">
                        <button class="iconfont icon-guanbi"></button>
                    </div>
                </div>
                <div class="model-form-body">
                    <div class="model-form-item">
                        <p>{{__('finance.account-address')}}:</p>
                        <label for="">
                            <input type="text"  id="withdrawBtcAddr" value=""  class="form-site-text"/>
                        </label>
                    </div>
                    <div class="model-form-item">
                        <p>{{__('finance.account-remark')}}:</p>
                        <label for="">
                            <input type="text" id="withdrawBtcRemark" value="" />
                        </label>
                    </div>
                    <div class="model-form-item">
                        <p>{{__('finance.account-tradPassword')}}:</p>
                        <label for="">
                            <input type="text" id="withdrawBtcPass" value="" />
                        </label>
                    </div>
                    <div class="model-form-item">
                        <p>{{__('finance.account-googleCode')}}:</p>
                        <label for="">
                            <input type="text"  id="withdrawBtcAddrTotpCode" value="" />
                        </label>
                    </div>
                    <div class="model-form-item">
                        <p id="binderrortips" style="font-size: 12px; color:red;"></p>
                    </div>
                    <div class="model-form-item">
                        <button class="model-form-btn" id="withdrawBtcAddrBtn">{{__('finance.account-save')}}</button>
                    </div>
                </div>
            </div>
        </div>
@endsection

@section('js')
        <script src="{{asset('js/plugin/layer/bitDate.js')}}" type="text/javascript" charset="utf-8"></script>
        <script src="{{asset('js/plugin/jquery.qrcode.min.js')}}" type="text/javascript" charset="utf-8"></script>
        <script src="{{asset('js/finance/account.assets.js')}}" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript">
        	      $(".funds-item-delete").on({
        	          click:function(){
        	              $(this).siblings("label").children("input").val("");
        	          }
        	      })
            $('.js-btn-addPack').click(function () {
                $('.model').show();
            });


        </script>
    @endsection


