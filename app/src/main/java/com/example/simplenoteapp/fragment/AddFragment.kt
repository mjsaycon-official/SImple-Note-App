package com.example.simplenoteapp.fragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.example.simplenoteapp.R
import com.example.simplenoteapp.entity.Note
import com.example.simplenoteapp.helpers.SharedPrefHelper
import com.example.simplenoteapp.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var etTitle: EditText
    private lateinit var etDesc: EditText
    private lateinit var btnSave: FloatingActionButton
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var note: Note
    private lateinit var returnDashboardInterface: ReturnDashboardInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etTitle = view.findViewById(R.id.idETTitle)
        etDesc = view.findViewById(R.id.idETDesc)
        btnSave = view.findViewById(R.id.idFABSave)
        returnDashboardInterface = activity as ReturnDashboardInterface
        SharedPrefHelper.initialize(requireContext())

        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val description = etDesc.text.toString()

            if (title.isEmpty() && description.isEmpty()) {
                Toast.makeText(requireContext(), "Title and description is required!", Toast.LENGTH_SHORT).show()
            } else {
                showNotification(title,description)
                if (this.note.title.isNotEmpty()) {
                    this.note.title = title
                    this.note.description = description
                    noteViewModel.updateNote(this.note)
                } else {
                    val currentTimeStamp: String = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").format(Date())
                    val note = Note(etTitle.text.toString(), etDesc.text.toString(), currentTimeStamp, "")
                    noteViewModel.addNote(note)
                }
                returnDashboardInterface.onDashboardReturn()
                etTitle.text.clear()
                etDesc.text.clear()
                SharedPrefHelper.clearSelectedNote()
            }
        }

        etTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun setupData() {
        note = Note("","","","") //default value for note
        val selectedNoteId = SharedPrefHelper.getSelectedNote(requireContext())
        if (selectedNoteId > -1) {
            noteViewModel.getNote(selectedNoteId).observe(viewLifecycleOwner) {noteObject->
                if (noteObject != null) {
                    note = noteObject
                    etTitle.setText(note.title)
                    etDesc.setText(note.description)
                }
            }
        } else {
            etTitle.text.clear()
            etDesc.text.clear()
        }
    }

    private fun showNotification(title: String, description: String) {
        val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val NOTE_CHANNEL_ID = "note_channel_id"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTE_CHANNEL_ID, "Note Notification", NotificationManager.IMPORTANCE_HIGH)
            channel.description = description
            channel.enableLights(false)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(requireContext(), NOTE_CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_baseline_event_note_24)
            .setContentTitle("New Note Added")
            .setContentText(title)
            .setContentInfo("Info")

        notificationManager.notify(1, notificationBuilder.build())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

}

interface ReturnDashboardInterface {
    fun onDashboardReturn()
}