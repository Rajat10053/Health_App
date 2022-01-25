package com.example.helthapp.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.helthapp.R
import androidx.recyclerview.widget.RecyclerView
import com.example.helthapp.model.State
import java.util.zip.Inflater

class Statelistadapter (private val StateList:ArrayList<State>,
                        private val context: Context):RecyclerView.Adapter<Statelistadapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Statelistadapter.ViewHolder {
        var itemview = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_row,parent,false)

        return ViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: Statelistadapter.ViewHolder, position: Int) {
        val cutrrentitem = StateList[position]
        holder.Location.text  = cutrrentitem.loc
        holder.totalconfirm.text = cutrrentitem.tatal.toString()
    }

    override fun getItemCount(): Int {
        return StateList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val Location :TextView = itemView.findViewById(R.id.LocationId)
        val totalconfirm :TextView = itemView.findViewById(R.id.totalConfirmed)


    }
}