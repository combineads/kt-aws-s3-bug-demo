import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.content.decodeToString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class S3FileStorageService @Autowired constructor(val client: S3Client) : IFileStorageService {

    // todo encryption?
    override suspend fun uploadFile(key: String, content: String): Boolean {
        client.putObject {
            this.bucket = "testerino"
            this.key = key
            this.body = ByteStream.fromString(content)
        }
        return true
    }

    override suspend fun downloadFile(key: String): String = client.getObject(GetObjectRequest.invoke {
        this.bucket = "testerino"
        this.key = key
    }) {
        it.body!!.decodeToString()
    }
}
