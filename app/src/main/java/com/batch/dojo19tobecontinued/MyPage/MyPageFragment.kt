package com.batch.dojo19tobecontinued.MyPage

import android.os.Bundle
import android.preference.PreferenceManager
import android.transition.ChangeBounds
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.batch.dojo19tobecontinued.QR.QRFragment
import com.batch.dojo19tobecontinued.R
import kotlinx.android.synthetic.main.fragment_mypage.*

class MyPageFragment: Fragment() {

    //private val pref = PreferenceManager.getDefaultSharedPreferences(context)
    private val FULLNAME = "FULLNAME"
    private val GITHUB = "GITHUB"
    private val TWITTER = "TWITTER"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val fullName = pref?.getString(FULLNAME, "")
        val gitHub = pref?.getString(GITHUB, "")
        val twitter = pref?.getString(TWITTER, "")

        fullNameText.setText(fullName)
        gitHubText.setText(gitHub)
        twitterText.setText(twitter)

        createQrButton.setOnClickListener { onCreateQRTapped() }
    }


    private fun onCreateQRTapped() {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        pref?.edit {

            putString(FULLNAME, fullNameText.text.toString())
            putString(GITHUB, gitHubText.text.toString())
            putString(TWITTER, twitterText.text.toString())
        }

        if (fullName.error != null) {
            fullName.error = null
        }

        if (gitHub.error != null) {
            gitHub.error = null
        }

        if (pref?.getString(FULLNAME, "") != "" && pref?.getString(GITHUB, "") != "") {

            val transaction = fragmentManager?.beginTransaction()
            val qrFragment = QRFragment()

            val ts = TransitionSet()
            ts.addTransition(ChangeBounds())
            qrFragment.enterTransition

            transaction?.add(R.id.qrContainer, qrFragment)
            transaction?.commit()

            val fullName = pref.getString(FULLNAME, "")
            var newFullName = ""
            if (fullName!!.contains(" ")) {
                newFullName = fullName.replace(" ", "%20")
            }
            val gitHub = pref.getString(GITHUB, "")
            val twitter = pref.getString(TWITTER, "")

            val data = "ca-tech://dojo/share?iam=${newFullName}&tw=${twitter}&gh=${gitHub}"

            Toast.makeText(view?.context, data, Toast.LENGTH_LONG).show()

            // Toast.makeText(view?.context, "CREATE QR!", Toast.LENGTH_LONG).show()
        } else if (pref.getString(FULLNAME, "") == "") {
            fullName.error = "The field is required."
        } else {
            gitHub.error = "The field is required."
        }

        fullName.clearFocus()
        gitHub.clearFocus()
        twitter.clearFocus()


        // TODO: QR生成したらポップアップ画面みたいの出してQR表示したい
        // できなかったら，普通にActivityで画面遷移か，Tab追加してQR表示
    }
}