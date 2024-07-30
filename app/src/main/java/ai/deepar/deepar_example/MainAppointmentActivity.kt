package ai.deepar.deepar_example

import ai.deepar.deepar_example.doctor.DoctorLoginActivity
import ai.deepar.deepar_example.patient.PatientLoginActivity
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainAppointmentActivity : AppCompatActivity() {
    private lateinit var patientButton : ImageView
    private lateinit var doctorButton : ImageView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_appointment)

        patientButton = findViewById(R.id.patientButtonImage)
        doctorButton = findViewById(R.id.doctorButtonImage)

        patientButton.setOnClickListener {
            val intent = Intent(this@MainAppointmentActivity, PatientLoginActivity::class.java)
            startActivity(intent)
        }

        doctorButton.setOnClickListener {
            val intent = Intent(this@MainAppointmentActivity, DoctorLoginActivity::class.java)
            startActivity(intent)
        }
    }
}