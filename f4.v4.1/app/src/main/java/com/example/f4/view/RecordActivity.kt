package com.example.f4.view
import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.`interface`.MasterApplication
import com.example.f4.`interface`.Token
import com.example.f4.adapter.RecordAdapter
import com.example.f4.data.*
import com.example.f4.databinding.ActivityRecordBinding
import kotlinx.android.synthetic.main.activity_group_setting_l.*
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.activity_sign.*
import kotlinx.android.synthetic.main.add_food_lv_item.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RecordActivity : RecordBaseActivity(), DialogInterface1, DialogInterface2, DialogInterface3 {

    private lateinit var activityResultCamera: ActivityResultLauncher<Intent>
    private lateinit var activityResultGallery: ActivityResultLauncher<Intent>

    private lateinit var activityResultAddFood: ActivityResultLauncher<Intent>

    private lateinit var addFoodlist: RecyclerView
    lateinit var RecordAdapter: RecordAdapter

    val binding by lazy { ActivityRecordBinding.inflate(layoutInflater) }

    var userSelect : Int = 0

    //권한 플래그 값 정의
    val FLAG_PERM_CAMERA = 9
    val FLAG_PERM_STORAGE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addFoodlist = findViewById(R.id.addFoodlist)


        var date = intent.getStringExtra("date")
        lateinit var dateid :LocalDate
        if(date != null){
            Log.d("데이트2", date)
            dateid = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)

        }

        binding.backRecord.setOnClickListener {
            var intent = Intent(this, DietDayActivity::class.java)
            intent.putExtra("dateGetRecord", date)
            startActivity(intent)
            finish()
        }

        var photoURI_1 = "0"
        var photoURI_2 = "0"
        var photoURI_3 = "0"

        requirePermissions(arrayOf(Manifest.permission.CAMERA), FLAG_PERM_CAMERA)
        requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), FLAG_PERM_STORAGE)

        //카메라를 찍은 후에 호출
        activityResultCamera = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == RESULT_OK) {
                //미리보기 이미지
//                val bitmap = it.data?.extras?.get("data") as Bitmap
//                binding.imgFood1.setImageBitmap(bitmap)
                //원본 이미지
                realUri?.let { uri ->
                    val bitmap = loadBitmap(uri)
                    if (userSelect == 1) {
                        Log.d("soso 1:", "${uri}")
                        photoURI_1 = uri.toString()
                        savePhotoData(photoURI_1, "1")
                        binding.imgFood1.setImageBitmap(bitmap)
                    }
                    else if (userSelect == 2) {
                        Log.d("soso 2 :", "${uri}")
                        photoURI_2 = uri.toString()
                        savePhotoData(photoURI_2, "2")
                        binding.imgFood2.setImageBitmap(bitmap)
                    }
                    else {
                        Log.d("soso 3 :", "${uri}")
                        photoURI_3 = uri.toString()
                        savePhotoData(photoURI_3, "3")
                        binding.imgFood3.setImageBitmap(bitmap)
                    }
                    realUri = null
                }
            }
        }

        //갤러리 호출
        activityResultGallery = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == RESULT_OK) {
                it.data?.data?.let { uri ->
                    if (userSelect == 11) {
                        Log.d("soso 11:", "${uri}")
                        photoURI_1 = uri.toString()
                        savePhotoData(photoURI_1, "1")
                        binding.imgFood1.setImageURI(uri)
                    }
                    else if (userSelect == 22) {
                        Log.d("soso 22:", "${uri}")
                        photoURI_2 = uri.toString()
                        savePhotoData(photoURI_2, "2")
                        binding.imgFood2.setImageURI(uri)
                    }
                    else {
                        Log.d("soso 33:", "${uri}")
                        photoURI_3 = uri.toString()
                        savePhotoData(photoURI_3, "3")
                        binding.imgFood3.setImageURI(uri)
                    }
                }
            }
        }

        val foodList = mutableListOf<Foods>()
        val recordList = mutableListOf<Record_food>()

        fun addItem(foodItem:Long, amountItem:Double){
        val item = Foods(1,  0.0)
            item.foodListId = foodItem
            item.amount = amountItem
            foodList.add(item)
        }

        fun addRecord(nameRecord:String, amountRecord:Int, kcalRecord: Int){
            val item = Record_food("",  0,0)
            item.foodName = nameRecord
            item.amount = amountRecord
            item.kcal = kcalRecord
            recordList.add(item)
        }

        activityResultAddFood = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == RESULT_OK) {
                val data = it.data

                // data 가 nulll 일 수 있기 때문에 nullsafety 처리 필요
                var foodListId = data?.getLongExtra("foodListId", 0)!!.toLong() //foodListId
                var foodName = data?.getStringExtra("FoodName").toString()
                var amount = data?.getIntExtra("Amount", 0).toInt()
                var kcal = data?.getDoubleExtra("Kcal", 0.0).toDouble()

                addItem(foodListId, amount.toDouble())
                addRecord(foodName, amount, kcal.toInt())

                setAdapter(recordList)

                addFoodlist?.visibility = View.INVISIBLE
                if(data?.hasExtra("foodListId")){
                    addFoodlist?.visibility = View.VISIBLE
                }
            }
        }

        //(2) 음식 데이터 추가
        btn_add_food.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            intent.putExtra("date", date)
            Log.d("데이트1", "${date}")
            activityResultAddFood.launch(intent)
        }

        var mealtype = ""

        btn_save.setOnClickListener {
            val photolist:List<Images> = listOf(Images(photoURI_1), Images(photoURI_2), Images(photoURI_3))

            if (btn_breakfast.isChecked == true)
                mealtype = "MORNING"
            else if (btn_lunch.isChecked == true)
                mealtype = "LUNCH"
            else if (btn_dinner.isChecked == true)
                mealtype = "EVENING"
            else
                mealtype = "SNACK"

            val memo = memo.text.toString()

            (application as MasterApplication).service.record(Token.token, dateid,
                Record(mealtype, memo, foodList, photolist)).enqueue(object :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("RECORD fail", "통신 실패")
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful.not()) {
                        Log.d("RECORD fail", "응답 실패")
                        return
                    }
                    Log.d("RECORD response", "${response}")
                }
            })
            val intent = Intent(this, DietDayActivity::class.java)
            intent.putExtra("dateGetRecord", date)
            startActivity(intent)
            finish()
        }

    }

    fun setViews() {
        //카메라 요청시 권한을 먼저 체크하고 승인되었으면 카메라를 연다
        binding.imgFood1.setOnClickListener{
            val myDialog1 = RecordDialog1(this, this)
            myDialog1.show()
        }
        binding.imgFood2.setOnClickListener{
            val myDialog2 = RecordDialog2(this, this)
            myDialog2.show()
        }
        binding.imgFood3.setOnClickListener{
            val myDialog3 = RecordDialog3(this, this)
            myDialog3.show()
        }
    }

    //원본 이미지의 주소를 저장할 변수
    var realUri: Uri? = null

    //카메라에 찍은 사진을 저장하기 위한 Uri를 넘겨줌
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createImageUri(newFilename(),"image/jpg")?.let{ uri ->
            realUri = uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            activityResultCamera.launch(intent)
        }
    }

    fun opengallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        activityResultGallery.launch(intent)
    }

    //원본 이미지를 저장할 Uri를 MediaStore(데이터베이스)에 생성하는 메서드
    fun createImageUri(filename:String, mimeType:String) : Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        //사진이 저장되는 테이블의 Uri 반환
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    //파일 이름을 생성하는 메서드
    fun newFilename(): String {
        val sdf = SimpleDateFormat("yyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.jpg"
    }

    //원본 이미지를 불러오는 메서드
    fun loadBitmap(photoUri: Uri) : Bitmap? {
        var image:Bitmap? = null
        try {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
                val source = ImageDecoder.createSource(contentResolver, photoUri)
                image = ImageDecoder.decodeBitmap(source)
            } else {
                image = MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
        return image
    }

    //    //원본 이미지를 불러오는 메서드 //코드 줄인 버전
//    fun loadBitmap(photiUri: Uri) : Bitmap? {
//        try {
//            return if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
//                val source = ImageDecoder.createSource(contentResolver, photiUri)
//                ImageDecoder.decodeBitmap(source)
//            } else {
//                MediaStore.Images.Media.getBitmap(contentResolver, photiUri)
//            }
//        } catch (e:Exception) {
//            e.printStackTrace()
//        }
//        return null
//    }

    override fun permissionGranted(requestCode: Int) {
        when(requestCode){
            FLAG_PERM_STORAGE -> setViews()
            FLAG_PERM_CAMERA -> setViews()
        }
    }

    override fun permissionDenied(requestCode: Int) {
        when(requestCode){
            FLAG_PERM_STORAGE -> {
                Toast.makeText(this, "음식 추가에 실패했습니다.\n저장소 권한을 승인해주세요.", Toast.LENGTH_SHORT).show()
            }
            FLAG_PERM_CAMERA -> {
                Toast.makeText(this, "음식 추가에 실패했습니다.\n카메라 권한을 승인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun CameraClicked1() {
        userSelect = 1
        openCamera()
    }

    override fun GalleryClicked1() {
        userSelect = 11
        opengallery()
    }

    override fun CameraClicked2() {
        userSelect = 2
        openCamera()
    }

    override fun GalleryClicked2() {
        userSelect = 22
        opengallery()
    }

    override fun CameraClicked3() {
        userSelect = 3
        openCamera()
    }

    override fun GalleryClicked3() {
        userSelect =33
        opengallery()
    }

    //Photo data를 SharedPreferences에 저장
    fun savePhotoData(uri: String, position: String) {
        val sp = getSharedPreferences("photo_data", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(uri, position)
        editor.commit()
    }

    private fun setAdapter(data: List<Record_food>) {
        RecordAdapter = RecordAdapter(this, data)
        addFoodlist.adapter = RecordAdapter
        addFoodlist.layoutManager = LinearLayoutManager(this)
    }

//    // 절대경로 변환
//    fun absolutelyPath(path: Uri): String {
//        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
//        var c: Cursor = contentResolver.query(path, proj, null, null, null)!!
//        var index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        c.moveToFirst()
//        var result = c.getString(index)
//        return result
//    }
}