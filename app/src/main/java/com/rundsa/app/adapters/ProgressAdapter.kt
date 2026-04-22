package com.rundsa.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.models.ProgressModel   // 🔥 IMPORTANT

import android.widget.ProgressBar //progress bar
//progress bar color
import android.content.res.ColorStateList
import android.graphics.Color

class ProgressAdapter(private val list: List<ProgressModel>) :
    RecyclerView.Adapter<ProgressAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topic: TextView = view.findViewById(R.id.tvTopic)
        val runs: TextView = view.findViewById(R.id.tvRuns)
        val progress: TextView = view.findViewById(R.id.tvProgress)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_progress, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        // 🔥 ALWAYS SET TOPIC FIRST
        holder.topic.text = item.topic


        // 🔵 HEADER
        if (item.runs.toInt() == -1 && item.progress == -1) {
            holder.runs.visibility = View.GONE
            holder.progress.visibility = View.GONE
            holder.progressBar.visibility = View.GONE

            //  (color logic)
            holder.itemView.setBackgroundColor(Color.parseColor("#F5F5F5"))
            // spacing between sections
            val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = 16
            holder.itemView.layoutParams = params

            return
        }

        // 🔥 QUIZ RESULT ITEM
        if (item.score.isNotEmpty()) {
            holder.runs.visibility = View.GONE
            holder.progress.visibility = View.VISIBLE
            holder.progressBar.visibility = View.GONE   // ❌ hide

            holder.progress.text = item.score   // ✅ Score: 9 / 10
        }

        // 🔥 TOPIC PROGRESS ITEM
        else {
            holder.runs.visibility = View.VISIBLE
            holder.progress.visibility = View.VISIBLE
            holder.progressBar.visibility = View.VISIBLE

            holder.runs.text = "Runs: ${item.runs}"
            holder.progress.text = "Progress: ${item.progress}%"

            holder.progressBar.progress = item.progress

            // 🔥 ADD HERE (color logic)
            if (item.progress == 100) {
                holder.progressBar.progressTintList =
                    ColorStateList.valueOf(Color.parseColor("#4CAF50")) // Green
            } else {
                holder.progressBar.progressTintList =
                    ColorStateList.valueOf(Color.parseColor("#FFC107")) // Yellow
            }
        }
    }

    // ✅ ADD THIS
    override fun getItemCount(): Int {
        return list.size
    }
}