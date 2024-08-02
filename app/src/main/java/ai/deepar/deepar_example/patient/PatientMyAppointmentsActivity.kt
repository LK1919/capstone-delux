package ai.deepar.deepar_example.patient

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.patient.adapter.PatientAppointmentAdapter
import ai.deepar.deepar_example.patient.models.PatientAppointmentData
import ai.deepar.deepar_example.patient.services.PatientAppointmentService
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class PatientMyAppointmentsActivity : AppCompatActivity() {
  private lateinit var patientAppointmentsList : ListView

  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_patient_my_appointments)

    val btnMyAppointmentsBack = findViewById<ImageButton>(R.id.myAppointmentsBackBtn)
    btnMyAppointmentsBack.setOnClickListener {
      finish()
    }

    patientAppointmentsList = findViewById(R.id.patientAppointmentsList)
    val patientAppointmentService = PatientAppointmentService()
    val patientEmail = FirebaseAuth.getInstance().currentUser?.email

    patientAppointmentService.getAppointmentsForPatient(patientEmail!!) { appointments ->
      val adapter = PatientAppointmentAdapter(this,appointments)
      patientAppointmentsList.adapter = adapter
    }

    patientAppointmentsList.setOnItemLongClickListener { adapterView, _, i, _ ->
      val selectedAppointment = adapterView.getItemAtPosition(i) as PatientAppointmentData
      AlertDialog.Builder(this).apply {
        setTitle("Cancel Appointment")
        setMessage("Are you sure to cancel the Appointment?")
        setPositiveButton("Yes") { _, _ ->
          patientAppointmentService.deleteAppointment(
            patientEmail,
            selectedAppointment.date!!,
            selectedAppointment.id!!
          ) { success ->
            if (success) {
              Toast.makeText(this@PatientMyAppointmentsActivity, "Appointment Canceled", Toast.LENGTH_SHORT).show()
              patientAppointmentService.getAppointmentsForPatient(patientEmail) { updatedAppointments ->
                val newAdapter = PatientAppointmentAdapter(this@PatientMyAppointmentsActivity, updatedAppointments)
                patientAppointmentsList.adapter = newAdapter
              }
            } else {
              Toast.makeText(this@PatientMyAppointmentsActivity, "Appointment cannot be Canceled", Toast.LENGTH_SHORT).show()
            }
          }
        }
        setNegativeButton("No", null)
      }.create().show()
      true
    }
  }
}