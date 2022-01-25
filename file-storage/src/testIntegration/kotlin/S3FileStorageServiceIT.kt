import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.localstack.LocalStackContainer

@Profile("integration-test")
@SpringBootTest(classes = [S3FileStorageServiceIT.Companion.Context::class])
class S3FileStorageServiceIT : DescribeSpec() {

    companion object {
        @SpringBootApplication
        class Context
    }

    @Autowired
    private lateinit var localstackContainer: LocalStackContainer

    @Autowired
    private lateinit var fileStorageService: IFileStorageService

    init {
        register(SpringExtension)

        beforeSpec {
            localstackContainer.execInContainer("awslocal", "s3", "mb", "s3://testerino")
        }

        afterSpec {
            localstackContainer.execInContainer("awslocal", "s3", "rm", "s3://testerino")
        }

        describe("S3 File Upload Service") {
            it("can upload and download a file from S3") {
                // arrange
                val fileContent = javaClass.classLoader.getResource("all_star.txt")?.readText()
                    ?: error("Where's ma txt file 😡")
                val fileKey = "swamp"

                // act
                fileStorageService.uploadFile(fileKey, fileContent)
                val result = fileStorageService.downloadFile(fileKey)

                // assert
                result shouldBe fileContent
            }
        }
    }
}
