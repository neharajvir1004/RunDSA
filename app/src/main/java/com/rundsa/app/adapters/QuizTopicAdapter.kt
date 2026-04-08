package com.rundsa.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.models.TopicModel

class QuizTopicAdapter(
    private val list: List<TopicModel>,
    private val onItemClick: (TopicModel) -> Unit
) : RecyclerView.Adapter<QuizTopicAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textTopic)
        val icon: ImageView = view.findViewById(R.id.iconTopic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_topic_quiz, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title
        holder.icon.setImageResource(item.icon)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}