package com.dacodes.todolistad.presentation.views

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dacodes.todolistad.databinding.FragmentBottomSheetBinding
import com.dacodes.todolistad.model.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var listener : AddNewTaskListener

    private val bottomSheetDialog: BottomSheetDialog by lazy {
        BottomSheetDialog(requireContext(), theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSave.setOnClickListener {
                validateData(etTask.text.toString())
            }
        }
    }

    private fun validateData(task: String){
        binding.apply {
            if (task.isEmpty()){
                etTask.error = "Complete this field"
                return
            }
            val priority = when(rgPriority.checkedRadioButtonId){
                rbHigh.id -> 1
                rbNormal.id -> 2
                rbLow.id -> 3
                else -> 2
            }

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val currentDate = LocalDateTime.now().format(formatter)

            listener.onAddNewTask(Task(task = task, priority = priority, completed = false, date = currentDate))
            bottomSheetDialog.dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> bottomSheetDialog.behavior.state =
                        BottomSheetBehavior.STATE_EXPANDED
                    else -> bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        return bottomSheetDialog
    }


    interface AddNewTaskListener{
        fun onAddNewTask(task: Task)
    }

    companion object {
        @JvmStatic
        fun newInstance(listener: AddNewTaskListener) =
            BottomSheetFragment().apply {
                this.listener = listener
            }
    }

}