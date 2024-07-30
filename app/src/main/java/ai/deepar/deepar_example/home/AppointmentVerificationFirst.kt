package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ai.deepar.deepar_example.R
import android.content.Intent
import android.widget.Button
import android.widget.Toast

class AppointmentVerificationFirst : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_appointment_verification_first)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val confirmAppointment = findViewById<Button>(R.id.confirmAppointmentBtn)
    confirmAppointment.setOnClickListener {
      Toast.makeText(this@AppointmentVerificationFirst, "Appointment Confirmed", Toast.LENGTH_SHORT).show()
      startActivity(Intent(this@AppointmentVerificationFirst, BottomNavigation::class.java))
    }
  }
}