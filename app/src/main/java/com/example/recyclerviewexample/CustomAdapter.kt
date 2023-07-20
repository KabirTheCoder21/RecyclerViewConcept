package com.example.recyclerviewexample

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private var mList : ArrayList<ItemViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView.findViewById(R.id.imageview)
        val textView : TextView = itemView.findViewById(R.id.textView)
        val detail : TextView = itemView.findViewById(R.id.detail)
        val layout : LinearLayout = itemView.findViewById(R.id.layout)
        val cdView : CardView = itemView.findViewById(R.id.cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = mList[position]
        holder.imageView.setImageResource(items.image)
        holder.textView.text = items.text
        holder.detail.text = items.detail
        val isExp : Boolean = items.isExpandale

       var visibility = if(isExp)
            View.VISIBLE
        else
            View.GONE
        holder.detail.visibility = visibility

        transition(holder,items,position)

        val animator = animation(holder)
        holder.cdView.startAnimation(animator)
    }

    private fun transition(holder: ViewHolder, items: ItemViewModel, position: Int) {
        holder.layout.setOnClickListener {

            val layoutTransition = LayoutTransition()
            layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            holder.layout.layoutTransition = layoutTransition

            TransitionManager.beginDelayedTransition(holder.layout,AutoTransition())
            items.isExpandale = !items.isExpandale
            notifyItemChanged(position)

            isAnyItemExpanded(position)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun animation(holder: ViewHolder):Animation{
      val animator = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.animation)
        return animator
    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = mList.indexOfFirst {
            it.isExpandale
        }
     val last = mList.indexOfLast {
         it.isExpandale
     }
        if(temp>=0 && temp!=position)
        {
            mList[temp].isExpandale = false;
            notifyItemChanged(temp,0)
        }
        if(last>=0 && last!=position)
        {
            mList[last].isExpandale = false
            notifyItemChanged(last,1)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        when {
            payloads.isNotEmpty() && payloads[0] == 0 -> holder.detail.visibility = View.GONE
            payloads.isNotEmpty() && payloads[payloads.size - 1] == 1 -> holder.detail.visibility = View.GONE
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }
    fun setfilteredList(filteredList: ArrayList<ItemViewModel>) {
        this.mList = filteredList
        notifyDataSetChanged()
    }
}
