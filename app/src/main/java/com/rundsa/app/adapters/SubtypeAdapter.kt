package com.rundsa.app.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.models.SubtypeModel

class SubtypeAdapter(
    private val subtypeList: List<SubtypeModel>,
    private val onSubtypeClick: (SubtypeModel) -> Unit
) : RecyclerView.Adapter<SubtypeAdapter.SubtypeViewHolder>() {

    private var selectedPosition = 0

    inner class SubtypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardLayout: LinearLayout = itemView.findViewById(R.id.cardSubtype)
        val tvSubtypeName: TextView = itemView.findViewById(R.id.tvSubtypeName)
        val tvSubtypeDefinition: TextView = itemView.findViewById(R.id.tvSubtypeDefinition)
        val tvSubtypeCode: TextView = itemView.findViewById(R.id.tvSubtypeCode)
        val tvSelectedLabel: TextView = itemView.findViewById(R.id.tvSelectedLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subtype, parent, false)
        return SubtypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubtypeViewHolder, position: Int) {
        val item = subtypeList[position]

        holder.tvSubtypeName.text = item.name
        holder.tvSubtypeDefinition.text = item.definition
        holder.tvSubtypeCode.text = item.code

        if (position == selectedPosition) {
            holder.cardLayout.setBackgroundColor(Color.parseColor("#DCEEFF"))
            holder.tvSelectedLabel.visibility = View.VISIBLE
        } else {
            holder.cardLayout.setBackgroundColor(Color.parseColor("#F7F9FC"))
            holder.tvSelectedLabel.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            val oldPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(oldPosition)
            notifyItemChanged(selectedPosition)
            onSubtypeClick(item)
        }
    }

    override fun getItemCount(): Int = subtypeList.size
}