package com.example.lr10

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lr10.adapter.PhotoAdapter
import java.text.SimpleDateFormat
import java.io.File
import java.io.IOException
import java.util.*

class GalleryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var photoAdapter: PhotoAdapter

    private val photoPaths = mutableListOf<String>()

    private lateinit var currentPhotoPath: String

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        photoAdapter = PhotoAdapter(photoPaths)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = photoAdapter
        }
//        photoAdapter.notifyItemInserted(photoPaths.size - 1)
        photoAdapter.notifyDataSetChanged()

//        val manager = getNotificationManager()

        view.findViewById<Button>(R.id.btnTakePhoto).setOnClickListener {
            dispatchTakePictureIntent()

//            Log.d("--notice", "after took a photo")
//            val notification = NotificationCompat.Builder(requireContext(), "normal")
//                .setContentTitle("This is content title")
//                .setContentText("This is content text")
//                .setSmallIcon(R.drawable.icon)
//                .setLargeIcon(
//                    BitmapFactory.decodeResource(
//                        resources,
//                        R.drawable.icon
//                    )
//                )
//                .build()
//            manager.notify(1, notification)
//
//            Log.d("--notice", "End--")
        }
        return view
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            // Створення файлу для зображення
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Помилка під час створення файлу
                null
            }
            // Продовження тільки якщо файл було успішно створено
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.lr10.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Створення імені файлу зображення
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* префікс */
            ".jpg", /* суфікс */
            storageDir /* директорія */
        ).apply {
            // Збереження шляху файлу для використання з намірами ACTION_VIEW
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Додавання шляху до списку фотографій
            photoPaths.add(currentPhotoPath)
            photoAdapter.notifyItemInserted(photoPaths.size - 1)
//            photoAdapter.notifyDataSetChanged()

            // Менеджер сповіщень
            val manager = getNotificationManager()

            // Сповіщення
            notify(manager)
        }
    }

    private fun getNotificationManager(): NotificationManager {
        val manager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "normal", "Normal",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)

            Log.d("--notice", "if block")
        }

        return manager
    }

    private fun notify(manager: NotificationManager) {
        Log.d("--notice", "after took a photo")
        val notification = NotificationCompat.Builder(requireContext(), "normal")
            .setContentTitle("This is content title")
//            .setContentText("This is content text")
//            .setSmallIcon(R.drawable.icon)
//            .setLargeIcon(
//                BitmapFactory.decodeResource(
//                    resources,
//                    R.drawable.icon
//                )
//            )
//            .build()
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.icon))
            )
            .setSmallIcon(R.drawable.icon)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.icon
                )
            )
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()

        manager.notify(1, notification)

        Log.d("--notice", "End--")
    }
}
