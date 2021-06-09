package com.alamkanak.weekview.sample.models

import com.alamkanak.weekview.sample.data.model.CalendarEntity

data class Events(val username:String,
                  val engineer_id:String,
                  val id: String,
                  val color_string: String,
                  val title:String,
                  val location: String,
                  val hasInterval: Boolean,
                  val interval: Int,
                  val startDay: Int,
                  val endDay: Int,
                  val startTime: Int,
                  val endTime: Int,
                  val startMillis: Long,
                  val endMillis: Long,
                  val jobEventType: Int,
                  val startDate: String,
                  val endDate: String,
                  val event_status:String,
/*
                  val fromDate: String,
                  val ToDate: String,
                  val userId: String,
                  val description: String,*/
)