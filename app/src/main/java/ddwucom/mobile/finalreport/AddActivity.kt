package ddwucom.mobile.finalreport

import android.app.DatePickerDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import ddwucom.mobile.finalreport.databinding.ActivityAddBinding
import ddwucom.mobile.finalreport.databinding.ActivityMainBinding

class AddActivity : AppCompatActivity() {
    val TAG = "AddActivity"

    lateinit var addBinding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        var diaryDao = DiaryDao(this)

        addBinding.photo.setImageResource(R.drawable.school)

        addBinding.btnAdd.setOnClickListener{
            var date = addBinding.etAddDate.text.toString()
            val weather = addBinding.etAddWeather.text.toString()
            val photo = R.drawable.school
            val score = addBinding.etAddScore.text.toString()
            val feeling = addBinding.etAddFeeling.text.toString()
            val dContent = addBinding.etAddContent.text.toString()
            val newDto = DiaryDto(0,date, weather, score, photo, feeling, dContent)

            if(date.length==0) {
                Toast.makeText(this@AddActivity, "날짜는 필수 항목입니다.", Toast.LENGTH_SHORT).show()
            }else{
                if( diaryDao.addDiary(newDto)>0 ){
                    setResult(RESULT_OK)
                }else{
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }

        addBinding.btnAddDate.setOnClickListener{
            var dateD :String? = null

            DatePickerDialog(this, object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    if(day<10){
                        dateD = "${year}.0${month+1}.0${day}"
                    }else{
                        dateD = "${year}.0${month+1}.${day}"
                    }
                    if(dateD!=null){
                        addBinding.etAddDate.setText(dateD)
                    }
                }
            }, 2023,5,24).show()
        }

        addBinding.btnCanceled.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

    }

}