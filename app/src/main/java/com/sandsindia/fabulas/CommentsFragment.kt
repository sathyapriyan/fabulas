package com.sandsindia.fabulas

import android.annotation.SuppressLint
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
import com.sandsindia.fabulas.databinding.FragmentCommentsBinding
import com.sandsindia.fabulas.models.CommentResponse
import com.sandsindia.fabulas.presentation.adapters.CommentsListAdapter
import com.sandsindia.fabulas.utils.CommonUtil
import com.sandsindia.fabulas.utils.CommonUtil.loadImageFromCoil
import com.sandsindia.fabulas.utils.CommonUtil.loadPostImageFromCoil
import com.sandsindia.fabulas.viewmodels.CommentListViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CommentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCommentsBinding
    private val viewModel: CommentListViewModel by viewModels()

    private val args: CommentsFragmentArgs by navArgs()

    private val dataList: MutableList<CommentResponse> = mutableListOf()
    private val recyclerViewAdapter : CommentsListAdapter by lazy{
        CommentsListAdapter(dataList) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCommentsBinding.inflate(layoutInflater)

        val imageUrl1 = CommonUtil.makeProfieImageUrl(requireContext(), args.postResponce.userId!!)
        val imageUrl2 = CommonUtil.makePostImageUrl(requireContext(), args.postResponce.id!!)
        val updatedTime = CommonUtil.updatedTime(requireContext(), args.postResponce.id!!)

        binding.textTitle.text = args.postResponce.title
        binding.textBody.text = args.postResponce.body
        binding.textTime.text = updatedTime
        binding.textLikeCount.text = "10"
        binding.textCommentCount.text = "10"
        binding.imageUser.loadImageFromCoil(imageUrl1)
        binding.imageViewPost.loadPostImageFromCoil(imageUrl2)
        val url= CommonUtil.makeCommentsUrl(args.postResponce.id)

        binding.textShowComments.setOnClickListener {
            val directions = CommentsFragmentDirections.actionCommentsFragmentToPostsFragment(args.userResponce)
            findNavController().navigate(directions = directions)
        }

        // Refresh function for the layout
        binding.cardViewRefresh.setOnClickListener {
            viewModel.getComments(url = url, context = requireContext())
        }


        viewModel.getComments(url = url, context = requireContext())
        viewModel.commentListResponse.observe(viewLifecycleOwner){ response ->

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

        binding.recyclerViewCommentsList.apply {
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
         * @return A new instance of fragment CommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}