```json

{
  "query": {
    "bool": {
      "must": [
        {
          "range": {
            "timestamp": {
              "gte": "2023-01-01 00:00:00.000", 
              "lte": "2023-12-31 23:59:59.999"  
            }
          }
        }
      ]
    }
  },
  "sort": [
    {
      "timestamp": {
        "order": "desc" 
      }
    },
    {
      "id": {
        "order": "desc" 
      }
    }
  ]
}

```




```json
PUT /my_index
{
  "settings": {
    "number_of_shards": 1,    
    "number_of_replicas": 0   
  },
  "mappings": {
    "properties": {
      "timestamp": {
        "type": "date",       
        "format": "yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd||epoch_millis" 
      },
      "id": {
        "type": "integer"     
      },
      "long_text": {
        "type": "text"        
      }
    }
  }
}
```

```json
PUT /my_index_2
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 0,
    "index.sort.field": ["timestamp", "id"],  // 指定排序字段
    "index.sort.order": ["desc", "desc"]      // 指定排序顺序
  },
  "mappings": {
    "properties": {
      "timestamp": {
        "type": "date",
        "format": "yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd||epoch_millis"
      },
      "id": {
        "type": "integer"
      },
      "long_text": {
        "type": "text"
      }
    }
  }
}
```
