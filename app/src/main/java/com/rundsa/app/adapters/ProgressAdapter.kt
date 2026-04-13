package com.rundsa.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.models.ProgressModel   // 🔥 IMPORTANT

class ProgressAdapter(private val list: List<ProgressModel>) :
    RecyclerView.Adapter<ProgressAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topic: TextView = view.findViewById(R.id.tvTopic)
        val runs: TextView = view.findViewById(R.id.tvRuns)
        val progress: TextView = view.findViewById(R.id.tvProgress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_progress, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        // HEADER
        if (item.runs.toInt() == -1 && item.progress == -1) {
            holder.topic.text = item.topic
            holder.runs.visibility = View.GONE
            holder.progress.visibility = View.GONE
            return
        }

        holder.topic.text = item.topic

        if (item.score.isNotEmpty()) {
            holder.progress.text = item.score   // SHOW SCORE
            holder.runs.text = ""
        } else {
            holder.runs.text = "Runs: ${item.runs}"
            holder.progress.text = "Progress: ${item.progress}%"
        }
    }

    // ✅ ADD THIS
    override fun getItemCount(): Int {
        return list.size
    }
}