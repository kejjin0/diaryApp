package ddwucom.mobile.finalreport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DiaryDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "DiaryHelper"

    companion object{
        const val DB_NAME = "diary_db"
        const val TABLE_NAME = "diary_table"
        const val TRASH_TABLE_NAME = "trash_table"

        const val COL_PHOTO = "photo"
        const val COL_DATE = "date"
        const val COL_WEATHER = "weather"
        const val COL_SCORE = "score"
        const val COL_FEELING= "feeling"
        const val COL_DCONTENT="diaryContent"
    }

    override fun onCreate(db: SQLiteDatabase?){
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            "$COL_DATE TEXT ,$COL_WEATHER TEXT, $COL_SCORE TEXT, $COL_PHOTO INTEGER, $COL_FEELING TEXT, $COL_DCONTENT TEXT )"
        db?.execSQL(CREATE_TABLE)

        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '2023.06.17' , 'sunny','5', ${R.drawable.food2_mara}, 'happy', 'maratang 먹고 싶었던 마라탕을 먹어 행복한 하루였다.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '2023.06.18' , 'sunny', '4', ${R.drawable.food2_jeon}, 'so so', 'pa jeon 파전도 맛잇었지만 김치전을 먹지 못해 아쉬웠다. 그래도 날씨는 맑아 좋았다')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '2023.06.19' , 'sunny', '5', ${R.drawable.food1_fish}, '좋음', '오랜만에 야식을 먹었다. 야식으로 행복하게 마무리한 하루였다.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '2023.06.20' , 'cloudy', '5', ${R.drawable.image100}, '행복', '방학이 되면 학교가 그리울 것 같다. 이번 방학은 알차게 보내기로 결심했다.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '2023.06.21' , 'rainy', '5', ${R.drawable.school2}, 'sad', '비가 오니 기분이 다운되었다. 빨리 날씨가 맑아졌으면 좋겠다.')")

        ///////
        val CREATE_TRASH_TABLE = "CREATE TABLE $TRASH_TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "$COL_DATE TEXT ,$COL_WEATHER TEXT, $COL_SCORE TEXT, $COL_PHOTO INTEGER, $COL_FEELING TEXT, $COL_DCONTENT TEXT )"

        db?.execSQL(CREATE_TRASH_TABLE)
        db?.execSQL("INSERT INTO $TRASH_TABLE_NAME VALUES (NULL, 'trash_sample' , 'sunny','5', ${R.drawable.food2_mara}, 'happy', 'trash trash')")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)

        val DROP_TABLE2 = "DROP TABLE IF EXISTS $TRASH_TABLE_NAME"
        db?.execSQL(DROP_TABLE2)
        onCreate(db)

    }

}