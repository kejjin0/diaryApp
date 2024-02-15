package ddwucom.mobile.finalreport

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ddwucom.mobile.finalreport.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    var TAG = "SearchActivity"

    var selection = 0
    lateinit var searchBinding : ActivitySearchBinding
    lateinit var adapter : SearchAdapter
    lateinit var diaryDao: DiaryDao
    lateinit var days:ArrayList<DiaryDto>
    lateinit var searchDays:ArrayList<DiaryDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        searchBinding.searchRv.layoutManager = LinearLayoutManager(this@SearchActivity).apply{
            orientation=LinearLayoutManager.VERTICAL
        }

        diaryDao = DiaryDao(this)

        days = diaryDao.getAllDays()
        adapter = SearchAdapter(days)
        searchBinding.searchRv.adapter = adapter

        searchBinding.btnSearch.setOnClickListener{
            var search = searchBinding.etSearch.text.toString()

            if(selection==0){
                searchDays = diaryDao.getSearchDay(search)
            }else if(selection==1){
                searchDays = diaryDao.getSearchDate(search)
            }else{
                searchDays = diaryDao.getSearchContent(search)
            }

            if(searchDays.isEmpty()){
                if(selection==0){
                    Toast.makeText(this@SearchActivity,"'${search}' 단어가 들어간 다이어리는 존재하지 않습니다.",Toast.LENGTH_SHORT).show()
                }else if(selection==1){
                    Toast.makeText(this@SearchActivity,"'${search}' 날짜의 다이어리는 존재하지 않습니다.",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@SearchActivity,"'${search}' 내용의 다이어리는 존재하지 않습니다.",Toast.LENGTH_SHORT).show()

                }

                adapter = SearchAdapter(days)
                searchBinding.searchRv.adapter=adapter
            }else{
                adapter = SearchAdapter(searchDays)
                searchBinding.searchRv.adapter=adapter
            }
        }

        searchBinding.btnDate.setOnClickListener{
            var dateD :String? = null

            DatePickerDialog(this, object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    if(day<10){
                        dateD = "${year}.0${month+1}.0${day}"
                    }else{
                        dateD = "${year}.0${month+1}.${day}"
                    }
                    if(dateD!=null){
                        searchBinding.etSearch.setText(dateD)
                    }
                }
            }, 2023,5,24).show()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_option, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when(selection){
            0 -> menu?.findItem(R.id.allSearch)?.setChecked(true)
            1 -> menu?.findItem(R.id.dateSearch)?.setChecked(true)
            2 -> menu?.findItem(R.id.contentSearch)?.setChecked(true)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.allSearch -> selection = 0
            R.id.dateSearch -> selection = 1
            R.id.contentSearch -> selection = 2
        }
        return super.onOptionsItemSelected(item)
    }

}