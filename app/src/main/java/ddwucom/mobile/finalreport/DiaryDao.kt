package ddwucom.mobile.finalreport

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import com.google.android.material.color.utilities.Score

class DiaryDao(val context: Context) {

    val TAG = "DiaryDao"

    @SuppressLint("Range")
    fun getAllDays() : ArrayList<DiaryDto>{
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase
        var cursor = db.query(DiaryDBHelper.TABLE_NAME, null, null, null, null, null, null)
        val days = arrayListOf<DiaryDto>()

        with(cursor) {
            while (moveToNext()){
                val id = getInt (getColumnIndex(BaseColumns._ID))
                val date = getString (getColumnIndex(DiaryDBHelper.COL_DATE))
                val weather = getString (getColumnIndex(DiaryDBHelper.COL_WEATHER))
                val score = getString (getColumnIndex(DiaryDBHelper.COL_SCORE))
                val image = getInt (getColumnIndex(DiaryDBHelper.COL_PHOTO))
                val feeling = getString (getColumnIndex(DiaryDBHelper.COL_FEELING))
                val diaryContent = getString(getColumnIndex(DiaryDBHelper.COL_DCONTENT))

                val dto = DiaryDto(id,date,weather,score,image,feeling,diaryContent)
                days.add(dto)
            }
        }
        cursor.close()
        helper.close()

        return days
    }

    @SuppressLint("Range")
    fun getTrashAllDays() : ArrayList<DiaryDto>{
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase
        var cursor = db.query(DiaryDBHelper.TRASH_TABLE_NAME, null, null, null, null, null, null)
        val days = arrayListOf<DiaryDto>()

        with(cursor) {
            while (moveToNext()){
                val id = getInt (getColumnIndex(BaseColumns._ID))
                val date = getString (getColumnIndex(DiaryDBHelper.COL_DATE))
                val weather = getString (getColumnIndex(DiaryDBHelper.COL_WEATHER))
                val score = getString (getColumnIndex(DiaryDBHelper.COL_SCORE))
                val image = getInt (getColumnIndex(DiaryDBHelper.COL_PHOTO))
                val feeling = getString (getColumnIndex(DiaryDBHelper.COL_FEELING))
                val diaryContent = getString(getColumnIndex(DiaryDBHelper.COL_DCONTENT))

                val dto = DiaryDto(id,date,weather,score,image,feeling,diaryContent)
                days.add(dto)
            }
        }
        cursor.close()
        helper.close()

        return days
    }

    @SuppressLint("Range")
    fun getSearchDay( searchWords : String ) : ArrayList<DiaryDto>{
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase

        val cursor = db.query(DiaryDBHelper.TABLE_NAME, null, null, null, null, null, null)
        val days = arrayListOf<DiaryDto>()
        var checkCount = 0

        with(cursor) {
            while (moveToNext()){
                val id = getInt (getColumnIndex(BaseColumns._ID))

                val date = getString (getColumnIndex(DiaryDBHelper.COL_DATE))
                checkCount += checkEquals(date,searchWords)

                val weather = getString (getColumnIndex(DiaryDBHelper.COL_WEATHER))
                checkCount += checkEquals(weather,searchWords)

                val score = getString (getColumnIndex(DiaryDBHelper.COL_SCORE))
                checkCount += checkEquals(score,searchWords)

                val image = getInt (getColumnIndex(DiaryDBHelper.COL_PHOTO))

                val feeling = getString (getColumnIndex(DiaryDBHelper.COL_FEELING))
                checkCount += checkEquals(feeling,searchWords)

                val diaryContent = getString(getColumnIndex(DiaryDBHelper.COL_DCONTENT))
                checkCount += checkEquals(diaryContent,searchWords)

                if(checkCount>0){
                    val dto = DiaryDto(id,date,weather,score,image,feeling,diaryContent)
                    days.add(dto)
                }
                checkCount = 0
            }
        }
        cursor.close()
        helper.close()

        return days
    }

    @SuppressLint("Range")
    fun getSearchDate( searchWords : String ) : ArrayList<DiaryDto>{
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase

        val cursor = db.query(DiaryDBHelper.TABLE_NAME, null, null, null, null, null, null)
        val days = arrayListOf<DiaryDto>()
        var check = 0

        with(cursor) {
            while (moveToNext()){
                val id = getInt (getColumnIndex(BaseColumns._ID))
                val date = getString (getColumnIndex(DiaryDBHelper.COL_DATE))
                check = checkEquals(date,searchWords)

                if(check == 1 ){
                    val weather = getString (getColumnIndex(DiaryDBHelper.COL_WEATHER))
                    val score = getString (getColumnIndex(DiaryDBHelper.COL_SCORE))
                    val image = getInt (getColumnIndex(DiaryDBHelper.COL_PHOTO))
                    val feeling = getString (getColumnIndex(DiaryDBHelper.COL_FEELING))
                    val diaryContent = getString(getColumnIndex(DiaryDBHelper.COL_DCONTENT))
                    val dto = DiaryDto(id,date,weather,score,image,feeling,diaryContent)
                    days.add(dto)
                }
                check = 0
            }
        }
        cursor.close()
        helper.close()

        return days
    }

    @SuppressLint("Range")
    fun getSearchContent(searchWords : String ) : ArrayList<DiaryDto>{
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase

        val cursor = db.query(DiaryDBHelper.TABLE_NAME, null, null, null, null, null, null)
        val days = arrayListOf<DiaryDto>()
        var check = 0

        with(cursor) {
            while (moveToNext()){
                val id = getInt (getColumnIndex(BaseColumns._ID))

                val diaryContent = getString(getColumnIndex(DiaryDBHelper.COL_DCONTENT))
                check = checkEquals(diaryContent,searchWords)

                if(check == 1 ){
                    val date = getString (getColumnIndex(DiaryDBHelper.COL_DATE))
                    val weather = getString (getColumnIndex(DiaryDBHelper.COL_WEATHER))
                    val score = getString (getColumnIndex(DiaryDBHelper.COL_SCORE))
                    val image = getInt (getColumnIndex(DiaryDBHelper.COL_PHOTO))
                    val feeling = getString (getColumnIndex(DiaryDBHelper.COL_FEELING))
                    val dto = DiaryDto(id,date,weather,score,image,feeling,diaryContent)
                    days.add(dto)
                }
                check = 0
            }
        }
        cursor.close()
        helper.close()

        return days
    }

    fun checkEquals( objectW:String,  searchW:String) : Int {
        var i = 0
        lateinit var buf : String
        lateinit var bbuf : String
        for(i in 0..objectW.length-searchW.length){
            buf = objectW.substring(i)
            bbuf = buf.substring(0,searchW.length)
            if(bbuf.equals(searchW))
                return 1
        }
        return 0
    }

    /////////////
    @SuppressLint("Range")
    fun restoreDiary(id : Int) : Long {
        val helper = DiaryDBHelper(context)
        val db = helper.readableDatabase

        lateinit var dto : DiaryDto
        var count =0

        val columns = null
        val selection = "${BaseColumns._ID}=?"
        val selectionArgs = arrayOf("${id}")
        val cursor = db.query(
            "${DiaryDBHelper.TRASH_TABLE_NAME}", columns, selection, selectionArgs, null, null, null, null
        )

        with(cursor){
            while (moveToNext()){
                val date = getString (getColumnIndex(DiaryDBHelper.COL_DATE))
                val weather = getString (getColumnIndex(DiaryDBHelper.COL_WEATHER))
                val score = getString (getColumnIndex(DiaryDBHelper.COL_SCORE))
                val image = getInt (getColumnIndex(DiaryDBHelper.COL_PHOTO))
                val feeling = getString (getColumnIndex(DiaryDBHelper.COL_FEELING))
                val diaryContent = getString(getColumnIndex(DiaryDBHelper.COL_DCONTENT))

                dto = DiaryDto(id,date,weather,score,image,feeling,diaryContent)
                count++
                if(count==1)
                    break
            }
        }

        val result = addDiary(dto)
        realDelete(id)
        return result
    }

    fun addDiary(newDto: DiaryDto) : Long {
        val helper = DiaryDBHelper(context)
        val db = helper.writableDatabase

        val newValues = ContentValues()
        newValues.put(DiaryDBHelper.COL_DATE, newDto.date)
        newValues.put(DiaryDBHelper.COL_WEATHER, newDto.weather)
        newValues.put(DiaryDBHelper.COL_SCORE, newDto.score)
        newValues.put(DiaryDBHelper.COL_PHOTO, newDto.photo)
        newValues.put(DiaryDBHelper.COL_FEELING, newDto.feeling)
        newValues.put(DiaryDBHelper.COL_DCONTENT, newDto.diaryContent)

        val result = db.insert(DiaryDBHelper.TABLE_NAME, null, newValues)

        helper.close()

        return result
    }

    fun updateDiary(dto:DiaryDto):Int{
        val helper=DiaryDBHelper(context)
        val db = helper.writableDatabase
        val updateValue = ContentValues()
        updateValue.put(DiaryDBHelper.COL_DATE, dto.date)
        updateValue.put(DiaryDBHelper.COL_WEATHER,dto.weather)
        updateValue.put(DiaryDBHelper.COL_SCORE,dto.score)
        updateValue.put(DiaryDBHelper.COL_FEELING, dto.feeling)
        updateValue.put(DiaryDBHelper.COL_DCONTENT, dto.diaryContent)

        val whereCaluse = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val result = db.update(DiaryDBHelper.TABLE_NAME,updateValue, whereCaluse, whereArgs)

        helper.close()
        return result
    }

    fun realDelete(id:Int) : Int{
        val helper = DiaryDBHelper(context)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(DiaryDBHelper.TRASH_TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }

    fun deleteDiary(id:Int) : Int{
        var check = addTrash(id)

        if(check!=null){
            val helper = DiaryDBHelper(context)
            val db = helper.writableDatabase

            val whereClause = "${BaseColumns._ID}=?"
            val whereArgs = arrayOf(id.toString())

            val result = db.delete(DiaryDBHelper.TABLE_NAME, whereClause, whereArgs)

            helper.close()
            return result

        }
        return 0
    }

    @SuppressLint("Range")
    fun addTrash(searchId: Int) : Long {
        val helper = DiaryDBHelper(context)
        var db = helper.readableDatabase

        val cursor = db.query(DiaryDBHelper.TABLE_NAME, null, null, null, null, null, null)
        val days = arrayListOf<DiaryDto>()

        lateinit var date:String
        lateinit var weather:String
        lateinit var score: String
        var image: Int? = null
        lateinit var feeling:String
        lateinit var diaryContent:String

        with(cursor) {
            while (moveToNext()){
                var id = getInt (getColumnIndex(BaseColumns._ID))
                if(searchId==(id)){
                    date = getString (getColumnIndex(DiaryDBHelper.COL_DATE))
                    weather = getString (getColumnIndex(DiaryDBHelper.COL_WEATHER))
                    score = getString (getColumnIndex(DiaryDBHelper.COL_SCORE))
                    image = getInt (getColumnIndex(DiaryDBHelper.COL_PHOTO))
                    feeling = getString (getColumnIndex(DiaryDBHelper.COL_FEELING))
                    diaryContent = getString(getColumnIndex(DiaryDBHelper.COL_DCONTENT))

                    db = helper.writableDatabase

                    val newValues = ContentValues()
                    newValues.put(DiaryDBHelper.COL_DATE, date)
                    newValues.put(DiaryDBHelper.COL_WEATHER, weather)
                    newValues.put(DiaryDBHelper.COL_SCORE, score)
                    newValues.put(DiaryDBHelper.COL_PHOTO, image)
                    newValues.put(DiaryDBHelper.COL_FEELING, feeling)
                    newValues.put(DiaryDBHelper.COL_DCONTENT, diaryContent)

                    val result = db.insert(DiaryDBHelper.TRASH_TABLE_NAME, null, newValues)

                    cursor.close()
                    helper.close()
                    return result
                }
            }
        }
        cursor.close()
        helper.close()

        return 0
    }


}