package testerino

import aws.sdk.kotlin.services.s3.S3Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3ClientConfig {

    @Value("\${application.s3.region:us-east-1}")
    private lateinit var region: String

    @Bean
    fun s3Client(): S3Client = S3Client {
        region = this@S3ClientConfig.region
    }
}
