package com.example.project2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LifeFragment : Fragment(R.layout.fragment_life) {

    //vars for all object needed to run the game of life
    private lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: LifeGridAdapter
    lateinit var lifeGridModel: LifeGridModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //link the recycler view to its object by id
        recyclerView = getView()?.findViewById(R.id.lifeRecyclerView) ?: recyclerView

        //initialize the life grid model
        lifeGridModel = LifeGridModel(view.context)

        //run init functions
        initView(view)
        lifeGridModel.init()
    }

    private fun initView(view: View) {
        //give the recycler view a grid layout 20 cells wide
        recyclerView.layoutManager = GridLayoutManager(view.context, 20)
        //create the grid adapter and link it to the grid model
        recyclerViewAdapter = LifeGridAdapter(view.context, lifeGridModel)
        //give the recycler view our custom adapter
        recyclerView.adapter = recyclerViewAdapter
        //might not be needed but keeps the recycler view from resizing
        recyclerView.setHasFixedSize(true)
    }

    fun next() {
        //tell the grid model to advance 1 generation
        lifeGridModel.nextGeneration()
        //tell the recycler view that data has changed so it will refresh
        update()
    }

    fun update() {
        //tell the recycler view that data has changed so it will refresh
        recyclerViewAdapter.notifyDataSetChanged()
    }
}