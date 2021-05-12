package com.example.aulaite

import android.content.Intent
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.util.*
import kotlin.concurrent.timer

class ListActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_USER_EDIT = 42
    }

    private val adapter by lazy {
        SampleAdapter(
            (0 until 100).map { User(it, "Usuário $it") },
            findViewById(R.id.list),
            this::handleSelection
        )
    }

    private fun handleSelection(item: User, at: Int) {
        Toast.makeText(this, "Selecionou linha $at que exibe $item", Toast.LENGTH_SHORT).show()
        startActivityForResult(UserActivity.newIntent(this, item), REQUEST_USER_EDIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_USER_EDIT -> {
                if (resultCode == RESULT_OK) {
                    val updatedUser = UserActivity.getIntentuser(data!!)
                    val newItems = adapter.items.toMutableList()
                    val updatedUserIndex = newItems.indexOfFirst {
                        it.id == updatedUser.id
                    }
                    newItems[updatedUserIndex] = updatedUser
                    adapter.items = newItems
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        findViewById<SwipeRefreshLayout>(R.id.refresh).let { refresh ->
            refresh.setOnRefreshListener {
                timer(
                    name = "",
                    daemon = false,
                    initialDelay = 5000,
                    period = Long.MAX_VALUE
                ) {
                    runOnUiThread {
                        adapter.items = (0 until 100).map { User(it, "User ${Date()} $it") }
                        refresh.isRefreshing = false
                    }
                    cancel()
                }
            }
        }

        findViewById<RecyclerView>(R.id.list).let {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                .also { manager ->
                    manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position == 0 || position == adapter.itemCount - 1) 2
                                else 1
                        }
                    }
                }
        }
        findViewById<Button>(R.id.button_add10).setOnClickListener {
            adapter.items = adapter.items +
                    (adapter.itemCount until (adapter.itemCount + 10))
                        .map { User(it, "Elemento $it") }
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
        var onSelect: ((User) -> Unit)? = null
    ) : SampleViewHolder(rootView) {
        private lateinit var element: User

        init {
            itemView.setOnClickListener {
                onSelect?.invoke(element)
            }
        }

        fun bind(value: User, isOdd: Boolean) {
            element = value
            itemView.background = if (isOdd) ContextCompat.getDrawable(itemView.context, R.color.colorAccent)
            else null
            itemView.findViewById<TextView>(R.id.text_main).text = value.username
            itemView.findViewById<TextView>(R.id.text_detail).text = value.id.toString()
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

        fun createItemHolder(parent: ViewGroup, onSelect: ((User) -> Unit)?) = Item(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_sample, parent, false
            ) as ViewGroup,
            onSelect
        )
    }
}

class SampleAdapter(
    items: List<User>,
    private val parentRecyclerView: RecyclerView,
    private val onSelect: (User, Int) -> Unit
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
