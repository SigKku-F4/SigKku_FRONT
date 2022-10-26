package com.example.f4.view
import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.f4.R
import com.example.f4.interfaces.MasterApplication
import com.example.f4.interfaces.Token
import com.example.f4.adapter.RecordAdapter
import com.example.f4.data.*
import com.example.f4.databinding.ActivityChangeRecordBinding
import kotlinx.android.synthetic.main.activity_change_record.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom

class ChangeRecordActivity : RecordBaseActivity(), DialogInterface1, DialogInterface2, DialogInterface3 {

    private lateinit var activityResultCamera: ActivityResultLauncher<Intent>
    private lateinit var activityResultGallery: ActivityResultLauncher<Intent>

    private lateinit var activityResultAddFood: ActivityResultLauncher<Intent>

    private lateinit var addFoodlist: RecyclerView
    lateinit var RecordAdapter: RecordAdapter

    val binding by lazy { ActivityChangeRecordBinding.inflate(layoutInflater) }

    var userSelect : Int = 0

    val FLAG_PERM_CAMERA = 9
    val FLAG_PERM_STORAGE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        addFoodlist = findViewById(R.id.addFoodlist)

        val foodList = mutableListOf<FoodsC>()
        val recordList = mutableListOf<Record_food>()

        var photoURI_1 = "0"
        var photoURI_2 = "0"
        var photoURI_3 = "0"

        fun addItem(foodIdItem:Long, foodItem:Long, amountItem:Double){
            val item = FoodsC(1, 1,  0.0)
            item.foodId = foodIdItem
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

        var date = intent.getStringExtra("date")
        lateinit var dateid :LocalDate
        if(date != null){
            dateid = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)

        }

        var mealtype = ""

        var meal = intent.getSerializableExtra("meal") as Meal

        if(meal.mealType == "MORNING"){
            radioGroupC.check(R.id.btn_breakfastC)
            mealtype = "MORNING"
        }
        else if(meal.mealType == "LUNCH"){
            radioGroupC.check(R.id.btn_lunchC)
            mealtype = "LUNCH"
        }
        else if(meal.mealType == "EVENING"){
            radioGroupC.check(R.id.btn_dinnerC)
            mealtype = "EVENING"
        }
        else if(meal.mealType == "SNACK"){
            radioGroupC.check(R.id.btn_snackC)
            mealtype = "SNACK"
        }

        if (meal.imgs[0] != "0"){
            val uri: Uri = Uri.parse(meal.imgs[0])
            photoURI_1 = meal.imgs[0]
            binding.imgFood1.setImageURI(uri)
        }
        if (meal.imgs[1] != "0"){
            val uri: Uri = Uri.parse(meal.imgs[1])
            photoURI_2 = meal.imgs[1]
            binding.imgFood2.setImageURI(uri)
        }
        if (meal.imgs[2] != "0"){
            val uri: Uri = Uri.parse(meal.imgs[2])
            photoURI_3 = meal.imgs[2]
            binding.imgFood3.setImageURI(uri)
        }

        for (i in 0..meal.foods.size - 1) {
            addItem(meal.foods[i].foodId, meal.foods[i].foodListId, meal.foods[i].amount)
            addRecord(meal.foods[i].name, meal.foods[i].amount.toInt(), meal.foods[i].nutrient.calorie.toInt())
        }

        setAdapter(recordList)

        addFoodlist?.visibility = View.VISIBLE

        binding.backRecord.setOnClickListener {
            var intent = Intent(this, DietDayActivity::class.java)
            intent.putExtra("dateGetRecord", date)
            startActivity(intent)
            finish()
        }

        requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), FLAG_PERM_STORAGE)
        requirePermissions(arrayOf(Manifest.permission.CAMERA), FLAG_PERM_CAMERA)

        activityResultCamera = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == RESULT_OK) {
                realUri?.let { uri ->
                    val bitmap = loadBitmap(uri)
                    if (userSelect == 1) {
                        photoURI_1 = uri.toString()
                        savePhotoData(photoURI_1, "1")
                        binding.imgFood1.setImageBitmap(bitmap)
                    }
                    else if (userSelect == 2) {
                        photoURI_2 = uri.toString()
                        savePhotoData(photoURI_2, "2")
                        binding.imgFood2.setImageBitmap(bitmap)
                    }
                    else {
                        photoURI_3 = uri.toString()
                        savePhotoData(photoURI_3, "3")
                        binding.imgFood3.setImageBitmap(bitmap)
                    }
                    realUri = null
                }
            }
        }

        activityResultGallery = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if (it.resultCode == RESULT_OK) {
                it.data?.data?.let { uri ->
                    if (userSelect == 11) {
                        photoURI_1 = uri.toString()
                        savePhotoData(photoURI_1, "1")
                        binding.imgFood1.setImageURI(uri)
                    }
                    else if (userSelect == 22) {
                        photoURI_2 = uri.toString()
                        savePhotoData(photoURI_2, "2")
                        binding.imgFood2.setImageURI(uri)
                    }
                    else {
                        photoURI_3 = uri.toString()
                        savePhotoData(photoURI_3, "3")
                        binding.imgFood3.setImageURI(uri)
                    }
                }
            }
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

                val random = ThreadLocalRandom.current().nextLong(1, 50)
                addItem(random, foodListId, amount.toDouble())
                addRecord(foodName, amount, kcal.toInt())
                setAdapter(recordList)

                addFoodlist?.visibility = View.INVISIBLE
                if(data?.hasExtra("foodListId")){
                    addFoodlist?.visibility = View.VISIBLE
                }
            }
        }

        btn_add_food.setOnClickListener {
            val intent = Intent(this, AddFoodActivity::class.java)
            intent.putExtra("date", date)
            activityResultAddFood.launch(intent)
        }

        btn_change.setOnClickListener {
            val photolist:List<Images> = listOf(Images(photoURI_1), Images(photoURI_2), Images(photoURI_3))

            if (btn_breakfastC.isChecked == true)
                mealtype = "MORNING"
            else if (btn_lunchC.isChecked == true)
                mealtype = "LUNCH"
            else if (btn_dinnerC.isChecked == true)
                mealtype = "EVENING"
            else
                mealtype = "SNACK"

            val memo = memo.text.toString()

            (application as MasterApplication).service.recordChange(Token.token, dateid,
                Change_record(meal.mealId, mealtype, memo, foodList, photolist)).enqueue(object :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful.not()) {
                        return
                    }
                }
            })
            val intent = Intent(this, DietDayActivity::class.java)
            intent.putExtra("dateGetRecord", date)
            startActivity(intent)
            finish()
        }

    }

    fun setViews() {
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

    var realUri: Uri? = null

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

    fun createImageUri(filename:String, mimeType:String) : Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    fun newFilename(): String {
        val sdf = SimpleDateFormat("yyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.jpg"
    }

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

}