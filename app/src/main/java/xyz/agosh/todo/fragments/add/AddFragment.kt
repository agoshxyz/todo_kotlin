package xyz.agosh.todo.fragments.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add.*
import xyz.agosh.todo.R
import xyz.agosh.todo.database.models.TodoData
import xyz.agosh.todo.database.models.Type
import xyz.agosh.todo.database.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {


    private val mTodoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //Setting menu items
        setHasOptionsMenu(true)



        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add){
            insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun insertDataToDatabase() {
        val mName = nameTodo.text.toString()
        val mType = typeTodo.selectedItem.toString()
        val mDescription = descriptionTodo.text.toString()


        val validation = verifyDataFromUser(mName, mDescription)

        if(validation){
            //Inserting Data
            val newTodoData = TodoData(
                0,
                mName,
                parseType(mType),
                mDescription
            )
            mTodoViewModel.insertData(newTodoData)
            Toast.makeText(requireContext(), "Task Added Successfully", Toast.LENGTH_SHORT).show()

            //Navigating into list fragment
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun verifyDataFromUser(name: String, description: String ): Boolean{
        return if(TextUtils.isEmpty(name) || TextUtils.isEmpty(description)){
            false
        }
        else !(name.isEmpty() || description.isEmpty())
    }


    private fun  parseType(type: String): Type {
        return when(type){
            "Personal" -> Type.Personal
            "Work" -> Type.Work
            "School" -> Type.School
            else -> Type.Personal
        }
    }


    private fun clickDatePicker(view: View) {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                var monthInWord = "Undefined"
                when (selectedMonth) {
                    0 -> monthInWord = "January"
                    1 -> monthInWord = "February"
                    2 -> monthInWord = "March"
                    3 -> monthInWord = "April"
                    4 -> monthInWord = "May"
                    5 -> monthInWord = "June"
                    6 -> monthInWord = "July"
                    7 -> monthInWord = "August"
                    8 -> monthInWord = "September"
                    9 -> monthInWord = "October"
                    10 -> monthInWord = "November"
                    11 -> monthInWord = "December"
                }

                var selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = simpleDateFormat.parse(selectedDate)
                val selectedDateInMinutes = theDate!!.time / 60_000

                val currentDate =
                    simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                val currentDateInMinutes = currentDate!!.time / 60_000

                val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes;
                Toast.makeText(
                    requireContext(),
                    "The picked date is $monthInWord $selectedDayOfMonth, $selectedYear",
                    Toast.LENGTH_LONG
                ).show()
                //selectedTextView.setText("$monthInWord $selectedDayOfMonth, $selectedYear")
                //selectedDateInMinutesTextView.setText(differenceInMinutes.toString())
            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.setMaxDate(Date().time + 86_400_000)
        datePickerDialog.show()
    }
}