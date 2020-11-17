package com.example.project2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LifeFragment : Fragment(R.layout.fragment_life) {

    private lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: LifeGridAdapter
    private lateinit var lifeGridModel: LifeGridModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = getView()?.findViewById<RecyclerView>(R.id.lifeRecyclerView) ?: recyclerView
        lifeGridModel = LifeGridModel()

        initView(view)
        lifeGridModel.init()
    }

    private fun initView(view: View) {
        recyclerView.layoutManager = GridLayoutManager(view.context, 20)
        recyclerViewAdapter = LifeGridAdapter(view.context, lifeGridModel)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.setHasFixedSize(true)
    }

    fun next() {
        lifeGridModel.nextGeneration()

        recyclerViewAdapter.notifyDataSetChanged()
    }

}