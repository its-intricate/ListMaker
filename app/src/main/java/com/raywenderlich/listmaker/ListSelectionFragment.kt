package com.raywenderlich.listmaker

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListSelectionFragment : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    lateinit var listDataManager: ListDataManager
    lateinit var listsRecyclerView: RecyclerView


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: MainActivity = activity as MainActivity
        listDataManager = ListDataManager(activity)
        val lists = listDataManager.readLists()

        view.findViewById<FloatingActionButton>(R.id.add_list_button).setOnClickListener {
            showCreateListDialog()
        }

        listsRecyclerView = view.findViewById(R.id.lists_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(activity)
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        activity.title = getString(R.string.app_name)
    }

    private fun showCreateListDialog() {
        val activity: MainActivity = activity as MainActivity
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create)

        val builder = AlertDialog.Builder(activity)
        val listTitleEditText = EditText(activity)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)

            val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)

            dialog.dismiss()
            showListDetail(list)
        }

        builder.create().show()
    }

     fun showListDetail(list: TaskList) {
         val activity: MainActivity = activity as MainActivity
         val bundle = bundleOf("list" to list)

         if (activity.largeScreen) {
             var fragment = ListDetailFragment()
             fragment.list = list
             activity.supportFragmentManager.beginTransaction().
             replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
             activity.listTitle!!.text = list.name.toUpperCase()
         } else {
             findNavController().navigate(R.id.ListDetailFragment, bundle)
         }
    }

    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }

}
