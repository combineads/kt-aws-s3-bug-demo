package testerino

import aws.sdk.kotlin.runtime.endpoint.AwsEndpoint
import aws.sdk.kotlin.runtime.endpoint.AwsEndpointResolver
import aws.sdk.kotlin.services.s3.S3Client
import aws.smithy.kotlin.runtime.http.engine.ktor.KtorEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.localstack.LocalStackContainer

@Profile("integration-test")
@Configuration
class S3ClientConfig {

    @Autowired
    private lateinit var localstack: LocalStackContainer

    @Bean
    fun s3Client(): S3Client = S3Client {
        region = "us-east-1"
        httpClientEngine = KtorEngine()
        endpointResolver = AwsEndpointResolver { _, _ ->
            AwsEndpoint(
                localstack.getEndpointOverride(LocalStackContainer.Service.S3).toString()
            )
        }
    }

}
