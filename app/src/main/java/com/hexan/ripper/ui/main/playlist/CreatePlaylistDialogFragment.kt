package com.hexan.ripper.ui.main.playlist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hexan.ripper.R
import kotlinx.android.synthetic.main.fragment_dialog_create_playlist.view.*


/**
 * Created by James Cooper on 14/04/2018.
 */
class CreatePlaylistDialogFragment : DialogFragment() {

    private var onCreatePlaylistListener: OnCreatePlaylistListener? = null

    companion object {
        fun newInstance(targetFragment: Fragment): CreatePlaylistDialogFragment {
            val dialogFragment = CreatePlaylistDialogFragment()
            dialogFragment.setTargetFragment(targetFragment, 0)
            return dialogFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_dialog_create_playlist, container, false)
        v.createPlaylistButton.setOnClickListener {
            onCreatePlaylistListener?.onCreatePlaylist(v.playlistNameEdittext.text.toString())
            dismiss()
        }
        return v
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            this.onCreatePlaylistListener = targetFragment as OnCreatePlaylistListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnCreatePlaylistListener")
        }
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    interface OnCreatePlaylistListener {
        fun onCreatePlaylist(name: String)
    }
}