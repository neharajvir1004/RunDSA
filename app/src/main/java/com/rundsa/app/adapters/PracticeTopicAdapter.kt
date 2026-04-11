package com.rundsa.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.models.PracticeTopicModel

class PracticeTopicAdapter(
    private val topicList: List<PracticeTopicModel>,
    private val onClick: (PracticeTopicModel) -> Unit
) : RecyclerView.Adapter<PracticeTopicAdapter.TopicViewHolder>() {

    class TopicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.topicTitle)
        val description: TextView = view.findViewById(R.id.topicDesc)
        val icon: ImageView = view.findViewById(R.id.topicIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_topic_practice, parent, false)
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val item = topicList[position]
        holder.title.text = item.title
        holder.description.text = item.description
        holder.icon.setImageResource(item.icon)

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = topicList.size
}