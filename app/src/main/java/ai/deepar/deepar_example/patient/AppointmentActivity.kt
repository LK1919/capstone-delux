package ai.deepar.deepar_example.patient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.doctor.models.AppointmentData
import ai.deepar.deepar_example.home.BottomNavigation
import ai.deepar.deepar_example.home.SetAppointment
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar

class AppointmentActivity : AppCompatActivity() {
//  lateinit var txtAppName: TextView
//  lateinit var txtAppSurname: TextView
//  lateinit var txtAppAge: TextView
//  lateinit var txtAppField: TextView
  lateinit var txtAppHour: TextView
  lateinit var btnSelectHour: ImageButton
  lateinit var btnSelectDate: ImageButton
  lateinit var btnMakeApp: Button
  var Date = ""
  var selectedHour = ""
  lateinit var ImgApp: ImageView
  @SuppressLint("MissingInflatedId")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_appointment)

//    txtAppName = findViewById(R.id.txtAppName)
//    txtAppSurname = findViewById(R.id.txtAppSurname)
//    txtAppAge = findViewById(R.id.txtAppAge)
//    txtAppField = findViewById(R.id.txtAppField)
    txtAppHour = findViewById(R.id.txtAppHour)
    btnSelectHour = findViewById(R.id.btnSelectHour)
    btnSelectDate = findViewById(R.id.btnSelectDate)
//    ImgApp = findViewById(R.id.ImgApp)
    btnMakeApp = findViewById(R.id.btnMakeApp)

//    val doctorName = intent.getStringExtra("name")
//    val doctorSurname = intent.getStringExtra("surname")
//    val doctorAge = intent.getStringExtra("age")
//    val doctorField = intent.getStringExtra("field")
//    val doctorImage = intent.getStringExtra("image")
    val patientImage = intent.getStringExtra("patientImage")
    val patientFullName = intent.getStringExtra("patientName")

//    txtAppName.text = "Name: $doctorName"
//    txtAppSurname.text = "Surname: $doctorSurname"
//    txtAppAge.text = "Age: $doctorAge"
//    txtAppField.text = "Branch: $doctorField"
//    Glide.with(this).load(doctorImage).into(ImgApp)

    val currentDate = Calendar.getInstance()
    val year = currentDate.get(Calendar.YEAR)
    val month = currentDate.get(Calendar.MONTH)
    val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)
    var simpleDateFormat: SimpleDateFormat
    val datePickerDialog = DatePickerDialog(
      this,
      DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
        val selectedDate = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("E.LLLL.yyyy")
        selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
        val dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK)

        if (dayOfWeek == Calendar.SUNDAY) {
          Toast.makeText(this, "Closed on Sundays, Please Select Another Day", Toast.LENGTH_LONG)
            .show()
        } else {
          var ay = "${selectedMonth + 1}"
          if (selectedMonth + 1 < 10) {
            ay = "0${selectedMonth + 1}"
          }
          Date = "$selectedDayOfMonth.$ay.$selectedYear"
        }
      },
      year,
      month,
      dayOfMonth
    )

    val minDate = Calendar.getInstance()
    minDate.add(Calendar.DAY_OF_MONTH, 0)
    datePickerDialog.datePicker.minDate = minDate.timeInMillis

    val maxDate = Calendar.getInstance()
    maxDate.add(Calendar.DAY_OF_MONTH, 20)
    datePickerDialog.datePicker.maxDate = maxDate.timeInMillis

    btnSelectDate.setOnClickListener {
      datePickerDialog.show()
    }

    val mTimePicker: TimePickerDialog
    val mCurrentTime = Calendar.getInstance()
    val hour = mCurrentTime.get(Calendar.HOUR_OF_DAY)
    val minute = mCurrentTime.get(Calendar.MINUTE)

    mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
      override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        val roundedMinute = (Math.round(minute.toFloat() / 15) * 15) % 60
        if (hour < 9 || hour >= 18) {
          Toast.makeText(
            this@AppointmentActivity,
            "Please Choose a Time During Business Hours (9:00 - 18:00)",
            Toast.LENGTH_LONG
          ).show()
        } else {
          selectedHour = String.format("%d:%d", hour, roundedMinute)
          txtAppHour.setText(
            "Date: $Date\nTime: " + String.format(
              "%d:%d",
              hour,
              roundedMinute
            )
          )
        }
      }
    }, hour, minute, false)

    btnSelectHour.setOnClickListener {
      if (Date.isEmpty()) {
        Toast.makeText(this, "Select Date First", Toast.LENGTH_LONG).show()
      } else {
        mTimePicker.show()
      }
    }
    btnMakeApp.setOnClickListener {
      val patientEmail = FirebaseAuth.getInstance().currentUser?.email
//      val doctorEmail = intent.getStringExtra("email")
//      val patientImage = patientImage
//      val doctorImage = doctorImage
      val appointmentDate = Date
      val appointmentHour = selectedHour

      if (patientEmail != null && appointmentDate.isNotEmpty() && appointmentHour.isNotEmpty()) {
//        val doctorFullname = doctorName + " " + doctorSurname
        val appointmentInfo = AppointmentData(
          null,
//          doctorEmail,
          patientEmail,
          patientFullName,
//          patientImage,
//          doctorFullname,
//          doctorImage,
//          doctorField,
          appointmentDate,
          appointmentHour
        )
        addAppointmentToFirestore(patientEmail, appointmentDate,appointmentInfo)
        Toast.makeText(this, "Appointment Created Successfully", Toast.LENGTH_LONG).show()
        val intent = Intent(this, PatientMyAppointmentsActivity::class.java)
        startActivity(intent)
        finish()
      } else {
        Toast.makeText(
          this,
          "Please Complete the Information",
          Toast.LENGTH_LONG
        ).show()
      }
    }
  }
  fun addAppointmentToFirestore(
    patientEmail: String,
    doctorEmail: String,
    appointment: AppointmentData
  ) {
    val db = FirebaseFirestore.getInstance()
    val patientRef = db.collection("appointments").document(patientEmail)
    val newAppointmentRef = patientRef.collection("patientAppointments").document()
    val doctorRef = db.collection("doctorAppointments").document(doctorEmail)
    val newDoctorAppointmentRef = doctorRef.collection("appointments").document(newAppointmentRef.id)

    newAppointmentRef.set(appointment)
      .addOnSuccessListener {
        newDoctorAppointmentRef.set(appointment)
          .addOnSuccessListener {
            Log.d("AppointmentActivity", "Appointment Added Successfully")
          }
          .addOnFailureListener { e ->
            Log.w("AppointmentActivity", "An Error Occurred while Adding the Doctor's Appointment", e)
          }
      }
      .addOnFailureListener { e ->
        Log.w("AppointmentActivity", "An Error Occurred while Adding an Appointment", e)
      }
  }
}