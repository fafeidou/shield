import os

# 获取环境变量
test_env = os.environ.get('TEST_ENV')
print(f"HOME 目录: {test_env}")
