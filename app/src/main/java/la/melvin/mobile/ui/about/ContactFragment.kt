package la.melvin.mobile.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.android.KodeinSupportFragment
import com.github.salomonbrys.kodein.instance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_contact.*
import la.melvin.mobile.FragmentInstancer
import la.melvin.mobile.R
import la.melvin.mobile.ui.BaseAdapter
import la.melvin.mobile.ui.OneLineTwoIconsCell
import la.melvin.mobile.ui.OneLineTwoIconsCellAdapter
import la.melvin.mobile.ui.users.User
import la.melvin.mobile.ui.users.UserApiService


/**
 *
 */
class ContactFragment : KodeinSupportFragment(), BaseAdapter.OnClickListener {
    private val TAG: String = ContactFragment::class.simpleName!!

    private val TYPE_PHONE = 0
    private val TYPE_EMAIL = 1
    private val TYPE_LINKEDIN = 2
    private val TYPE_FACEBOOK = 3
    private val TYPE_TWITTER = 4

    private val userApiService: UserApiService by instance()
    private var disposableFeaturedUser: Disposable? = null

    private var contactInfo: MutableList<OneLineTwoIconsCell> = mutableListOf()
    private var featuredUser: User? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val adapter = OneLineTwoIconsCellAdapter(contactInfo, 4)
        adapter.setOnClickListener(this)

        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = adapter

        // fetch the user's contact info
        disposableFeaturedUser = userApiService.getFeatured()
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe(
                        { user -> setContactInfo(user) },
                        { err -> print(err.message) }
                )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // We make sure to dispose the subscriptions in case the view gets destroyed before the
        // queries finished
        disposableFeaturedUser?.isDisposed.let { isDisposed ->
            if (isDisposed == false) {
                disposableFeaturedUser?.dispose()
            }
        }
    }

    // setContactInfo replaces the content of the current list by the data from the provided user
    private fun setContactInfo(user: User) {
        contactInfo.clear()
        featuredUser = user

        user.phone_number?.let { number ->
            contactInfo.add(OneLineTwoIconsCell(R.drawable.ic_phone, number, TYPE_PHONE, R.drawable.ic_textsms))
        }
        user.public_email?.let { email ->
            contactInfo.add(OneLineTwoIconsCell(R.drawable.ic_email, email, TYPE_EMAIL))
        }
        user.linkedin_custom_url?.let { _ ->
            contactInfo.add(OneLineTwoIconsCell(R.drawable.ic_linkedin, "Check out on LinkedIn", TYPE_LINKEDIN))
        }
        user.twitter_username?.let { _ ->
            contactInfo.add(OneLineTwoIconsCell(R.drawable.ic_twitter, "Check out on Twitter", TYPE_TWITTER))
        }
        user.facebook_username?.let { _ ->
            contactInfo.add(OneLineTwoIconsCell(R.drawable.ic_facebook, "Check out on Facebook", TYPE_FACEBOOK))
        }

        recycleView.adapter.notifyDataSetChanged()
    }

    override fun onItemClick(view: View, position: Int) {
        featuredUser?.let { user ->
            val item = contactInfo[position]
            when (item.id) {
                TYPE_PHONE -> {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:" + user.phone_number)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Could not start ACTION_DIAL")
                    }
                }
                TYPE_EMAIL -> {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("mailto:" + user.public_email)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Could not start ACTION_SENDTO for email")
                    }
                }
                TYPE_TWITTER -> {
                    // If the Twitter app is installed we open it, otherwise we open the browser
                    val intent = try {
                        context.packageManager.getPackageInfo("com.twitter.android", 0)
                        Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + user.twitter_username))
                    } catch (e: Exception) {
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + user.twitter_username))
                    }

                    if (intent.resolveActivity(context.packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Could not start ACTION_VIEW for twitter")
                    }
                }
                TYPE_FACEBOOK -> {
                    // If the facebook app is installed we open it, otherwise we open the browser
                    val intent = try {
                        Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + user.facebook_username))
                    } catch (e: Exception) {
                        Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/appetizerandroid" + user.facebook_username))
                    }

                    if (intent.resolveActivity(context.packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Could not start ACTION_VIEW for facebook")
                    }
                }
                TYPE_LINKEDIN -> {
                    // If the linkedin app is installed we open it, otherwise we open the browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/" + user.linkedin_custom_url))
                    try {
                        val linkedInPackage = "com.linkedin.android"
                        context.packageManager.getPackageInfo(linkedInPackage, 0)
                        intent.`package` = linkedInPackage
                    } catch (e: Exception) {
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + user.twitter_username))
                    }

                    if (intent.resolveActivity(context.packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Could not start ACTION_VIEW for linkedin")
                    }
                }
                else -> {
                    Log.e(TAG, "Unhandled item with type " + item.id.toString())
                }
            }
        }
    }

    override fun onRightIconClick(view: View, position: Int) {
        featuredUser?.let { user ->
            val item = contactInfo[position]
            when (item.id) {
                TYPE_PHONE -> {
                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("smsto:" + user.phone_number)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Could not start ACTION_SENDTO for text")
                    }
                }
                else -> {
                    Log.e(TAG, "Unhandled item with type " + item.id.toString())
                }
            }
        }
    }

    companion object : FragmentInstancer {
        override fun newInstance(): ContactFragment {
            return ContactFragment()
        }
    }
}
