package com.alamkanak.weekview.sample.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alamkanak.weekview.WeekViewEntity
import com.alamkanak.weekview.jsr310.WeekViewPagingAdapterJsr310
import com.alamkanak.weekview.jsr310.firstVisibleDateAsLocalDate
import com.alamkanak.weekview.jsr310.scrollToDate
import com.alamkanak.weekview.sample.R
import com.alamkanak.weekview.sample.data.model.CalendarEntity
import com.alamkanak.weekview.sample.data.model.toWeekViewEntity
import com.alamkanak.weekview.sample.databinding.ActivityStaticBinding
import com.alamkanak.weekview.sample.models.EventUtils
import com.alamkanak.weekview.sample.models.Events
import com.alamkanak.weekview.sample.util.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class StaticActivity : AppCompatActivity() {

    private val binding: ActivityStaticBinding by lazy {
        ActivityStaticBinding.inflate(layoutInflater)
    }
    var arrayList: ArrayList<List<Events>>? = null
    var alFlatEvents: List<Events>? = null

    var fiveSingleArray: ArrayList<Events>? = ArrayList<Events>()
    var fiveSingleArrayCalendarEntity: List<CalendarEntity.Event>? = ArrayList<CalendarEntity.Event>()

    lateinit var engineerNames: Array<String?>

    private val viewModel by genericViewModel()

    private val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    val arrToday = arrayOf("2021-06-09","2021-06-10","2021-06-11","2021-06-12","2021-06-13")

    private val adapter: StaticActivityWeekViewAdapter by lazy {
        StaticActivityWeekViewAdapter(
            loadMoreHandler = this::onLoadMore,
            rangeChangeHandler = this::onRangeChanged
        )
    }

    private fun processEventTime(index:Int, datetime: String): LocalDateTime {
        //time = 2021-05-29 08:00:00.0//

        val strArr = datetime.split(" ")
        val time = strArr[1].substring(0,5)

        val localString = arrToday[index]+"T"+strArr[1]
        Log.e("local String  ", "  local String $localString")

        return LocalDateTime.parse(localString)
    }

    private fun getTodayDate() {

        val date = Calendar.getInstance().time
        Log.e(" today date  ", "  today date $date")

        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val s: String = dateFormatter.format(date)

    }

    fun getNextDate(curDate: String?): String? {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(curDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        return format.format(calendar.time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Get data and flatten
        arrayList = EventUtils.getData()
        engineerNames = EventUtils.setEngineerColumnNames()
        alFlatEvents = arrayList!!.flatten()


        //val time = LocalDateTime.of(year= "2021")
        // Map to Calendar Entity

        for (event in arrayList!!) {
            fiveSingleArray?.add(event[0])
        }


        fiveSingleArrayCalendarEntity = fiveSingleArray!!.mapIndexed { index, it ->  CalendarEntity.Event(it.id.toLong() ?: 123, it.title,
            startTime =  processEventTime(index, it.startDate),
            endTime = processEventTime(index, it.endDate), it.location ?: "123", Color.parseColor("#bbbbbd"), false, false) }


       // alFlatEvents!!.map { CalendarEntity.Event(it.id.toLong(), it.title, startTime =  "2021-05-03T20:00", it.endTime,
       // it.location, 12345, false, false) }

        binding.toolbarContainer.toolbar.setupWithWeekView(binding.weekView)
        binding.weekView.adapter = adapter

        binding.weekView.setEngineerNames(arrayOf("name1 ","name 2", "name 3", "name 4", "name 5"))
        engineerNames.let {
            binding.weekView.setEngineerNames(it)
        }
        binding.weekView.numberOfVisibleDays = 5


        binding.leftNavigationButton.setOnClickListener {
            val firstDate = binding.weekView.firstVisibleDateAsLocalDate
            val newFirstDate = firstDate.minusDays(7)
            binding.weekView.scrollToDate(newFirstDate)
        }

        binding.rightNavigationButton.setOnClickListener {
            val firstDate = binding.weekView.firstVisibleDateAsLocalDate
            val newFirstDate = firstDate.plusDays(7)
            binding.weekView.scrollToDate(newFirstDate)
        }

        viewModel.viewState.observe(this) { viewState ->
            Log.e("entittieeeee  ", " entitess    ${fiveSingleArrayCalendarEntity!!}")
            adapter.submitList(fiveSingleArrayCalendarEntity!!)
          //  adapter.submitList(viewState.entities)

        }
    }


    private fun onLoadMore(yearMonths: List<YearMonth>) {
        viewModel.fetchEvents(yearMonths)
    }

    private fun onRangeChanged(startDate: LocalDate, endDate: LocalDate) {
        binding.dateRangeTextView.text = buildDateRangeText(startDate, endDate)
    }

    private fun buildDateRangeText(startDate: LocalDate, endDate: LocalDate): String {
        val formattedFirstDay = dateFormatter.format(startDate)
        val formattedLastDay = dateFormatter.format(endDate)
        return getString(R.string.date_infos, formattedFirstDay, formattedLastDay)
    }
}

private class StaticActivityWeekViewAdapter(
    private val rangeChangeHandler: (LocalDate, LocalDate) -> Unit,
    private val loadMoreHandler: (List<YearMonth>) -> Unit
) : WeekViewPagingAdapterJsr310<CalendarEntity>() {

    override fun onCreateEntity(item: CalendarEntity): WeekViewEntity = item.toWeekViewEntity()

    override fun onEventClick(data: CalendarEntity) {
        if (data is CalendarEntity.Event) {
            context.showToast("Clicked ${data.title}")
        }
    }

    override fun onEmptyViewClick(time: LocalDateTime) {
        context.showToast("Empty view clicked at ${defaultDateTimeFormatter.format(time)}")
    }

    override fun onEventLongClick(data: CalendarEntity) {
        if (data is CalendarEntity.Event) {
            context.showToast("Long-clicked ${data.title}")
        }
    }

    override fun onEmptyViewLongClick(time: LocalDateTime) {
        context.showToast("Empty view long-clicked at ${defaultDateTimeFormatter.format(time)}")
    }

    override fun onLoadMore(startDate: LocalDate, endDate: LocalDate) {
        loadMoreHandler(yearMonthsBetween(startDate, endDate))
    }

    override fun onRangeChanged(firstVisibleDate: LocalDate, lastVisibleDate: LocalDate) {
        rangeChangeHandler(firstVisibleDate, lastVisibleDate)
    }
}
