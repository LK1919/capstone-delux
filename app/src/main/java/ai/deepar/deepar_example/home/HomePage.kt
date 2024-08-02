package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.databinding.ActivityPatientMyAppointmentsBinding
import ai.deepar.deepar_example.patient.AppointmentActivity
import ai.deepar.deepar_example.patient.PatientMyAppointmentsActivity
import ai.deepar.deepar_example.patient.PatientProfileActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {
  // TODO: Rename and change types of parameters
  private var param1: String? = null
  private var param2: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      param1 = it.getString(ARG_PARAM1)
      param2 = it.getString(ARG_PARAM2)
    }
  }

  @SuppressLint("MissingInflatedId")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_home_page, container, false)

    val btnAppointments = view.findViewById<CardView>(R.id.appointmentsCard)
    btnAppointments.setOnClickListener {
      startActivity(Intent(requireActivity(), PatientMyAppointmentsActivity::class.java))
    }

    val btnBranches = view.findViewById<CardView>(R.id.branchesCard)
    btnBranches.setOnClickListener {
      startActivity(Intent(requireActivity(), BranchesScreen::class.java))
    }

    val btnSetAppointments = view.findViewById<CardView>(R.id.setAppointmentCard)
    btnSetAppointments.setOnClickListener {
      startActivity(Intent(requireActivity(), AppointmentActivity::class.java))
    }

    // bottom sheet dialog option
//    val btnShowAppointmentsBottomSheet = view.findViewById<CardView>(R.id.setAppointmentCard)
//    btnShowAppointmentsBottomSheet.setOnClickListener {
//      val dialog = BottomSheetDialog(this.requireContext())
//      val view2 = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
//      val btnClose = view2.findViewById<Button>(R.id.bsdCancelAppointmentBtn)
//      val btnSetAppointment = view2.findViewById<Button>(R.id.bsdSetAppointmentBtn)
//      val checkAvailBranches = view2.findViewById<Button>(R.id.bsdCheckBranchesBtn)
//
//      btnClose.setOnClickListener {
//        dialog.dismiss()
//      }
//
//      btnSetAppointment.setOnClickListener {
//        Toast.makeText(this@HomePage.requireContext(), "Appointment set successfully", Toast.LENGTH_LONG).show()
//        startActivity(Intent(requireActivity(), PatientMyAppointmentsActivity::class.java))
//      }
//
//      checkAvailBranches.setOnClickListener {
//        startActivity(Intent(requireActivity(), BranchesScreen::class.java))
//      }
//
//
//      dialog.setCancelable(false)
//      dialog.setContentView(view2)
//      dialog.show()
//    }

    val btnGlasses = view.findViewById<CardView>(R.id.glassesCard)
    btnGlasses.setOnClickListener {
      startActivity(Intent(requireActivity(), GlassesScreen::class.java))
    }

    val btnProfile = view.findViewById<CardView>(R.id.profileCard)
    btnProfile.setOnClickListener {
      startActivity(Intent(requireActivity(), PatientProfileActivity::class.java))
    }

    return view
  }

  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePage.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      HomePage().apply {
        arguments = Bundle().apply {
          putString(ARG_PARAM1, param1)
          putString(ARG_PARAM2, param2)
        }
      }
  }
}