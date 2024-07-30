package ai.deepar.deepar_example.doctor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.doctor.adapter.DoctorAppointmentAdapter
import ai.deepar.deepar_example.doctor.services.DoctorAppointmentService
import ai.deepar.deepar_example.MainAppointmentActivity
import ai.deepar.deepar_example.home.NewsActivity
import android.annotation.SuppressLint

class DoctorHomePageActivity : AppCompatActivity() {
  private lateinit var appointmentsList: android.widget.ListView
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_doctor_home_page)

    appointmentsList = findViewById(R.id.appointmentsList)

    val doctorAppointmentService = DoctorAppointmentService()
    val doctorEmail =
      com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.email

    doctorAppointmentService.getAppointmentsForDoctor(doctorEmail!!) { appointments ->
      android.util.Log.d("appointments", appointments.toString())
      val adapter = DoctorAppointmentAdapter(this, appointments)
      appointmentsList.adapter = adapter

      appointmentsList.setOnItemLongClickListener { _, _, position, _ ->
        val selectedAppointment = appointments[position]

        androidx.appcompat.app.AlertDialog.Builder(this)
          .setTitle("Cancel Appointment")
          .setMessage("Do you want to Cancel the Appointment?")
          .setPositiveButton("Yes") { _, _ ->
            doctorAppointmentService.deleteAppointment(
              doctorEmail,
              selectedAppointment.patientEmail!!,
              selectedAppointment.id!!
            ) { success ->
              if (success) {
                android.widget.Toast.makeText(
                  this,
                  "Appointment Deleted Successfully",
                  android.widget.Toast.LENGTH_SHORT
                ).show()
                doctorAppointmentService.getAppointmentsForDoctor(doctorEmail) { updatedAppointments ->
                  adapter.clear()
                  adapter.addAll(updatedAppointments)
                  adapter.notifyDataSetChanged()
                }
              } else {
                android.widget.Toast.makeText(
                  this,
                  "An Error Occurred while Deleting the Appointment.",
                  android.widget.Toast.LENGTH_SHORT
                ).show()
              }
            }
          }
          .setNegativeButton("No", null)
          .show()

        true
      }
    }
  }

  override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
    menuInflater.inflate(R.menu.doctor_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
    when (item.itemId) {
      R.id.doctor_profile -> {
        val intent = android.content.Intent(this, DoctorProfileActivity::class.java)
        startActivity(intent)
      }

      R.id.doctor_news -> {
        val intent = android.content.Intent(this, NewsActivity::class.java)
        startActivity(intent)
      }
      R.id.doctor_logout -> {
        androidx.appcompat.app.AlertDialog.Builder(this).apply {
          setTitle("Logout")
          setMessage("Are you sure you want to Logout?")
          setPositiveButton("Yes") { _, _ ->
            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
            val intent =
              android.content.Intent(applicationContext, MainAppointmentActivity::class.java)
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP or android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
          }
          setNegativeButton("No", null)
        }.create().show()
      }
    }
    return super.onOptionsItemSelected(item)
  }
}