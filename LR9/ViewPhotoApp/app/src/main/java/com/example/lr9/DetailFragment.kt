package com.example.lr9

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lr9.databinding.DetailFragmentBinding


class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding

    private var photoPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            photoPath = it.getString(PHOTO_PATH_KEY)
            photoPath?.let { path ->
                Glide.with(this)
                    .load(path)
                    .into(binding.imageViewPhoto)
            }
        }
    }

    companion object {
        private const val PHOTO_PATH_KEY = "photo_path"

        fun newInstance(photoPath: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(PHOTO_PATH_KEY, photoPath)
            fragment.arguments = args
            return fragment
        }
    }
}
