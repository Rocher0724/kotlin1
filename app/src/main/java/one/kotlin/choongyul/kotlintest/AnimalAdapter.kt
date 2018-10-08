package one.kotlin.choongyul.kotlintest

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.animal_list_item.view.*

/**
 *  커니가 추천한 리사이클러뷰 만드는 방법이다.
 */
class AnimalAdapter(val items : ArrayList<String>) : RecyclerView.Adapter<AnimalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val animal = items[position]
        with (holder.itemView) {
                   tv_animal_type.text = animal
        }
    }

    inner class ViewHolder (parent: ViewGroup)
        : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.animal_list_item, parent, false))
}
