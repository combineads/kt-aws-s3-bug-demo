package testerino

import aws.sdk.kotlin.runtime.endpoint.AwsEndpoint
import aws.sdk.kotlin.runtime.endpoint.AwsEndpointResolver
import aws.sdk.kotlin.services.s3.S3Client
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.testcontainers.containers.localstack.LocalStackContainer
import testerino.Util.attachLocalstack


class S3FileStorageServiceIT : DescribeSpec({
    val localstackContainer = attachLocalstack()
    beforeSpec {
        localstackContainer.execInContainer("awslocal", "s3", "mb", "s3://testerino")
    }

    afterSpec {
        localstackContainer.execInContainer("awslocal", "s3", "rm", "s3://testerino")
    }
    describe("Testerino") {
        it("Can do the thing") {
            // arrange
            val fileContent = javaClass.classLoader.getResource("all_star.txt")?.readText()
                ?: error("Where's ma txt file 😡")
            val fileKey = "swamp"
            val client = S3Client {
                region = "us-east-1"
                endpointResolver = AwsEndpointResolver { _, _ ->
                    AwsEndpoint(
                        localstackContainer.getEndpointOverride(LocalStackContainer.Service.S3).toString()
                    )
                }
            }
            val service = S3FileStorageService(client)

            // act
            service.uploadFile(fileKey, fileContent)
            val result = service.downloadFile(fileKey)

            // assert
            result shouldBe fileContent
        }
    }
})