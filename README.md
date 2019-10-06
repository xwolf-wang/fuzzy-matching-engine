# fuzzy-matching-engine

#### 项目介绍
本项目内容为Spring boot + fuzzyWuzzy + Camel + Openshift。如您觉得该项目对您有用，欢迎点击右上方的Star按钮，给予支持！！

#### 软件架构
软件架构说明


#### 安装教程

1. Docker
2. OpenShift Origin
3. xxxx

#### 使用说明

1. Trade list
fusion_execution.csv
fusion_fill.csv

2. Rule json
------------
   {
       "ruleName": "fusionRule",
       "leftTradeType": "channel1",
       "leftPrimaryKey": "oraderId",
       "rightTradeType": "channel2",
       "rightPrimaryKey": "tradeId",
       "cutoffRatio": 90,
       "avgPrecision": 0.2,
       "matchFields": [
         {
           "leftField": "tradePrice",
           "rightField": "trdPrice",
           "matchingType": "avg"
         },
         {
           "leftField": "quantity",
           "rightField": "qty",
           "matchingType": "mandatory_aggregate"
         },
         {
           "leftField": "firm",
           "rightField": "firm",
           "matchingType": "mandatory"
         },
         {
           "leftField": "cust",
           "rightField": "customer",
           "matchingType": "mandatory"
         },
         {
           "leftField": "user",
           "rightField": "user",
           "matchingType": "fuzzy"
         },
         {
           "leftField": "exec",
           "rightField": "execBy",
           "matchingType": "fuzzy"
         }
       ]
     }



3. sample trade
fill
{"oraderId":"1102944","quantity":"1","tradePrice":"105.01","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS"}
{"oraderId":"1102944","quantity":"1","tradePrice":"100.01","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS"}
{"oraderId":"1102944","quantity":"2","tradePrice":"100.001","firm":"fxaa","cust":"3m","user":"ming3","exec":"NAS"}
{"oraderId":"1102944","quantity":"2","tradePrice":"100.03","firm":"fxaa","cust":"3m","user":"ming456","exec":"NAS234"}
{"oraderId":"1102944","quantity":"3","tradePrice":"99.9999","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS2"}
{"oraderId":"1102944","quantity":"4","tradePrice":"100.01","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS"}
{"oraderId":"1102944","quantity":"5","tradePrice":"100.01","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS"}
{"oraderId":"1102944","quantity":"6","tradePrice":"100.01","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS"}
{"oraderId":"1102944","quantity":"7","tradePrice":"100.01","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS"}
{"oraderId":"1102944","quantity":"8","tradePrice":"100.01","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS"}
{"oraderId":"1102944","quantity":"10","tradePrice":"100.01","firm":"fxaa","cust":"3m","user":"ming","exec":"NAS"}

fusion_execution
{"tradeId":"2102944","qty":"8","trdPrice":"100.01","firm":"fxaa","customer":"3m","user":"ming","execBy":"NAS"}

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [http://git.mydoc.io/](http://git.mydoc.io/)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)