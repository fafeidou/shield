## 如何根据test_demo字段分组，然后分组根据时间倒序里面取top1

> select last(record) from t group by test_demo order by timestamp desc 

```json
{
    "size":0,
    "query":{
        "bool":{
            "must":[
                {
                    "term":{
                        "userId":{
                            "value":"test",
                            "boost":1
                        }
                    }
                },
                {
                    "term":{
                        "tag":{
                            "value":"test",
                            "boost":1
                        }
                    }
                }
            ],
            "adjust_pure_negative":true,
            "boost":1
        }
    },
    "sort":[
        {
            "timestamp":{
                "order":"desc"
            }
        }
    ],
    "aggregations":{
        "group_by_host":{
            "terms":{
                "field":"test_demo",
                "size":100,
                "min_doc_count":1,
                "shard_min_doc_count":0,
                "show_term_doc_count_error":false,
                "order":[
                    {
                        "_count":"desc"
                    },
                    {
                        "_key":"asc"
                    }
                ]
            },
            "aggregations":{
                "latestRecords":{
                    "top_hits":{
                        "from":0,
                        "size":1,
                        "version":false,
                        "explain":false,
                        "sort":[
                            {
                                "timestamp":{
                                    "order":"desc"
                                }
                            }
                        ]
                    }
                }
            }
        }
    }
}

```



## es 聚合不准确分析

* https://learnku.com/elasticsearch/t/47303
* https://cloud.tencent.com/developer/article/1676920
* https://blog.csdn.net/laoyang360/article/details/107133008