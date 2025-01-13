import os

# 获取环境变量
test_env = os.environ.get('TEST_ENV')
print(f"py param: {test_env}")
