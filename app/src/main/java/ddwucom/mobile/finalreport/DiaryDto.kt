package ddwucom.mobile.finalreport

import java.io.Serializable

data class DiaryDto(var id: Int, var date:String, var weather:String, var score:String, var photo: Int ,var feeling:String, var diaryContent:String) : Serializable {
}