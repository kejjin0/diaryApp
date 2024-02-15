package ddwucom.mobile.finalreport

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ddwucom.mobile.finalreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // 과제명 : 다이어리 앱
    // 분반 : 02분반
    // 학번 : 20210770   성명 : 김은진
    // 제출일 : 2023년 06월 23일

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200
    val REQ_RESTORE = 300

    lateinit var binding : ActivityMainBinding
    lateinit var adapter: DiaryAdapter
    lateinit var days : ArrayList<DiaryDto>
    lateinit var  diaryDao : DiaryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rv.layoutManager= LinearLayoutManager(this).apply{
            orientation = LinearLayoutManager.VERTICAL
        }

        diaryDao = DiaryDao(this)

        days = diaryDao.getAllDays()
        adapter = DiaryAdapter(days)
        binding.rv.adapter=adapter

        val onClickListener = object : DiaryAdapter.OnItemClickListener{
            override fun onItemClick(view:View, position:Int){
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto",days.get(position))
                startActivityForResult(intent, REQ_UPDATE)
            }
        }
        adapter.setOnItemClickListener(onClickListener)

        val onLongClickListener = object : DiaryAdapter.OnItemLongClickListener{
            override fun onItemLongClick(view: View?, position: Int) {
                AlertDialog.Builder(this@MainActivity).run{
                    setTitle("Diary 삭제")
                    setMessage("${days.get(position).date}의 diary를 삭제하시겠습니까?")
                    setPositiveButton("삭제", object :DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            if(diaryDao.deleteDiary(days.get(position).id)>0){
                                refreshList(RESULT_OK)
                            }
                        }
                    })
                    setCancelable(false)
                    setNegativeButton("취소",null)
                    show()
                }.setCanceledOnTouchOutside(false)
            }
        }
        adapter.setOnItemLongClickListener(onLongClickListener)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addDiary -> {
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                startActivityForResult(intent, REQ_ADD)
            }

            R.id.clickIntroduce -> {
                val intent = Intent(this@MainActivity, IntroduceActivity::class.java)
                startActivity(intent)
            }

            R.id.clickFinish -> {
                AlertDialog.Builder(this).run{
                    setTitle("Diary 종료")
                    setMessage("정말로 종료하시겠습니까?")
                    setPositiveButton("종료", object :DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            finish()
                        }
                    })
                    setCancelable(false)
                    setNegativeButton("취소",null)
                    show()
                }.setCanceledOnTouchOutside(false)
            }

            R.id.clickSearch -> {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }

            R.id.clickTrash -> {
                val intent = Intent(this@MainActivity, TrashActivity::class.java)
                startActivityForResult(intent,REQ_RESTORE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode){
            REQ_ADD -> {
                refreshList(resultCode)
            }

            REQ_UPDATE -> {
                refreshList(resultCode)
            }

            REQ_RESTORE -> {
                refreshList(resultCode)
            }
        }
    }


    private fun refreshList(resultCode: Int){
        if(resultCode == RESULT_OK){
            days.clear()
            days.addAll(diaryDao.getAllDays())
            adapter.notifyDataSetChanged()
        }
    }

}