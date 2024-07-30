package ai.deepar.deepar_example.home

import ai.deepar.deepar_example.MainAppointmentActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.patient.PatientLoginActivity
import ai.deepar.deepar_example.patient.PatientMyAppointmentsActivity
import ai.deepar.deepar_example.patient.PatientProfileActivity
import ai.deepar.deepar_example.patient.adapter.DoctorCustomAdapter
import ai.deepar.deepar_example.patient.services.DoctorService
import android.annotation.SuppressLint
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class SettingsLogoutScreen : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var doctorService: DoctorService
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings_logout_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnNo = findViewById<Button>(R.id.btnLogoutNo)
        btnNo.setOnClickListener {
            finish()
        }

        val btnYes = findViewById<Button>(R.id.btnLogoutYes)
        btnYes.setOnClickListener {
//            startActivity(Intent(this@SettingsLogoutScreen, PatientLoginActivity::class.java))

            AlertDialog.Builder(this).apply {
                setTitle("Logout")
                setMessage("Do you really want to logout?")
                setPositiveButton("Yes") { _, _ ->
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this@SettingsLogoutScreen, PatientLoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                setNegativeButton("No", null)
            }.create().show()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.patient_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.profile -> {
//                val intent = Intent(this, PatientProfileActivity::class.java)
//                startActivity(intent)
//            }

//            R.id.news -> {
//                val intent = Intent(this, NewsActivity::class.java)
//                startActivity(intent)
//            }
            R.id.btnLogoutYes -> {
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

//    override fun onResume() {
//        super.onResume()
//        doctorService.getDoctors {
//            val adapter = DoctorCustomAdapter(this, it)
//            listView.adapter = adapter
//        }
//    }
}