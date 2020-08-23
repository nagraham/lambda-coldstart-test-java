#!/usr/bin/env bash

memorySizes="256 512 1024 1536 2048 2560 3008"

functionName=$1
if [[ -z $functionName ]]; then
  echo "USAGE: test-warn-starts.sh <function_name>"
  exit 1
fi

for memorySize in $memorySizes
do
  echo "Starting for memory=$memorySize"

  echo "Updating $functionName memory size"
  aws lambda update-function-configuration --function-name $functionName --memory-size $memorySize > /dev/null

  echo "Wait five seconds ..."
  sleep 5

  for i in {1..50}
  do
    echo "Invoking the lambda memory=$memorySize ($i/50)"
    aws lambda invoke --function-name $functionName --payload "{}" ./lambda-response.json > /dev/null

    echo "Wait one second ..."
    sleep 1
  done
done
