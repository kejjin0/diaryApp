package ddwucom.mobile.finalreport

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import ddwucom.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        val dto = intent.getSerializableExtra("dto") as DiaryDto
        var diaryDao = DiaryDao(this)

        updateBinding.etId.setText(dto.id.toString())
        updateBinding.photo.setImageResource(dto.photo)
        updateBinding.etUpdateDate.setHint(dto.date)
        updateBinding.etUpdateDate.setText(dto.date)
        updateBinding.etUpdateWeather.setHint(dto.weather)
        updateBinding.etUpdateWeather.setText(dto.weather)
        updateBinding.etUpdateScore.setHint(dto.score)
        updateBinding.etUpdateScore.setText(dto.score)
        updateBinding.etUpdateFeeling.setHint(dto.feeling)
        updateBinding.etUpdateFeeling.setText(dto.feeling)
        updateBinding.etUpdateContent.setHint(dto.diaryContent)
        updateBinding.etUpdateContent.setText(dto.diaryContent)

        updateBinding.btnUpdate.setOnClickListener{
            dto.date = updateBinding.etUpdateDate.text.toString()
            dto.weather=updateBinding.etUpdateWeather.text.toString()
            dto.score = updateBinding.etUpdateScore.text.toString()
            dto.feeling = updateBinding.etUpdateFeeling.text.toString()
            dto.diaryContent=updateBinding.etUpdateContent.text.toString()

            if(dto.date.length==0){
                Toast.makeText(this@UpdateActivity, "날짜는 필수 항목입니다.", Toast.LENGTH_SHORT).show()
            }else{
                if(diaryDao.updateDiary(dto)>0){
                    setResult(RESULT_OK)
                }else{
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }

        updateBinding.btnCanceled.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

}