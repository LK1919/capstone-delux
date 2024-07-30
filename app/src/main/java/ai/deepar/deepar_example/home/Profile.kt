package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.patient.PatientMyAppointmentsActivity
import ai.deepar.deepar_example.patient.PatientProfileActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
  // TODO: Rename and change types of parameters
  private var param1: String? = null
  private var param2: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
    arguments?.let {
      param1 = it.getString(ARG_PARAM1)
      param2 = it.getString(ARG_PARAM2)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.patient_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.profileCard -> {
        val intent = Intent(requireContext(), PatientProfileActivity::class.java)
        startActivity(intent)
        return true
      }
      R.id.news -> {
        val intent = Intent(requireContext(), NewsActivity::class.java)
        startActivity(intent)
        return true
      }
//      R.id.logoutSettings -> {
//        AlertDialog.Builder(requireContext()).apply {
//          setTitle("Logout")
//          setMessage("Do you really want to logout?")
//          setPositiveButton("Yes") { _, _ ->
//            FirebaseAuth.getInstance().signOut()
//            val intent = Intent(requireContext(), MainAppointmentActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            requireActivity().finish()
//          }
//          setNegativeButton("No", null)
//        }.create().show()
//        return true
//      }
      R.id.appointments -> {
        val intent = Intent(requireContext(), PatientMyAppointmentsActivity::class.java)
        startActivity(intent)
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  @SuppressLint("MissingInflatedId")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.activity_patient_profile, container, false)

//    val btnBack = view.findViewById<ImageButton>(R.id.actionBarBackArrowProfile)
//    btnBack.setOnClickListener {
//      requireActivity().supportFragmentManager.popBackStack()
//    }

    return view
  }

  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      Profile().apply {
        arguments = Bundle().apply {
          putString(ARG_PARAM1, param1)
          putString(ARG_PARAM2, param2)
        }
      }
  }
}