#!/usr/bin/env bash

functionName="coldstarttest-helloworld-java"
memorySizes="128 256 512 1024 1536 2048 2560 3008"

for i in {1..10}
do
  for memorySize in $memorySizes
  do
    echo "iteration=$i memory=$memorySize"

    echo "Updating $functionName"
    aws lambda update-function-configuration --function-name $functionName --memory-size $memorySize > /dev/null

    echo "Invoking lambda"
    aws lambda invoke --function-name $functionName --payload "{}" ./lambda-response.json > /dev/null

    echo "Sleeping for 5 seconds ... "
    sleep 5
  done
done
