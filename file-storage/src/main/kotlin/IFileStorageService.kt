interface IFileStorageService {
    suspend fun uploadFile(key: String, content: String): Boolean
    suspend fun downloadFile(key: String): String
}
