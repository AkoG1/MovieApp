package com.example.movieapp.presentation.ui.scheduleNotification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentItemListDialogListDialogBinding
import com.example.movieapp.presentation.notifications.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class ScheduleNotificationDialogFragment : BottomSheetDialogFragment() {

    private val safeArgs: ScheduleNotificationDialogFragmentArgs by navArgs()

    private var _binding: FragmentItemListDialogListDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        binding.timePicker.setIs24HourView(true)
        binding.remindMe.setOnClickListener {
            scheduleNotification()
            Toast.makeText(requireContext(), getString(R.string.Scheduled), Toast.LENGTH_SHORT)
                .show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent = Intent(requireContext(), Notification::class.java)
        val message = getString(R.string.default_message) + safeArgs.title
        val uniqueNotificationId = generateUniqueId()
        intent.putExtra(titleExtra, getString(R.string.Title))
        intent.putExtra(messageExtra, message)
        intent.putExtra(idExtra, safeArgs.imdbId)
        intent.putExtra(reminderNotificationIdExtra, uniqueNotificationId)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            uniqueNotificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun generateUniqueId() = (Date().time % Int.MAX_VALUE).toInt()

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}