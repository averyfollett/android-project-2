package com.example.project2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LifeFragment : Fragment(R.layout.fragment_life) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: LifeGridAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = getView()?.findViewById<RecyclerView>(R.id.lifeRecyclerView) ?: recyclerView

        initView(view)
    }

    private fun initView(view: View) {
        recyclerView.layoutManager = GridLayoutManager(view.context, 20)
        recyclerViewAdapter = LifeGridAdapter(view.context)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.setHasFixedSize(true)
    }

    fun next() {
        //recyclerViewAdapter.notifyDataSetChanged()
        //Log.i("number alive", recyclerViewAdapter.getNumberAlive().toString())
        //Log.i("most recent index", recyclerViewAdapter.getMostRecentIndex().toString())
        //recyclerViewAdapter.nextGeneration()
        //recyclerViewAdapter.getAliveCoordinates()
        recyclerViewAdapter.nextGeneration()
        //Log.i("x", recyclerViewAdapter.getXCoordinateFromID(recyclerViewAdapter.getMostRecentIndex()).toString())
        //Log.i("y", recyclerViewAdapter.getYCoordinateFromID(recyclerViewAdapter.getMostRecentIndex()).toString())
        //Log.i("id", recyclerViewAdapter.getIDFromCoordinates(recyclerViewAdapter.getXCoordinateFromID(recyclerViewAdapter.getMostRecentIndex()), recyclerViewAdapter.getYCoordinateFromID(recyclerViewAdapter.getMostRecentIndex())).toString())
        //Log.i("neighbors", recyclerViewAdapter.getNeighbors(43).joinToString())

    }

}