import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

public class PutTodoHandler implements RequestHandler<Map<String, String>, String> {

    private static final String TABLE_NAME = "TestColdStarts20200811";
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_USER = "user123";

    private final DynamoDbClient dynamoDbClient;

    public PutTodoHandler() {
        try {
            dynamoDbClient = DynamoDbClient.builder()
                    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                    .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
                    .endpointOverride(new URI("https://dynamodb.us-west-2.amazonaws.com"))
                    .httpClientBuilder(UrlConnectionHttpClient.builder())
                    .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                    .build();

            // throw-away call to initialize lazy-loaded reflection code in SDK + establish SSL handshake
            // dynamoDbClient.describeTable(DescribeTableRequest.builder().tableName(TABLE_NAME).build());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to set URI for DynamoDB", e);
        }
    }

    @Override
    public String handleRequest(Map<String, String> stringStringMap, Context context) {
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(Map.of(
                        "pk", AttributeValue.builder().s(UUID.randomUUID().toString()).build(),
                        "title", AttributeValue.builder().s(FAKE_TITLE).build(),
                        "user", AttributeValue.builder().s(FAKE_USER).build()
                ))
                .returnValues(ReturnValue.NONE)
                .build();

        dynamoDbClient.putItem(putItemRequest);

        return null;
    }
}
