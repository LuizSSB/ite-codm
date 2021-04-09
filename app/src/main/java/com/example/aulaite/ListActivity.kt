package com.example.aulaite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {
    private val adapter by lazy {
        SampleAdapter((0 until 100).map { "Elemento $it" })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        findViewById<RecyclerView>(R.id.list).adapter = adapter
        findViewById<Button>(R.id.button_add10).setOnClickListener {
            adapter.items = adapter.items +
                    (adapter.itemCount until (adapter.itemCount + 10)).map { "Elemento $it" }
        }
        findViewById<Button>(R.id.button_remove10).setOnClickListener {
            if (adapter.itemCount < 10) {
                return@setOnClickListener
            }
            adapter.items = adapter.items.subList(0, adapter.itemCount - 10)
        }
    }
}

abstract class SampleViewHolder private constructor(rootView: ViewGroup) : RecyclerView.ViewHolder(rootView) {
    class Item(rootView: ViewGroup) : SampleViewHolder(rootView) {
        fun bind(value: String, isOdd: Boolean) {
            itemView.background = if (isOdd) ContextCompat.getDrawable(itemView.context, R.color.colorAccent)
            else null
            itemView.findViewById<TextView>(R.id.text_main).text = value
            itemView.findViewById<TextView>(R.id.text_detail).text = hashCode().toString()
        }
    }

    class Header(rootView: ViewGroup) : SampleViewHolder(rootView)

    class Footer(rootView: ViewGroup) : SampleViewHolder(rootView)

    enum class Type {
        Item, Header, Footer;
    }

    companion object {
        fun create(parent: ViewGroup, type: Type): SampleViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return when (type) {
                Type.Item -> {
                    val itemView = layoutInflater.inflate(
                        R.layout.item_sample, parent, false
                    )
                    Item(itemView as ViewGroup)
                }
                Type.Header -> {
                    val itemView = layoutInflater.inflate(
                        R.layout.item_header, parent, false
                    )
                    Header(itemView as ViewGroup)
                }
                Type.Footer -> {
                    val itemView = layoutInflater.inflate(
                        R.layout.item_footer, parent, false
                    )
                    Footer(itemView as ViewGroup)
                }
            }
        }
    }
}

class SampleAdapter(
    items: List<String>
) : RecyclerView.Adapter<SampleViewHolder>() {
    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> SampleViewHolder.Type.Header.ordinal
            (items.size + 1) -> SampleViewHolder.Type.Footer.ordinal
            else -> SampleViewHolder.Type.Item.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        return SampleViewHolder.create(parent, SampleViewHolder.Type.values()[viewType])
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        if (holder is SampleViewHolder.Item) {
            holder.bind(items[position - 1], position % 2 == 1)
        }
    }

    override fun getItemCount(): Int {
        return items.size + 2
    }
}
