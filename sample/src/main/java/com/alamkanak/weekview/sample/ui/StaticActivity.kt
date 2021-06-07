package com.alamkanak.weekview.sample.ui

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
    lateinit var engineerNames: Array<String?>

    private val viewModel by genericViewModel()

    private val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

    private val adapter: StaticActivityWeekViewAdapter by lazy {
        StaticActivityWeekViewAdapter(
            loadMoreHandler = this::onLoadMore,
            rangeChangeHandler = this::onRangeChanged
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Get data and flatten
        arrayList = EventUtils.getData()
        engineerNames = EventUtils.setEngineerColumnNames()
        alFlatEvents = arrayList!!.flatten()


        // val time = LocalDateTime.of(year= "2021")
        // Map to Calendar Entity

        //alFlatEvents!!.map { CalendarEntity.Event(it.id.toLong(), it.title, "2021-05-03T20:00", it.endTime,
        //it.location, Color.parseColor("#00ff00"), false, false) }



        binding.toolbarContainer.toolbar.setupWithWeekView(binding.weekView)
        binding.weekView.adapter = adapter

        //binding.weekView.setEngineerNames(arrayOf("name1 ","name 2", "name 3", "name 4", "name 5"))
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
            Log.e("entittieeeee  ", " entitess    $viewState.entities")
            adapter.submitList(viewState.entities)
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
