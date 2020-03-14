package com.batch.dojo19tobecontinued.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batch.dojo19tobecontinued.R
import kotlinx.android.synthetic.main.fragment_mypage.*

class MyPageFragment : Fragment() {

    private val viewModel: MyPageViewModel by lazy {
        ViewModelProvider(this).get(MyPageViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        val fullName = sharedPref.getString(getString(R.string.full_name_key), "")
        val githubID = sharedPref.getString(getString(R.string.github_key), "")
        val twitterID = sharedPref.getString(getString(R.string.twitter_key), "")

        full_name_edit_text.setText(fullName)
        github_edit_text.setText(githubID)
        twitter_edit_text.setText(twitterID)

        createQrButton.setOnClickListener {
            viewModel.onCreateQR(
                requireActivity(),
                full_name_edit_text.text,
                github_edit_text.text,
                twitter_edit_text.text
            )
        }
        viewModel.state.observe(this, Observer {
            checkState(it)
        })
    }

    private fun checkState(state: MyPageState) {
        val errorMessage = " "
        if (state.isFullNameEmpty) {
            full_name_text_input.error = errorMessage
            return
        } else {
            full_name_text_input.error = null
        }

        if (state.isGithubIDEmpty) {
            github_text_input.error = errorMessage
            return
        } else {
            github_text_input.error = null
        }

        if (state.isTwitterIDEmpty) {
            twitter_text_input.error = errorMessage
            return
        } else {
            twitter_text_input.error = null
        }

        if (state.qrBitmap != null) {
            val qrImageView = ImageView(context)
            qrImageView.setImageBitmap(state.qrBitmap)

            context?.let {
                AlertDialog.Builder(it)
                    .setView(qrImageView)
                    .setPositiveButton("閉じる", null)
                    .show()
            }
        }
    }
}