package ddwucom.mobile.finalreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwucom.mobile.finalreport.databinding.ActivityIntroduceBinding

class IntroduceActivity : AppCompatActivity() {

    lateinit var introduceBinding : ActivityIntroduceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introduceBinding= ActivityIntroduceBinding.inflate(layoutInflater)
        setContentView(introduceBinding.root)

        introduceBinding.introduceImage.setImageResource(R.drawable.introduce_image)
        introduceBinding.name.setText("김은진")
        introduceBinding.studentId.setText("컴퓨터학과 20210770")
        introduceBinding.subject.setText("모바일 소프트웨어 02분반")
        introduceBinding.etcText.setText("<추가 기능>\n"+ "1.DAO 구현\n" +
                "(DB의 기능은 DiarayDao를 사용하도록 구현하였습니다.)\n\n" +
        "2.검색 기능\n" + "(검색을 하면 기록을 해둔 상세 내용(하루의 기분,다이어리 내용)까지 함께 볼 수 있습니다. + 전체에서 검색쁜만 아니라 메뉴 옵션을 선택하여 날짜 또는 내용의 검색도 가능합니다.)\n\n"+
                "3.위젯 기능\n" + "(검색 가능과 다이어리 추가에서 캘린더 표시를 누르고 날짜를 입력하면 EditText에 입력이 됩니다.)\n\n" +
                "4.리스트뷰 항목에 이미지 사용\n\n"+
                "5.휴지통 기능(삭제,복원)\n" + "(삭제된 다이어리들을 볼 수 있고 완전히 삭제, 복원이 가능합니다.)\n\n"
        +"6.다이어리로 가기 버튼\n")

        introduceBinding.btnBack.setOnClickListener{
            finish()
        }

    }
}