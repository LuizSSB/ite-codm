package com.example.aulaite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {
    private val adapter by lazy {
        SampleAdapter(
            (0 until 100).map { "Elemento $it" },
            findViewById(R.id.list),
            this::handleSelection
        )
    }

    private fun handleSelection(item: String, at: Int) {
        Toast.makeText(this, "Selecionou linha $at que exibe $item", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        findViewById<RecyclerView>(R.id.list).let {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        }
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
    class Item(
        rootView: ViewGroup,
        var onSelect: ((String) -> Unit)? = null
    ) : SampleViewHolder(rootView) {
        private lateinit var element: String

        init {
            itemView.setOnClickListener {
                onSelect?.invoke(element)
            }
        }

        fun bind(value: String, isOdd: Boolean) {
            element = value
            itemView.background = if (isOdd) ContextCompat.getDrawable(itemView.context, R.color.colorAccent)
            else null
            itemView.findViewById<TextView>(R.id.text_main).text = value
            itemView.findViewById<TextView>(R.id.text_detail).text = hashCode().toString()
        }
    }

    class Header(rootView: ViewGroup) : SampleViewHolder(rootView)

    class Footer(rootView: ViewGroup, private val recyclerView: RecyclerView) : SampleViewHolder(rootView) {
        init {
            itemView.findViewById<Button>(R.id.button_returntop).setOnClickListener {
                recyclerView.smoothScrollToPosition(0)
            }
        }
    }

    enum class Type {
        Item, Header, Footer;
    }

    companion object {
        fun createHeaderHolder(parent: ViewGroup) = Header(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_header, parent, false
            ) as ViewGroup
        )

        fun createFooterHolder(parent: ViewGroup, recyclerView: RecyclerView) = Footer(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_footer, parent, false
            ) as ViewGroup,
            recyclerView
        )

        fun createItemHolder(parent: ViewGroup, onSelect: ((String) -> Unit)?) = Item(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sample, parent, false
            ) as ViewGroup,
            onSelect
        )
    }
}

class SampleAdapter(
    items: List<String>,
    private val parentRecyclerView: RecyclerView,
    private val onSelect: (String, Int) -> Unit
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
        return when(SampleViewHolder.Type.values()[viewType]) {
            SampleViewHolder.Type.Item -> SampleViewHolder.createItemHolder(parent, null)
            SampleViewHolder.Type.Header -> SampleViewHolder.createHeaderHolder(parent)
            SampleViewHolder.Type.Footer -> SampleViewHolder.createFooterHolder(parent, parentRecyclerView)
        }
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        if (holder is SampleViewHolder.Item) {
            holder.bind(items[position - 1], position % 2 == 1)
            holder.onSelect = {
                onSelect(it, position - 1)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size + 2
    }
}
