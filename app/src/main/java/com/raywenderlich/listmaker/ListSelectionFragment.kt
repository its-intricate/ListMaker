package com.raywenderlich.listmaker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListSelectionFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: MainActivity = activity as MainActivity
        val listDataManager = ListDataManager(activity)
        val lists = listDataManager.readLists()
        activity.listsRecyclerView = view.findViewById(R.id.lists_recyclerview)
        activity.listsRecyclerView.layoutManager = LinearLayoutManager(activity)
        activity.listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists)
    }

}