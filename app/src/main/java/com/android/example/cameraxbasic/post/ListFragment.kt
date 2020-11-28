package com.android.example.cameraxbasic.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.example.cameraxbasic.R
import com.android.example.cameraxbasic.databinding.ListFragmentBinding
import com.android.example.cameraxbasic.models.MediaObject
import com.android.example.cameraxbasic.utils.PlayerViewAdapter.Companion.playIndexThenPausePreviousPlayer
import com.android.example.cameraxbasic.utils.PlayerViewAdapter.Companion.releaseAllPlayers
import com.android.example.cameraxbasic.utils.RecyclerViewScrollListener
import com.android.example.cameraxbasic.viewModels.MediaViewModel

class ListFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var mAdapter: ListAdapter? = null
    private val modelList = ArrayList<MediaObject>()

    // for handle scroll and get first visible item index
    private lateinit var scrollListener: RecyclerViewScrollListener

    private lateinit var binding: ListFragmentBinding

    private fun newPost( ) {
        Log.i("ListViewModel","SOM TUUUUUUUUUUUUU")
        Navigation.findNavController(
                requireActivity(), R.id.fragment_container
        ).navigate(ListFragmentDirections.actionListFragmentToCameraFragment())
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil.inflate(
                inflater, R.layout.list_fragment, container, false)

        binding.lifecycleOwner = this
        binding.stopButton.setOnClickListener(View.OnClickListener {
            newPost()
        })

        findViews(binding.root)

        return binding.root

    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()

        // load data
        val model: MediaViewModel by viewModels()
        model.getMedia().observe(requireActivity(), {
            mAdapter?.updateList(arrayListOf(*it.toTypedArray()))
        })
    }

    private fun findViews(view: View) {
        recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
    }

    private fun setAdapter() {
        mAdapter = ListAdapter(requireActivity(), modelList)
        recyclerView!!.setHasFixedSize(true)

        // use a linear layout manager
        val layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = mAdapter
        scrollListener = object : RecyclerViewScrollListener() {
            override fun onItemIsFirstVisibleItem(index: Int) {
                Log.d("visible item index", index.toString())
                // play just visible item
                if (index != -1)
                    playIndexThenPausePreviousPlayer(index)
            }

        }
        recyclerView!!.addOnScrollListener(scrollListener)
        mAdapter!!.SetOnItemClickListener(object : ListAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, model: MediaObject?) {

            }
        })
    }

    override fun onPause() {
        super.onPause()
        releaseAllPlayers()
    }
}