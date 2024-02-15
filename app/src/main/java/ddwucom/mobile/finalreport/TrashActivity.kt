package ddwucom.mobile.finalreport

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ddwucom.mobile.finalreport.databinding.ActivityTrashBinding

class TrashActivity : AppCompatActivity() {

    lateinit var trashBinding : ActivityTrashBinding
    lateinit var adapter : DiaryAdapter
    lateinit var days : ArrayList<DiaryDto>
    lateinit var diaryDao: DiaryDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trashBinding = ActivityTrashBinding.inflate(layoutInflater)
        setContentView(trashBinding.root)

        trashBinding.rv.layoutManager=LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        diaryDao = DiaryDao(this)

        days = diaryDao.getTrashAllDays()
        adapter = DiaryAdapter(days)
        trashBinding.rv.adapter=adapter
        var result = 0

        val onClickListener = object : DiaryAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position:Int){
                AlertDialog.Builder(this@TrashActivity).run{
                    setTitle("휴지통에서 삭제")
                    setMessage("정말로 삭제하시겠습니까?")
                    setPositiveButton("삭제", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            if(diaryDao.realDelete(days.get(position).id)>0){
                                refreshList(RESULT_OK)
                            }
                        }
                    } )
                    setCancelable(false)
                    setNegativeButton("취소",null)
                    show()
                }.setCanceledOnTouchOutside(false)
            }
        }
        adapter.setOnItemClickListener(onClickListener)

        val onLongClickListener = object : DiaryAdapter.OnItemLongClickListener{
            override fun onItemLongClick(view: View?, position: Int) {
                AlertDialog.Builder(this@TrashActivity).run{
                    setTitle("diary 복원")
                    setMessage("${days.get(position).date}의 diary를 복원하시겠습니까?")
                    setPositiveButton("복원", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            if(diaryDao.restoreDiary(days.get(position).id)>0){
                                result++
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


        trashBinding.btnBackT.setOnClickListener{
            if(result > 0){
                setResult(RESULT_OK)
//                Toast.makeText(this,"${result}  11", Toast.LENGTH_SHORT).show()

            }else{
                setResult(RESULT_CANCELED)
            }
            finish()
        }
    }

    private fun refreshList(resultCode: Int){
        if(resultCode == RESULT_OK){
            days.clear()
            days.addAll(diaryDao.getTrashAllDays())
            adapter.notifyDataSetChanged()
        }
    }

}