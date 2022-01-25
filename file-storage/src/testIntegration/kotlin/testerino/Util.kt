package testerino

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.testcontainers.perSpec
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName

object Util {
    private val LOCALSTACK_IMAGE: DockerImageName = DockerImageName.parse("localstack/localstack:0.13.3")
    fun DescribeSpec.attachLocalstack(): LocalStackContainer {
        val localstackContainer = LocalStackContainer(LOCALSTACK_IMAGE).apply {
            withServices(LocalStackContainer.Service.S3)
            withEnv("HOSTNAME_EXTERNAL", "localhost")
        }
        listener(localstackContainer.perSpec())
        return localstackContainer
    }

}