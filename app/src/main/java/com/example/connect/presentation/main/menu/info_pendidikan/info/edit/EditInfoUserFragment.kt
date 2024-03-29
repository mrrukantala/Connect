package com.example.connect.presentation.main.menu.info_pendidikan.info.edit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.connect.R
import com.example.connect.databinding.EditInfoUserFragmentBinding
import com.example.connect.utilites.DatePickerHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class EditInfoUserFragment : Fragment() {

    lateinit var binding: EditInfoUserFragmentBinding
    private val viewModel: EditInfoUserViewModel by viewModels()
    private val REQUEST_CODE = 101
    lateinit var datePicker: DatePickerHelper

    private lateinit var etNim: EditText
    private lateinit var etWa: EditText
    private lateinit var etDomisili: EditText

    private val mainNavigation: NavController? by lazy {
        activity?.findNavController(R.id.nav_host_fragment_menu)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s?.isEmpty() == true) {
                viewModel.setAllFieldNull()
            } else {
                viewModel.setAllField(
                    binding.editNim.text.toString(),
                    binding.editWa.text.toString(),
                    binding.editLokasi.text.toString()
                )
            }
        }

        override fun afterTextChanged(s: Editable?) {

            binding.button5.isEnabled = true

        }
    }

    private val args by navArgs<EditInfoUserFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            EditInfoUserFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.setAllFieldNull()


        datePicker = DatePickerHelper(requireActivity())


        binding.include9.backImage.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.circleImageView2.setOnClickListener {
            selectImageFromGallery()
        }

        binding.rg.setOnCheckedChangeListener { radioGroup, i ->
            when (radioGroup.checkedRadioButtonId) {
                R.id.rl -> viewModel.setJenisKelamin("Laki - Laki")
                R.id.rp -> viewModel.setJenisKelamin("Perempuan")
            }
        }

        if (viewModel.jenisKelamin.value.equals("Laki - Laki")) {
            binding.rl.isChecked = true
        } else if (viewModel.jenisKelamin.value.equals("Perempuan")) {
            binding.rp.isChecked = true
        }


        binding.textView42.setOnClickListener {
            showDatePickerDialog()
        }

        with(binding) {
            etNim = editNim
            etDomisili = editLokasi
            etWa = editWa
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        listOf(etNim, etWa, etDomisili).forEach {
            it.addTextChangedListener(textWatcher)
        }

        etNim.setText(viewModel.nim.value)
        etWa.setText(viewModel.wa.value)
        etDomisili.setText(viewModel.domisili.value)
        viewModel.setTglLahir(args.data?.tglLahir.toString())

        binding.button5.setOnClickListener {
            viewModel.editProfile(args.data?.id!!)
        }

        binding.data = args.data

        if (args.data?.jenisKelamin == "Laki - Laki") {
            binding.rl.isChecked = true
        } else {
            binding.rp.isChecked = true
        }
    }

    private fun selectImageFromGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        gallery.type = "image/*"
        startActivityForResult(gallery, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {

            var imageURI = data?.data

            var file = File(data?.data?.let { getRealPathFromURI(this.requireContext(), it) })

            val filePart = MultipartBody.Part.createFormData(
                "photo", file.name, RequestBody.create(
                    "image/*".toMediaTypeOrNull(), file
                )
            )
            viewModel.savePhoto(filePart)
            binding.circleImageView2.setImageURI(imageURI)
        }
    }

    private fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } catch (e: Exception) {
            Log.e("", "getRealPathFromURI Exception : $e")
            ""
        } finally {
            cursor?.close()
        }
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)

        datePicker.showDialog(d, m, y,
            object : DatePickerHelper.Callback {
                @RequiresApi(Build.VERSION_CODES.O)
                @SuppressLint("SetTextI18n", "SimpleDateFormat")
                override fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
                    val dayStr = if (dayOfMonth < 10) "0${dayOfMonth}" else "${dayOfMonth}"

                    val month = month + 1
                    val monthStr = if (month < 10) "0${month}" else "${month}"

                    val date = LocalDate.of(year, month, dayStr.toInt())
                    Log.v("DATE", date.toString())

                    val basic = date.format(DateTimeFormatter.BASIC_ISO_DATE)
                    viewModel.setTglLahir(basic)

                    binding.textView42.text = "${year} - ${monthStr} - ${dayStr}"
                }
            })
    }

    private fun observe() {
        viewModel.state.flowWithLifecycle(lifecycle)
            .onEach { state -> handleState(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: EditInfoUserState) {

        when (state) {
            is EditInfoUserState.Loading -> {
                binding.iloading.root.visibility = View.VISIBLE
            }
            is EditInfoUserState.Success -> {
                binding.iloading.root.visibility = View.GONE
                binding.iloadingsuccess.textView21.text = "Profil Berhasil Diedit"
                binding.iloadingsuccess.root.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    mainNavigation?.navigateUp()
                }, 2000)
            }
            else -> {}
        }

    }
}

