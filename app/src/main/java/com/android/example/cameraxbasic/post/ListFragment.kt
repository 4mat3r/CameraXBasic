package com.android.example.cameraxbasic.post

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.android.example.cameraxbasic.R
import com.android.example.cameraxbasic.camera.CameraFragmentDirections
import com.android.example.cameraxbasic.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: ListFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.list_fragment, container, false)



        binding.lifecycleOwner = this
        binding.stopButton.setOnClickListener(View.OnClickListener {
            newPost()
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun newPost( ) {
        Log.i("ListViewModel","SOM TUUUUUUUUUUUUU")
        Navigation.findNavController(
                requireActivity(), R.id.fragment_container
        ).navigate(ListFragmentDirections.actionListFragmentToCameraFragment())
    }

}