curl --location 'http://localhost:8080/index/cat?endpoint=_cat&method=GET'

curl --location 'http://localhost:8080/index/cat?endpoint=_cat/templates&method=GET'

curl --location 'http://localhost:8080/index/cat?endpoint=/_index_template/bfe_mcv_log_app_driver&method=GET'

```dtd

{
    "index_templates":[
        {
            "name":"bfe_mcv_log_app_driver",
            "index_template":{
                "index_patterns":[
                    "bfe_mcv_log_app_driver-*"
                ],
                "template":{
                    "settings":{
                        "index":{
                            "lifecycle":{
                                "rollover_alias":"bfe_mcv_log_app_driver"
                            }
                        }
                    }
                },
                "composed_of":[
                    "bfe_mcv_log_app_driver"
                ],
                "priority":500
            }
        }
    ]
}

```

curl --location 'http://localhost:8080/index/cat?endpoint=/_template/bfe_mcv_log_app_driver*&method=GET'

curl --location 'http://localhost:8080/index/cat?endpoint=/_cat/templates/bfe_mcv_log_app_driver&method=GET'

/_cat/templates/<template_name>


curl --location 'http://localhost:8080/index/cat?endpoint=/_ilm/policy/all_policy?format=json&method=GET'

