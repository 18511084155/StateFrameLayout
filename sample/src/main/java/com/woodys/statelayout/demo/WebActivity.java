package com.woodys.statelayout.demo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.pedant.SafeWebViewBridge.InjectedChromeClient;


public class WebActivity extends Activity {
    WebView webView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        webView=(WebView) findViewById(R.id.web_view);
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setDomStorageEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setAppCacheEnabled(false);
        webSetting.setAppCacheMaxSize(1024 * 1024 * 8);
        //启用地理定位
        webSetting.setGeolocationEnabled(true);
        String PICASSO_CACHE = "picasso-cache";
        webSetting.setGeolocationDatabasePath(PICASSO_CACHE);
        webSetting.setAppCacheEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }


        webView.setWebChromeClient(
            new CustomChromeClient("xyqbNative", HostJsScope.class)
        );

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String javascript="(function () {\nalert(2);})();";
                view.loadUrl("javascript:" + javascript);

                //String javascript = getIntent().getStringExtra("javascript");
                javascript="(function () {\n" +
                        "    alert('注入成功')\n" +
                        "    function sendData(data) {\n" +
                        "        window.xyqbNative.webViewAuthCollectionResults && window.xyqbNative.webViewAuthCollectionResults({\n" +
                        "            'data': data, //采集到的授权信息 \n" +
                        "        })\n" +
                        "    };\n" +
                        "\n" +
                        "    function sendProgress(json) {\n" +
                        "        window.xyqbNative.webViewAuthProgress && window.xyqbNative.webViewAuthProgress(json);\n" +
                        "    };\n" +
                        "\n" +
                        "    function isOverTime(time) {\n" +
                        "        time = time.replace('年', '-').replace('月', '-').replace('日', '');\n" +
                        "        var nowdate = new Date();\n" +
                        "        nowdate.setMonth(nowdate.getMonth() - 9);\n" +
                        "        var y = nowdate.getFullYear();\n" +
                        "        var m = nowdate.getMonth() + 1;\n" +
                        "        m = m >= 10 ? m : '0' + m;\n" +
                        "        var d = nowdate.getDate();\n" +
                        "        d = d >= 10 ? d : '0' + d;\n" +
                        "        var formatwdate = y + '-' + m + '-' + d;\n" +
                        "        return new Date(time).getTime() < new Date(formatwdate).getTime()\n" +
                        "    }\n" +
                        "\n" +
                        "    var urllList01 = ['https://member1.taobao.com/member/fresh/account_profile.htm', 'https://i.taobao.com/my_taobao.htm', 'https://member1.taobao.com/member/fresh/deliver_address.htm', 'https://pages.tmall.com/wow/jifen/act/point-details', 'https://buyertrade.taobao.com/trade/itemlist/list_bought_items.htm'];\n" +
                        "    var locationUrl01 = window.location.href;\n" +
                        "    var urlIndex01 = urllList01.indexOf(locationUrl01);\n" +
                        "    switch (urlIndex01) {\n" +
                        "        case 0:\n" +
                        "            window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            var trueName = '';\n" +
                        "            try {\n" +
                        "                trueName = document.querySelector('.elem-form > li:nth-child(1) > strong').innerText;\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            sendData({ type: 1, username: trueName });\n" +
                        "            sendProgress({ progress: 10 });\n" +
                        "            break;\n" +
                        "        case 1:\n" +
                        "            try {\n" +
                        "                document.getElementById('J_MyAlipayInfo').dispatchEvent(new Event('mouseover'))\n" +
                        "                let timer = setInterval(() => {\n" +
                        "                    try {\n" +
                        "                        var box = document.querySelector('.i-dropdown-con');\n" +
                        "                        var allowCountDom = box.querySelector('#J_MyAlipayInfo > div > div > div > ul > li:nth-child(3) > span.hidden.J_HuaBeiMoney > em');\n" +
                        "                        if (allowCountDom) {\n" +
                        "                            clearInterval(timer);\n" +
                        "                            var allowCount = allowCountDom.innerText;\n" +
                        "                            var totalCount = box.querySelector('#J_MyAlipayInfo > div > div > div > ul > li:nth-child(3) > p.mFund-profit.J_HuaBeiMoney > span').innerText;\n" +
                        "                            var resObj = {\n" +
                        "                                type: 2,\n" +
                        "                                allowCount,\n" +
                        "                                totalCount\n" +
                        "                            };\n" +
                        "                            sendData(resObj);\n" +
                        "                        }\n" +
                        "                    } catch (error) {\n" +
                        "                        window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "                    }\n" +
                        "                }, 100)\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            break;\n" +
                        "        case 2:\n" +
                        "            try {\n" +
                        "                var addressListDom = document.querySelectorAll('.thead-tbl-address');\n" +
                        "                var addressList = [];\n" +
                        "                for (var i = 0; i < addressListDom.length; i++) {\n" +
                        "                    let hh = addressListDom[i].querySelectorAll('td');\n" +
                        "                    var obj = {\n" +
                        "                        receiver: hh[0].innerText,\n" +
                        "                        areaDetail: hh[1].innerText,\n" +
                        "                        addressDetail: hh[2].innerText,\n" +
                        "                        zipCode: hh[3].innerText,\n" +
                        "                        phone: hh[4].innerText.replace(/s+/g, ''),\n" +
                        "                    }\n" +
                        "                    addressList.push(obj)\n" +
                        "                }\n" +
                        "                sendData({ type: 3, addressList });\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            break;\n" +
                        "        case 3:\n" +
                        "            var integral = {};\n" +
                        "            var pageIndex = 1;\n" +
                        "            var isOver = false;\n" +
                        "            integral.totalScore = document.querySelector('#J_pointSummary > div > div.item.valid > span.point').innerText;\n" +
                        "            integral.items = [];\n" +
                        "            integral.type = 4;\n" +
                        "            function getIntegralData() {\n" +
                        "                pageIndex++;\n" +
                        "                var itemList = document.querySelectorAll('.item-list li');\n" +
                        "                for (var i = 0; i < itemList.length; i++) {\n" +
                        "                    let obj = {};\n" +
                        "                    obj.id = itemList[i].querySelector('div.why > span').innerText;\n" +
                        "                    obj.source = itemList[i].querySelector('div.why > a.title').innerText;\n" +
                        "                    obj.scoreChange = itemList[i].querySelector('div.what > span').innerText;\n" +
                        "                    obj.createTime = itemList[i].querySelector('div.when').innerText;\n" +
                        "                    isOver = isOverTime(obj.createTime);\n" +
                        "                    if (isOver) {\n" +
                        "                        break;\n" +
                        "                    }\n" +
                        "                    obj.mark = itemList[i].querySelector('div.notes').innerText;\n" +
                        "                    integral.items.push(obj);\n" +
                        "                }\n" +
                        "                if (!isOver) {\n" +
                        "                    document.querySelector('#J_pointPager > div > div > div.mui-page-num > a.mui-page-next').click();\n" +
                        "                    var timer = setInterval(() => {\n" +
                        "                        let pi = document.querySelector('#J_pointPager > div > div > div.mui-page-num > b.mui-page-cur').innerText == pageIndex;\n" +
                        "                        if (pi) {\n" +
                        "                            clearInterval(timer);\n" +
                        "                            getIntegralData();\n" +
                        "                        }\n" +
                        "                    }, 100)\n" +
                        "                } else {\n" +
                        "                    sendData(integral);\n" +
                        "                }\n" +
                        "\n" +
                        "            }\n" +
                        "            try {\n" +
                        "                getIntegralData();\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            break;\n" +
                        "        case 4:\n" +
                        "            var goosList = [];\n" +
                        "            var pageIndex = 1;\n" +
                        "            var isOver = false;\n" +
                        "            function selectData() {\n" +
                        "                pageIndex++;\n" +
                        "                var tableList = document.querySelectorAll('.js-order-container table');\n" +
                        "                for (var i = 0; i < tableList.length; i++) {\n" +
                        "                    var obj = {};\n" +
                        "                    var tbody = tableList[i].querySelectorAll('tbody tr');\n" +
                        "                    var length = tbody.length;\n" +
                        "                    obj.createTime = tbody[0].querySelector('.bought-wrapper-mod__create-time___yNWVS').innerText.replace(/s+/g, '')\n" +
                        "                    isOver = isOverTime(obj.createTime);\n" +
                        "                    if (isOver) {\n" +
                        "                        break;\n" +
                        "                    }\n" +
                        "                    obj.id = tbody[0].querySelector('td.bought-wrapper-mod__head-info-cell___29cDO > span > span:nth-child(3)').innerText;\n" +
                        "                    obj.totalMoney = tbody[1].querySelector('div.price-mod__price___157jz > p > strong > span:nth-child(2)').innerText;\n" +
                        "                    obj.status = tbody[1].querySelector('td:nth-child(6) > div > p > span').innerText;\n" +
                        "                    obj.goods = [];\n" +
                        "                    obj.goods.push({\n" +
                        "                        name: tbody[1].querySelector('td:nth-child(1) > div > div:nth-child(2) > p:nth-child(1) > a:nth-child(1) > span:nth-child(2)') ? tbody[1].querySelector('td:nth-child(1) > div > div:nth-child(2) > p:nth-child(1) > a:nth-child(1) > span:nth-child(2)').innerText : tbody[1].querySelector('td:nth-child(1) > div > div:nth-child(2) > p:nth-child(1) > span:nth-child(1) > span:nth-child(2)').innerText,\n" +
                        "                        count: tbody[1].querySelector('td:nth-child(3) > div > p').innerText,\n" +
                        "                        price: tbody[1].querySelector('td:nth-child(2) > div > p:nth-child(2) > span:nth-child(2)') ? tbody[1].querySelector('td:nth-child(2) > div > p:nth-child(2) > span:nth-child(2)').innerText : tbody[1].querySelector('td:nth-child(2) > div > p > span:nth-child(2)').innerText\n" +
                        "                    })\n" +
                        "                    for (var j = 2; j < length - 1; j++) {\n" +
                        "                        obj.goods.push({\n" +
                        "                            name: tbody[j].querySelector('td:nth-child(1) > div > div:nth-child(2) > p:nth-child(1) > a:nth-child(1) > span:nth-child(2)').innerText,\n" +
                        "                            count: tbody[j].querySelector('td:nth-child(3) > div > p').innerText,\n" +
                        "                            price: tbody[1].querySelector('td:nth-child(2) > div > p:nth-child(2) > span:nth-child(2)') ? tbody[1].querySelector('td:nth-child(2) > div > p:nth-child(2) > span:nth-child(2)').innerText : tbody[1].querySelector('td:nth-child(2) > div > p > span:nth-child(2)').innerText\n" +
                        "                        })\n" +
                        "                    }\n" +
                        "                    goosList.push(obj);\n" +
                        "                }\n" +
                        "                if (!isOver) {\n" +
                        "                    document.querySelector('#tp-bought-root > div.row-mod__row___30Zj1.js-actions-row-bottom > div:nth-child(2) > ul > li.pagination-item.pagination-item-' + pageIndex).click();\n" +
                        "                    var timer = setInterval(() => {\n" +
                        "                        let pi = document.querySelector('.pagination-item-active > a').innerText == pageIndex;\n" +
                        "                        if (pi) {\n" +
                        "                            clearInterval(timer);\n" +
                        "                            selectData();\n" +
                        "                        }\n" +
                        "                    }, 3000)\n" +
                        "                } else {\n" +
                        "                    sendData({ type: 5, goosList });\n" +
                        "                }\n" +
                        "            }\n" +
                        "            try {\n" +
                        "                selectData();\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            break;\n" +
                        "    }\n" +
                        "})();\n" +
                        "\n" +
                        "(function () {\n" +
                        "    alert('注入成功')\n" +
                        "    function sendData(data) {\n" +
                        "        window.xyqbNative.webViewAuthCollectionResults && window.xyqbNative.webViewAuthCollectionResults({\n" +
                        "            'data': data, //采集到的授权信息 \n" +
                        "        })\n" +
                        "    };\n" +
                        "\n" +
                        "    function sendProgress(json) {\n" +
                        "        window.xyqbNative.webViewAuthProgress && window.xyqbNative.webViewAuthProgress(json);\n" +
                        "    };\n" +
                        "\n" +
                        "    function isOverTime(time) {\n" +
                        "        time = time.replace('年', '-').replace('月', '-').replace('日', '');\n" +
                        "        var nowdate = new Date();\n" +
                        "        nowdate.setMonth(nowdate.getMonth() - 9);\n" +
                        "        var y = nowdate.getFullYear();\n" +
                        "        var m = nowdate.getMonth() + 1;\n" +
                        "        m = m >= 10 ? m : '0' + m;\n" +
                        "        var d = nowdate.getDate();\n" +
                        "        d = d >= 10 ? d : '0' + d;\n" +
                        "        var formatwdate = y + '-' + m + '-' + d;\n" +
                        "        return new Date(time).getTime() < new Date(formatwdate).getTime()\n" +
                        "    }\n" +
                        "\n" +
                        "    var urllList01 = ['https://member1.taobao.com/member/fresh/account_profile.htm', 'https://i.taobao.com/my_taobao.htm', 'https://member1.taobao.com/member/fresh/deliver_address.htm', 'https://pages.tmall.com/wow/jifen/act/point-details', 'https://buyertrade.taobao.com/trade/itemlist/list_bought_items.htm'];\n" +
                        "    var locationUrl01 = window.location.href;\n" +
                        "    var urlIndex01 = urllList01.indexOf(locationUrl01);\n" +
                        "    switch (urlIndex01) {\n" +
                        "        case 0:\n" +
                        "            window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            var trueName = '';\n" +
                        "            try {\n" +
                        "                trueName = document.querySelector('.elem-form > li:nth-child(1) > strong').innerText;\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            sendData({ type: 1, username: trueName });\n" +
                        "            sendProgress({ progress: 10 });\n" +
                        "            break;\n" +
                        "        case 1:\n" +
                        "            try {\n" +
                        "                document.getElementById('J_MyAlipayInfo').dispatchEvent(new Event('mouseover'))\n" +
                        "                let timer = setInterval(() => {\n" +
                        "                    try {\n" +
                        "                        var box = document.querySelector('.i-dropdown-con');\n" +
                        "                        var allowCountDom = box.querySelector('#J_MyAlipayInfo > div > div > div > ul > li:nth-child(3) > span.hidden.J_HuaBeiMoney > em');\n" +
                        "                        if (allowCountDom) {\n" +
                        "                            clearInterval(timer);\n" +
                        "                            var allowCount = allowCountDom.innerText;\n" +
                        "                            var totalCount = box.querySelector('#J_MyAlipayInfo > div > div > div > ul > li:nth-child(3) > p.mFund-profit.J_HuaBeiMoney > span').innerText;\n" +
                        "                            var resObj = {\n" +
                        "                                type: 2,\n" +
                        "                                allowCount,\n" +
                        "                                totalCount\n" +
                        "                            };\n" +
                        "                            sendData(resObj);\n" +
                        "                        }\n" +
                        "                    } catch (error) {\n" +
                        "                        window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "                    }\n" +
                        "                }, 100)\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            break;\n" +
                        "        case 2:\n" +
                        "            try {\n" +
                        "                var addressListDom = document.querySelectorAll('.thead-tbl-address');\n" +
                        "                var addressList = [];\n" +
                        "                for (var i = 0; i < addressListDom.length; i++) {\n" +
                        "                    let hh = addressListDom[i].querySelectorAll('td');\n" +
                        "                    var obj = {\n" +
                        "                        receiver: hh[0].innerText,\n" +
                        "                        areaDetail: hh[1].innerText,\n" +
                        "                        addressDetail: hh[2].innerText,\n" +
                        "                        zipCode: hh[3].innerText,\n" +
                        "                        phone: hh[4].innerText.replace(/s+/g, ''),\n" +
                        "                    }\n" +
                        "                    addressList.push(obj)\n" +
                        "                }\n" +
                        "                sendData({ type: 3, addressList });\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            break;\n" +
                        "        case 3:\n" +
                        "            var integral = {};\n" +
                        "            var pageIndex = 1;\n" +
                        "            var isOver = false;\n" +
                        "            integral.totalScore = document.querySelector('#J_pointSummary > div > div.item.valid > span.point').innerText;\n" +
                        "            integral.items = [];\n" +
                        "            integral.type = 4;\n" +
                        "            function getIntegralData() {\n" +
                        "                pageIndex++;\n" +
                        "                var itemList = document.querySelectorAll('.item-list li');\n" +
                        "                for (var i = 0; i < itemList.length; i++) {\n" +
                        "                    let obj = {};\n" +
                        "                    obj.id = itemList[i].querySelector('div.why > span').innerText;\n" +
                        "                    obj.source = itemList[i].querySelector('div.why > a.title').innerText;\n" +
                        "                    obj.scoreChange = itemList[i].querySelector('div.what > span').innerText;\n" +
                        "                    obj.createTime = itemList[i].querySelector('div.when').innerText;\n" +
                        "                    isOver = isOverTime(obj.createTime);\n" +
                        "                    if (isOver) {\n" +
                        "                        break;\n" +
                        "                    }\n" +
                        "                    obj.mark = itemList[i].querySelector('div.notes').innerText;\n" +
                        "                    integral.items.push(obj);\n" +
                        "                }\n" +
                        "                if (!isOver) {\n" +
                        "                    document.querySelector('#J_pointPager > div > div > div.mui-page-num > a.mui-page-next').click();\n" +
                        "                    var timer = setInterval(() => {\n" +
                        "                        let pi = document.querySelector('#J_pointPager > div > div > div.mui-page-num > b.mui-page-cur').innerText == pageIndex;\n" +
                        "                        if (pi) {\n" +
                        "                            clearInterval(timer);\n" +
                        "                            getIntegralData();\n" +
                        "                        }\n" +
                        "                    }, 100)\n" +
                        "                } else {\n" +
                        "                    sendData(integral);\n" +
                        "                }\n" +
                        "\n" +
                        "            }\n" +
                        "            try {\n" +
                        "                getIntegralData();\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            break;\n" +
                        "        case 4:\n" +
                        "            var goosList = [];\n" +
                        "            var pageIndex = 1;\n" +
                        "            var isOver = false;\n" +
                        "            function selectData() {\n" +
                        "                pageIndex++;\n" +
                        "                var tableList = document.querySelectorAll('.js-order-container table');\n" +
                        "                for (var i = 0; i < tableList.length; i++) {\n" +
                        "                    var obj = {};\n" +
                        "                    var tbody = tableList[i].querySelectorAll('tbody tr');\n" +
                        "                    var length = tbody.length;\n" +
                        "                    obj.createTime = tbody[0].querySelector('.bought-wrapper-mod__create-time___yNWVS').innerText.replace(/s+/g, '')\n" +
                        "                    isOver = isOverTime(obj.createTime);\n" +
                        "                    if (isOver) {\n" +
                        "                        break;\n" +
                        "                    }\n" +
                        "                    obj.id = tbody[0].querySelector('td.bought-wrapper-mod__head-info-cell___29cDO > span > span:nth-child(3)').innerText;\n" +
                        "                    obj.totalMoney = tbody[1].querySelector('div.price-mod__price___157jz > p > strong > span:nth-child(2)').innerText;\n" +
                        "                    obj.status = tbody[1].querySelector('td:nth-child(6) > div > p > span').innerText;\n" +
                        "                    obj.goods = [];\n" +
                        "                    obj.goods.push({\n" +
                        "                        name: tbody[1].querySelector('td:nth-child(1) > div > div:nth-child(2) > p:nth-child(1) > a:nth-child(1) > span:nth-child(2)') ? tbody[1].querySelector('td:nth-child(1) > div > div:nth-child(2) > p:nth-child(1) > a:nth-child(1) > span:nth-child(2)').innerText : tbody[1].querySelector('td:nth-child(1) > div > div:nth-child(2) > p:nth-child(1) > span:nth-child(1) > span:nth-child(2)').innerText,\n" +
                        "                        count: tbody[1].querySelector('td:nth-child(3) > div > p').innerText,\n" +
                        "                        price: tbody[1].querySelector('td:nth-child(2) > div > p:nth-child(2) > span:nth-child(2)') ? tbody[1].querySelector('td:nth-child(2) > div > p:nth-child(2) > span:nth-child(2)').innerText : tbody[1].querySelector('td:nth-child(2) > div > p > span:nth-child(2)').innerText\n" +
                        "                    })\n" +
                        "                    for (var j = 2; j < length - 1; j++) {\n" +
                        "                        obj.goods.push({\n" +
                        "                            name: tbody[j].querySelector('td:nth-child(1) > div > div:nth-child(2) > p:nth-child(1) > a:nth-child(1) > span:nth-child(2)').innerText,\n" +
                        "                            count: tbody[j].querySelector('td:nth-child(3) > div > p').innerText,\n" +
                        "                            price: tbody[1].querySelector('td:nth-child(2) > div > p:nth-child(2) > span:nth-child(2)') ? tbody[1].querySelector('td:nth-child(2) > div > p:nth-child(2) > span:nth-child(2)').innerText : tbody[1].querySelector('td:nth-child(2) > div > p > span:nth-child(2)').innerText\n" +
                        "                        })\n" +
                        "                    }\n" +
                        "                    goosList.push(obj);\n" +
                        "                }\n" +
                        "                if (!isOver) {\n" +
                        "                    document.querySelector('#tp-bought-root > div.row-mod__row___30Zj1.js-actions-row-bottom > div:nth-child(2) > ul > li.pagination-item.pagination-item-' + pageIndex).click();\n" +
                        "                    var timer = setInterval(() => {\n" +
                        "                        let pi = document.querySelector('.pagination-item-active > a').innerText == pageIndex;\n" +
                        "                        if (pi) {\n" +
                        "                            clearInterval(timer);\n" +
                        "                            selectData();\n" +
                        "                        }\n" +
                        "                    }, 3000)\n" +
                        "                } else {\n" +
                        "                    sendData({ type: 5, goosList });\n" +
                        "                }\n" +
                        "            }\n" +
                        "            try {\n" +
                        "                selectData();\n" +
                        "            } catch (error) {\n" +
                        "                window.xyqbNative.webViewAuthFailure && window.xyqbNative.webViewAuthFailure()\n" +
                        "            }\n" +
                        "            break;\n" +
                        "    }\n" +
                        "})();";
                webView.loadUrl("javascript:" + javascript);
            }
        });

       /* String javascript="alert(1);";
        wv.loadUrl("javascript:" + javascript);*/


        String url = getIntent().getStringExtra("url");
        if(null==url) url = "http://192.168.28.30:8080/test/index.html";
        //webView.loadUrl(url);
        webView.loadUrl("file:///android_asset/test.html");
    }

    public class CustomChromeClient extends InjectedChromeClient {

        public CustomChromeClient (String injectedName, Class injectedCls) {
            super(injectedName, injectedCls);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            // to do your work
            // ...
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onProgressChanged (WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // to do your work
            // ...
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            // to do your work
            // ...
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }
}
