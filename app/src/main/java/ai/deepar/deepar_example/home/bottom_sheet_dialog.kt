package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ai.deepar.deepar_example.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private lateinit var textDate: TextView
private lateinit var buttonDate: Button

fun updateText(calendar: Calendar) {
    val dateFormat = "dd-MM-yyyy"
    val simple = SimpleDateFormat(dateFormat, Locale.CHINA)
    textDate.setText(simple.format(calendar.time))
}

class bottom_sheet_dialog : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bottom_sheet_dialog)

        textDate = findViewById(R.id.noDateSelected)
        buttonDate = findViewById(R.id.btnSelectDate)

        val calendarBox = Calendar.getInstance()
        val dateBox = DatePickerDialog.OnDateSetListener{ datePicker, year, month, day ->
            calendarBox.set(Calendar.YEAR, year)
            calendarBox.set(Calendar.MONTH, month)
            calendarBox.set(Calendar.DAY_OF_MONTH, day)

            updateText(calendarBox)
        }

        buttonDate.setOnClickListener {
            DatePickerDialog(this, dateBox, calendarBox.get(Calendar.YEAR), calendarBox.get(Calendar.MONTH), calendarBox.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
}