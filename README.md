# Lambda Cold Start Test with Java and DynamoDB

This repo contains code for two simple Java handlers:

- A "Hello World" handler that prints a message to the console
- A "PutTodo" handler that calls PutItem to write a simple item to Dynamo

These handlers can be used as a simple test for Lambda performance when using dynamoDB. A blog post with a write up using these functions can be found here: https://alexandergraham.dev/posts/lambda-cold-starts-java-nodejs-dynamodb

## Build and Deploy

To build the package:

```
mvn clean package
```

This will result in a fat jar at `target/lambda-coldstart-test-java-1.0-SNAPSHOT` that you can upload to a Lambda function via the AWS console.

## Run Cold Start Tests

```
./test-cold-starts.sh my-lambda-name
```

## Run Warm Lambda Tests

```
./test-warm-lambdas.sh my-lambda-name
```

