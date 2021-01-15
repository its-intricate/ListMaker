package com.raywenderlich.listmaker

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListDetailFragment : Fragment() {

    lateinit var list: TaskList
    lateinit var listItemsRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: MainActivity = activity as MainActivity

        list = requireArguments().getParcelable("list")!!
        activity.title = list.name

        view.findViewById<FloatingActionButton>(R.id.add_list_item_button).setOnClickListener {
            showCreateListItemDialog()
        }

        listItemsRecyclerView = view.findViewById(R.id.list_item_recyclerview)
        listItemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        listItemsRecyclerView.adapter = ListDetailRecyclerViewAdapter(list)
    }



    private fun showCreateListItemDialog() {
        val activity: MainActivity = activity as MainActivity
        val dialogTitle = getString(R.string.name_of_list_item)
        val positiveButtonTitle = getString(R.string.create)

        val builder = AlertDialog.Builder(activity)
        val listItemTitleEditText = EditText(activity)
        listItemTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listItemTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val listItem = listItemTitleEditText.text.toString()
            list.tasks.add(listItem)

            val recyclerAdapter = listItemsRecyclerView.adapter as ListDetailRecyclerViewAdapter
            recyclerAdapter.notifyItemInserted(list.tasks.size - 1)

            val listDataManager = ListDataManager(activity)
            listDataManager.saveList(list)
            dialog.dismiss()
        }

        builder.create().show()
    }
}