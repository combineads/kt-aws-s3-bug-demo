import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName

@Configuration
class LocalstackConfig {

    companion object {
        private val LOCALSTACK_IMAGE: DockerImageName = DockerImageName.parse("localstack/localstack:0.13.3")
    }

    @Bean
    fun localstackContainer(): LocalStackContainer {
        val localstackContainer = LocalStackContainer(LOCALSTACK_IMAGE).apply {
            withServices(LocalStackContainer.Service.S3)
            withEnv("HOSTNAME_EXTERNAL", "localhost")
        }
        localstackContainer.start()
        return localstackContainer
    }

}
