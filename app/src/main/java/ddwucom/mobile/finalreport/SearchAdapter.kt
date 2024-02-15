package ddwucom.mobile.finalreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwucom.mobile.finalreport.databinding.SearchItemBinding

class SearchAdapter (val days : ArrayList<DiaryDto>) : RecyclerView.Adapter<SearchAdapter.DiarySearchViewHolder>()  {
    override fun getItemCount(): Int {
        return days.size
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): DiarySearchViewHolder {
        val searchItemBinding = SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchAdapter.DiarySearchViewHolder(searchItemBinding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.DiarySearchViewHolder, position: Int) {
        holder.searchItemBinding.date.setText(days[position].date)
        holder.searchItemBinding.weather.setText(days[position].weather)
        holder.searchItemBinding.score.setText(days[position].score)
        holder.searchItemBinding.imageView.setImageResource(days[position].photo)
        holder.searchItemBinding.feeling.setText(days[position].feeling)
        holder.searchItemBinding.detail.setText(days[position].diaryContent)
    }

    class DiarySearchViewHolder (val searchItemBinding: SearchItemBinding) : RecyclerView.ViewHolder(searchItemBinding.root){
    }

}