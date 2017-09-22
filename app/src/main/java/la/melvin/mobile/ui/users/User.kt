package la.melvin.mobile.ui.users

/**
 * User describes a user as specified by the API
 */
class User {
    var id = ""
    var name = ""
    var first_name = ""
    var last_name = ""
    var email: String? = null
    var is_admin = false
    var picture: String? = null
    var public_email: String? = null
    var phone_number: String? = null
    var linkedin_custom_url: String? = null
    var facebook_username: String? = null
    var twitter_username: String? = null
    var is_featured = false

    fun fullName(): String {
        return first_name + " " + last_name
    }
}
