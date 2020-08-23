#!/usr/bin/env bash

memorySizes="256 512 1024 1536 2048 2560 3008"

functionName=$1
if [[ -z $functionName ]]; then
  echo "USAGE: test-cold-starts.sh <function_name>"
  exit 1
fi

for i in {1..50}
do
  for memorySize in $memorySizes
  do
    echo "iteration=$i memory=$memorySize"

    echo "Updating $functionName"
    aws lambda update-function-configuration --function-name $functionName --memory-size $memorySize > /dev/null

    echo "Wait five seconds ..."
    sleep 5

    echo "Invoking the lambda"
    aws lambda invoke --function-name $functionName --payload "{}" ./lambda-response.json > /dev/null

    echo "Wait five seconds ..."
    sleep 1
  done
done
