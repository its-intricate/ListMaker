package com.raywenderlich.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val taskTextView = itemView.findViewById(R.id.text_view_task) as TextView
}
