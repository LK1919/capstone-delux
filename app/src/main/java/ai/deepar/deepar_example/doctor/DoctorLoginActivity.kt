package ai.deepar.deepar_example.doctor

import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.patient.PatientLoginActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class DoctorLoginActivity : AppCompatActivity() {
  private lateinit var btnDoctorLogin: Button
  private lateinit var btnDoctorRegister: Button
  private lateinit var editTxtDoctorLEmail: EditText
  private lateinit var editTxtDoctorLPassword: EditText
  private lateinit var user: FirebaseAuth
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_doctor_login)

    btnDoctorLogin = findViewById(R.id.btnDoctorLogin)
    btnDoctorRegister = findViewById(R.id.btnDoctorRegister)
    editTxtDoctorLEmail = findViewById(R.id.editTxtDoctorLEmail)
    editTxtDoctorLPassword = findViewById(R.id.editTxtDoctorLPassword)



    user = FirebaseAuth.getInstance()

    btnDoctorLogin.setOnClickListener {
      if (editTxtDoctorLEmail.text.toString() == "" || editTxtDoctorLPassword.text.toString() == "") {
        Toast.makeText(
          this,
          "Please fill in the information completely",
          Toast.LENGTH_LONG
        ).show()
      } else if (!editTxtDoctorLEmail.text.toString().contains("@doctor")) {
        Toast.makeText(
          this,
          "Enter a valid doctor email address",
          Toast.LENGTH_LONG
        ).show()
      }else {
        val LoginEmail = editTxtDoctorLEmail.text.toString()
        val LoginPassword = editTxtDoctorLPassword.text.toString()
        user.signInWithEmailAndPassword(LoginEmail, LoginPassword)
          .addOnCompleteListener(PatientLoginActivity()) { task ->
            if (task.isSuccessful) {
              Toast.makeText(
                this,
                "User logged in successfully",
                Toast.LENGTH_LONG
              ).show()
              val intent = Intent(this, DoctorHomePageActivity::class.java)
              startActivity(intent)
              finish()
            } else {
              Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
            }
          }
      }
    }

    btnDoctorRegister.setOnClickListener {
      val intent = Intent(this, DoctorRegisterActivity::class.java)
      startActivity(intent)
    }
  }
}