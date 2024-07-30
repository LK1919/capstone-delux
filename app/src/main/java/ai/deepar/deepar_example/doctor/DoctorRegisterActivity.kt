package ai.deepar.deepar_example.doctor

import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.doctor.models.DoctorData
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DoctorRegisterActivity : AppCompatActivity() {
  lateinit var spinnerSpecialties: Spinner
  lateinit var txtRDoctorName: EditText
  lateinit var txtRDoctorSurname: EditText
//  lateinit var txtRDoctorAge: EditText
  lateinit var txtRDoctorEmail: EditText
  lateinit var txtRDoctorPassword: EditText
  lateinit var btnRDocConfirm: Button

  lateinit var auth: FirebaseAuth
  lateinit var db: FirebaseFirestore

  lateinit var DoctorName: String
  lateinit var DoctorSurname: String
//  lateinit var DoctorAge: String
  lateinit var DoctorBranch: String
  lateinit var DoctorEmail: String
  lateinit var DoctorPassword: String
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_doctor_register)

    db = FirebaseFirestore.getInstance()
    auth = FirebaseAuth.getInstance()

    txtRDoctorName = findViewById(R.id.txtRDoctorName)
    txtRDoctorSurname = findViewById(R.id.txtRDoctorSurname)
//    txtRDoctorAge = findViewById(R.id.txtRDoctorAge)
    txtRDoctorEmail = findViewById(R.id.txtRDoctorEmail)
    txtRDoctorPassword = findViewById(R.id.txtRDoctorPassword)
    btnRDocConfirm = findViewById(R.id.btnRDocConfirm)
    spinnerSpecialties = findViewById(R.id.spinnerField)

    val specialties = resources.getStringArray(R.array.branches)
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, specialties)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinnerSpecialties.adapter = adapter

    btnRDocConfirm.setOnClickListener {
      DoctorName = txtRDoctorName.text.toString()
      DoctorSurname = txtRDoctorSurname.text.toString()
//      DoctorAge = txtRDoctorAge.text.toString()
      DoctorBranch = spinnerSpecialties.selectedItem.toString()
      DoctorEmail = txtRDoctorEmail.text.toString()
      DoctorPassword = txtRDoctorPassword.text.toString()
      if (!DoctorEmail.contains("@doctor")) {
        Toast.makeText(
          this,
          "Enter a Valid Doctor Email",
          Toast.LENGTH_LONG
        ).show()
      } else if (DoctorName != "" && DoctorSurname != "" && DoctorBranch != "" && DoctorEmail.toString() != "" && DoctorPassword.toString() != "") {
        auth.createUserWithEmailAndPassword(DoctorEmail, DoctorPassword)
          .addOnCompleteListener(DoctorRegisterActivity()) { task ->
            if (task.isSuccessful) {
              Toast.makeText(this, "User added succesfully", Toast.LENGTH_LONG).show()
              val user = auth.currentUser
              val doctorData = DoctorData(
                user!!.uid,
                DoctorName,
                DoctorSurname,
                DoctorBranch,
                DoctorEmail,
                DoctorPassword,
                ""
              )
              db.collection("doctors").document(user.email!!).set(doctorData)
                .addOnSuccessListener {
                  Log.d(
                    "Firestore",
                    "Doctor DocumentSnapshot successfully written!"
                  )
                }.addOnFailureListener {
                  Log.e("Firestore", it.message.toString())
                }
              Log.d("doctor", doctorData.toString())
              val intent =
                Intent(this@DoctorRegisterActivity, DoctorLoginActivity::class.java)
              startActivity(intent)
              finish()
            } else {
              Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()
            }
          }

      } else {
        Toast.makeText(this, "Incomplete Information", Toast.LENGTH_LONG).show()
      }
    }
  }
}