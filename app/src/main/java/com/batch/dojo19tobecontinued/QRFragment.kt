package com.batch.dojo19tobecontinued

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.AndroidRuntimeException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_qr.*

class QRFragment: Fragment() {

    private val FULLNAME = "FULLNAME"
    private val GITHUB = "GITHUB"
    private val TWITTER = "TWITTER"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = PreferenceManager.getDefaultSharedPreferences(context)

        val fullName = pref.getString(FULLNAME, "")
        var newFullName = ""
        if (fullName!!.contains(" ")) {
            newFullName = fullName.replace(" ", "%20")
        }
        val gitHub = pref.getString(GITHUB, "")
        val twitter = pref.getString(TWITTER, "")

        val data = "ca-tech://dojo/share?iam=${newFullName}&tw=${twitter}&gh=${gitHub}"

        try {
            val barCodeEncoder = BarcodeEncoder()
            val bitmap = barCodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 500, 500)
            qrImage.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            throw AndroidRuntimeException("Barcode Error", e)
        }

        backButton.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.remove(this)
            transaction?.commit()
        }
    }



}