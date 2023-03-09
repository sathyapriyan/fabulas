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
import com.sandsindia.fabulas.data.remote.NetworkResult
import com.sandsindia.fabulas.databinding.FragmentUserDetailsBinding
import com.sandsindia.fabulas.models.UserResponse
import com.sandsindia.fabulas.utils.CommonUtil
import com.sandsindia.fabulas.utils.CommonUtil.loadImageFromCoil
import com.sandsindia.fabulas.utils.CommonUtil.makeAddress
import com.sandsindia.fabulas.utils.CommonUtil.makeC0mpaney
import com.sandsindia.fabulas.utils.CommonUtil.makeProfieImageUrl
import com.sandsindia.fabulas.viewmodels.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UserDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentUserDetailsBinding
    private val viewModel: UserDetailsViewModel by viewModels()
    private val args: UserDetailsFragmentArgs by navArgs()
    private var dataList: UserResponse? = null

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
        binding = FragmentUserDetailsBinding.inflate(layoutInflater)
        val url=CommonUtil.makeUserDetailsUrl(args.userResponce.id)
        viewModel.getUserDetails(url = url, context = requireContext())
        viewModel.userDetailsResponse.observe(viewLifecycleOwner){ response ->
            when(response) {

                is NetworkResult.Success -> {

                    println("Login Response --> ${response.data}")
                    binding.textViewName.text=response.data!!.name
                    binding.textViewUserName.text=response.data.username
                    binding.textMobileNumber.text=response.data.phone
                    binding.textEmail.text=response.data.email

                    val address=makeAddress(response.data.address!!.street,
                        response.data.address!!.city,
                        response.data.address!!.zipcode)
                    val company=makeC0mpaney(response.data.company!!.name,
                        response.data.company!!.bs,
                        response.data.company!!.catchPhrase)
                    val profileUrl=makeProfieImageUrl(requireContext(), response.data.id!!)

                    binding.textAddress.text=address
                    binding.textCompaneyName.text=company
                    binding.imgViewProfile.loadImageFromCoil(profileUrl)
                    dataList= response.data

                }

                is NetworkResult.Error -> {

                    CommonUtil.alertDialogError(requireContext(), response.message.toString())

                }

                is NetworkResult.Loading -> {


                }

            }

        }
        binding.cardViewShowPost.setOnClickListener {
            val directions = UserDetailsFragmentDirections.actionUserDetailsFragmentToPostsFragment(dataList!!)
            findNavController().navigate(directions = directions)
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}