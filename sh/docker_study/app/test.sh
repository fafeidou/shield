#!/bin/bash
# shellcheck disable=SC2155,SC2028,SC2039
extract_and_export() {
  key=$1
  var_name=$2
  value=$(echo "$envData" | jq -r "$key")
#  export "$var_name"="$value"
  echo "$var_name"="$value"
}

injectEnv() {
  envData=$1
  echo $envData
  echo $2
  # 打印所有参数
  echo $@
}


injectEnvAtomConfig() {
  envData=$1
  extract_and_export '.name' NAME
  extract_and_export '.age' AGE
}


json='{
  "name": "John Doe",
  "age": 30,
  "email": "john.doe@example.com",
  "address": {
    "street": "123 Main St",
    "city": "Anytown",
    "zipcode": "12345"
  },
  "phone_numbers": [
    "+1234567890",
    "+0987654321"
  ]
}'

data='{
        "people": [
          { "name": "John", "age": 30 },
          { "name": "Alice", "age": 25 },
          { "name": "Bob", "age": 35 }
        ]
      }
'

echo "$data" | jq '.people[].name'

injectEnvAtomConfig "$json"


eval ./start-dev.sh


