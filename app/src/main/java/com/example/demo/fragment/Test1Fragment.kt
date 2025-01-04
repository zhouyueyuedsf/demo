package com.example.demo.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.example.demo.R
import kotlinx.android.synthetic.main.fragment_test1.btn2
import kotlinx.android.synthetic.main.fragment_test1.btn3
import kotlinx.android.synthetic.main.fragment_test1.test_tv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Test1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Test1Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onAttach(context: Context) {
//        setRetainInstance(true)
        super.onAttach(context)
//        childFragmentManager.beginTransaction()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: frag = $this ${Thread.currentThread()}")

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i(TAG, "onSaveInstanceState: ")
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewStateRestored: ")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: $this")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        btn2.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        val tf = Test1Fragment.newInstance("1", "2")
        val fragmentManager = childFragmentManager
        GlobalScope.launch(Dispatchers.IO) {
            fragmentManager.beginTransaction().add(R.id.test_frag_container, tf).setMaxLifecycle(tf, Lifecycle.State.CREATED).commit()
        }

        btn3.setOnClickListener {
            fragmentManager.beginTransaction().setMaxLifecycle(tf, Lifecycle.State.RESUMED).commit()
        }
    }

    override fun onResume() {
        Log.i(TAG, "onResume: $this")
        super.onResume()
    }
    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView: $this")
        super.onDestroyView()
    }
    override fun onDestroy() {
        Log.i(TAG, "onDestroy: $this")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.i(TAG, "onDetach: $this")
        super.onDetach()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Test1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Test1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}