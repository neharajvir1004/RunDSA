package com.rundsa.app.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.models.FeatureModel
import com.rundsa.app.activities.LearnActivity

class FeatureAdapter(private val featureList: List<FeatureModel>) :
    RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder>() {

    class FeatureViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.textFeature)
        val icon: ImageView = view.findViewById(R.id.iconFeature)
        val container: View = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feature, parent, false)

        return FeatureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {

        val feature = featureList[position]

        holder.title.text = feature.title
        holder.icon.setImageResource(feature.icon)
        holder.container.setBackgroundResource(feature.gradient)

        holder.container.setOnClickListener {
            if (feature.title == "Learn DSA") {
                val intent = Intent(holder.itemView.context, LearnActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return featureList.size
    }
}