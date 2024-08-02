package ai.deepar.deepar_example.home

import ai.deepar.deepar_example.MainAppointmentActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.doctor.models.DoctorData
import ai.deepar.deepar_example.patient.AppointmentActivity
import ai.deepar.deepar_example.patient.PatientMyAppointmentsActivity
import ai.deepar.deepar_example.patient.PatientProfileActivity
import ai.deepar.deepar_example.patient.adapter.DoctorCustomAdapter
import ai.deepar.deepar_example.patient.services.DoctorService
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SetAppointment : AppCompatActivity() {
  private lateinit var listView: ListView
  private lateinit var doctorService: DoctorService

  private lateinit var userImage: String
  private lateinit var userName : String

  private val auth = FirebaseAuth.getInstance()
  private val db = FirebaseFirestore.getInstance()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_set_appointment)

    listView = findViewById(R.id.listView)
    doctorService = DoctorService()

    doctorService.getDoctors {
      val adapter = DoctorCustomAdapter(this, it)
      listView.adapter = adapter
    }

    val userEmail = auth.currentUser?.email
    if (userEmail != null) {
      db.collection("patients").document(userEmail).get()
        .addOnSuccessListener { documentSnapshot ->
          if (documentSnapshot.exists()) {
            userImage = documentSnapshot.getString("image") ?: ""
            val name = documentSnapshot.getString("first") ?: ""
            val surname = documentSnapshot.getString("last")
            userName = "$name $surname"
          }
        }
    }


    listView.setOnItemClickListener() { adapterView, view, i, l ->
      val selectedItem = adapterView.getItemAtPosition(i) as DoctorData
      Log.d("info", selectedItem.toString())

      val intent = Intent(this, AppointmentActivity::class.java)
//      intent.putExtra("name", selectedItem.first)
//      intent.putExtra("surname", selectedItem.last)
////      intent.putExtra("age", selectedItem.age)
//      intent.putExtra("field", selectedItem.field)
//      intent.putExtra("image", selectedItem.image)
//      intent.putExtra("email",selectedItem.email)
//      intent.putExtra("patientImage",userImage)
//      intent.putExtra("patientName",userName)
      startActivity(intent)

      true
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.patient_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.profile -> {
        val intent = Intent(this, PatientProfileActivity::class.java)
        startActivity(intent)
      }

      R.id.news -> {
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
      }
      R.id.logout -> {
        AlertDialog.Builder(this).apply {
          setTitle("Logout")
          setMessage("Do you really want to logout?")
          setPositiveButton("Yes") { _, _ ->
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, MainAppointmentActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
          }
          setNegativeButton("No", null)
        }.create().show()
      }
      R.id.appointments -> {
        val intent = Intent(applicationContext, PatientMyAppointmentsActivity::class.java)
        startActivity(intent)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onResume() {
    super.onResume()
    doctorService.getDoctors {
      val adapter = DoctorCustomAdapter(this, it)
      listView.adapter = adapter
    }
  }
}