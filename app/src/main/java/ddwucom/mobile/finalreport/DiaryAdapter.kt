package ddwucom.mobile.finalreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import androidx.recyclerview.widget.RecyclerView
import ddwucom.mobile.finalreport.databinding.ListItemBinding

class DiaryAdapter (val days : ArrayList<DiaryDto>) :RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
    val TAG = "DiartAdapter"

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return DiaryViewHolder(itemBinding, listener, longListener)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.itemBinding.date.text=days[position].date
        holder.itemBinding.weather.text=days[position].weather
        holder.itemBinding.score.text=days[position].score
        holder.itemBinding.imageView.setImageResource(days[position].photo)
    }

    class DiaryViewHolder (val itemBinding: ListItemBinding, listener:OnItemClickListener?, longListener:OnItemLongClickListener?) : RecyclerView.ViewHolder(itemBinding.root){
        init{
            itemBinding.root.setOnClickListener{
                listener?.onItemClick(it, adapterPosition)
            }

            itemBinding.root.setOnLongClickListener{
                longListener?.onItemLongClick(it, adapterPosition)
                true
            }

        }
    }


    var listener : OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(view:View, position:Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?){
        this.listener=listener
    }



    var longListener : OnItemLongClickListener? = null

    interface  OnItemLongClickListener{
        fun onItemLongClick(view:View?, position:Int)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener?){
        this.longListener=listener
    }


}