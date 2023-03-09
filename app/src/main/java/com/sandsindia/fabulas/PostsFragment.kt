package com.sandsindia.fabulas

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.sandsindia.fabulas.data.remote.NetworkResult
import com.sandsindia.fabulas.databinding.FragmentPostsBinding
import com.sandsindia.fabulas.models.PostResponse
import com.sandsindia.fabulas.presentation.adapters.PostListAdapter
import com.sandsindia.fabulas.utils.CommonUtil
import com.sandsindia.fabulas.viewmodels.PostListViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PostsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentPostsBinding
    private val viewModel: PostListViewModel by viewModels()

    private val args: PostsFragmentArgs by navArgs()

    private val dataList: MutableList<PostResponse> = mutableListOf()
    private val recyclerViewAdapter : PostListAdapter by lazy{
        PostListAdapter(dataList) {
            val directions = PostsFragmentDirections.actionPostsFragmentToCommentsFragment(it,args.userResponce)
            findNavController().navigate(directions = directions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostsBinding.inflate(layoutInflater)
        val url= CommonUtil.makePostUrl(args.userResponce.id)
        viewModel.getPosts(url = url, context = requireContext())
        viewModel.postListResponse.observe(viewLifecycleOwner){ response ->

            when(response) {

                is NetworkResult.Success -> {

                    println("Login Response --> ${response.data}")
                    dataList.clear()
                    response.data?.forEach {
                        dataList.add(it)
                    }

                    setupRecyclerView()
                }

                is NetworkResult.Error -> {


                    CommonUtil.alertDialogError(requireContext(), response.message.toString())

                }

                is NetworkResult.Loading -> {


                }

            }

        }
        return binding.root
    }
    private fun setupRecyclerView() {

        binding.recyclerViewPostList.apply {
            adapter = recyclerViewAdapter
            layoutManager = GridLayoutManager(
                context,
                1
            )
            isNestedScrollingEnabled = false
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}