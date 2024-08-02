package ai.deepar.deepar_example.doctor.adapter

import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.doctor.models.DoctorAppointmentData
import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DoctorAppointmentAdapter(private val context: Activity, private val list:List<DoctorAppointmentData>) : ArrayAdapter<DoctorAppointmentData>(context,
    R.layout.custom_doctor_appointment, list)
{
    @SuppressLint("ViewHolder", "InflateParams", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = context.layoutInflater.inflate(R.layout.custom_doctor_appointment,null,true)
        val r_appName = rootView.findViewById<TextView>(R.id.r_appDName)
        val r_appDate = rootView.findViewById<TextView>(R.id.r_appDDate)
        val r_appHour = rootView.findViewById<TextView>(R.id.r_appDHour)
//        val r_appNote = rootView.findViewById<TextView>(R.id.r_appDNote)
//        val r_appImg = rootView.findViewById<ImageView>(R.id.r_appDImg)
        val appointment = list[position]
        r_appName.text = appointment.patientName
        r_appDate.text = "Date: " + appointment.date
        r_appHour.text = "Hour: " + appointment.hour
//        r_appNote.text = "Note: " + appointment.note

//        Glide.with(context).load(appointment.patientImg).into(r_appImg)
        return rootView
    }

}