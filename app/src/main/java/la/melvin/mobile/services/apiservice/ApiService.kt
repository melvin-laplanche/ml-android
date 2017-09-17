package la.melvin.mobile.services.apiservice

/**
 * ApiService describes the methods needed to create a crash reporting class
 */
interface ApiService {
    fun <T> create(service: Class<T>): T
}