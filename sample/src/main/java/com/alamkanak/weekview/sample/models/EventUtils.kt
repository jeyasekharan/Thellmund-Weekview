package com.alamkanak.weekview.sample.models

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object EventUtils {

    // Static index to store events for a particular date
    var eventGroupedOnEngineerIDs: Map<String, List<Events>>? = null

    // Engineer keys grouped into 5 arrays
    lateinit var arrFiveGroupsArrayKeys: List<List<String>>


    lateinit var keysEngineer: List<String>

    // Static index for grouping list of engineers
    var usersListIndex = 0

    fun getData(): ArrayList<List<Events>>? {

        var arValues: ArrayList<List<Events>>? = null

        /* Convert string data to Class  */
        val listType: Type = object : TypeToken<List<Events?>?>() {}.type
        val arList = Gson().fromJson<List<Events>>(EventData2.events, listType)

        val totalEvents = arList.size

        /* Grouping events by engineers id  */
        eventGroupedOnEngineerIDs = arList.groupBy { it.engineer_id }

        /* Set adapter */
        eventGroupedOnEngineerIDs?.let {
            arValues = setGridAdapter(it)
        }

        arrFiveGroupsArrayKeys = DiaryData.splitInto5Keys(eventGroupedOnEngineerIDs!!)

        keysEngineer = DiaryData.getKeyIndex(arrFiveGroupsArrayKeys, 0)

        return arValues
    }


    fun getDataForSingleDate(date: String): ArrayList<List<Events>>? {

        var arValues: ArrayList<List<Events>>? = null

        /* Convert string data to Class  */
        val listType: Type = object : TypeToken<List<Events?>?>() {}.type
        val arList = Gson().fromJson<List<Events>>(EventData2.events, listType)


        val todayEvents = arList.filter {
            it.startDate.substring(0, 10) == date
        }


        /* Grouping events by engineers id  */
        eventGroupedOnEngineerIDs = todayEvents.groupBy { it.engineer_id }

        /* Set adapter Convert map into arraylist of 5 grouped events */
        eventGroupedOnEngineerIDs?.let {
            arValues = setGridAdapter(it)
            arrFiveGroupsArrayKeys = DiaryData.splitInto5Keys(eventGroupedOnEngineerIDs!!)
            print("the chunk keys $arrFiveGroupsArrayKeys")
        }


        println("eventGroupedOnEngineerIDs  today $eventGroupedOnEngineerIDs")

        //keysEngineer = DiaryData.getKeyIndex(arrFiveGroupsArrayKeys, 0)

        return arValues
    }

    fun setEngineerColumnNames(date: String): Array<String?> {

        var arValues: ArrayList<List<Events>>? = null

        /* Convert string data to Class  */
        val listType: Type = object : TypeToken<List<Events?>?>() {}.type
        val arList = Gson().fromJson<List<Events>>(EventData.data, listType)

        val totalEvents = arList.size


        val todayEvents = arList.filter {
            it.startDate.substring(0, 10) == date
        }

        /* Grouping events by engineers id  */
        eventGroupedOnEngineerIDs = todayEvents.groupBy { it.engineer_id }


        /* Set adapter Convert map into arraylist of 5 grouped events */
        eventGroupedOnEngineerIDs?.let {
            arValues = setGridAdapter(it)
            arrFiveGroupsArrayKeys = DiaryData.splitInto5Keys(it)
            if(arrFiveGroupsArrayKeys.isNotEmpty()) {
                EventUtils.keysEngineer = DiaryData.getKeyIndex(arrFiveGroupsArrayKeys, usersListIndex)
            }
        }


        /* Engineer keys are grouped into five each*/
        UserDetails.getModelFromJson()

        var usersNames = arrayOfNulls<String>(10) // returns Array<String?>

        keysEngineer.forEachIndexed { index, key ->
            val user = UserDetails.arEngineersObjectList?.first { it.user_id.toString() == key }
            usersNames[index] = user?.username!!
        }

        print("Engineer names from method $usersNames")

        return usersNames
    }

    fun getEngineerColumnNames(date: String): ArrayList<String> {

        arrFiveGroupsArrayKeys = DiaryData.splitInto5Keys(eventGroupedOnEngineerIDs!!)

        EventUtils.keysEngineer = DiaryData.getKeyIndex(arrFiveGroupsArrayKeys, usersListIndex)

        println(" the engineerNames $keysEngineer")

        /* Engineer keys are grouped into five each*/
        UserDetails.getModelFromJson()

        val usersNames :ArrayList<String> = arrayListOf<String>()	 // returns Array<String?>

        keysEngineer.forEachIndexed { index, key ->
            val user = UserDetails.arEngineersObjectList?.first { it.user_id.toString() == key }
            user?.username?.let {
                usersNames.add(user?.username!!)
            }
           // usersNames[index] = user?.username!!
        }
        println(" the engineerNames ")
        for (user in usersNames) {
            println("  $user")
        }
        println(" the engineerNames ")

        return usersNames
    }



    // Get events from engineers Index
    fun setGridAdapter(arList: Map<String, List<Events>>): ArrayList<List<Events>> {

        var arList2: Map.Entry<String, List<Events>>
        var nameIndex = 0

        /* This code will fetch 3 engineers name and place it in heading
        arList.forEach lit@{

            if (nameIndex == 5) {
                return@lit
            }
            arList2 = it


            when (nameIndex) {
                0 -> {
                    // tv_name_1.text = it.value[0].username
                }

                1 -> {
                    // tv_name_1.text = it.value[0].username
                }

                2 -> {
                    //tv_name_1.text = it.value[0].username
                }

                3 -> {

                }
            }
            nameIndex++
        }*/

        /* Take list of 5 engineers id from data */

        arrFiveGroupsArrayKeys = DiaryData.splitInto5Keys(arList)

        /* Engineer keys are grouped into five each*/
        if (arrFiveGroupsArrayKeys.isNotEmpty()) {
            keysEngineer = DiaryData.getKeyIndex(arrFiveGroupsArrayKeys, usersListIndex)
        }

        val eventsData = getFiveEvents(arList, keysEngineer)

        return eventsData
    }


    /* To get no of events depending upon keys */
    /* arList - this is grouped events based on engineer keys */

    private fun getFiveEvents(
        arList: Map<String, List<Events>>,
        keysEngineer: List<String>
    ): ArrayList<List<Events>> {
        val eventsBasedOnEngineers: ArrayList<List<Events>> = ArrayList()

        if (keysEngineer.isNotEmpty() && arList.isNotEmpty()) {
            for (i in keysEngineer.indices) {
                eventsBasedOnEngineers.add(arList[keysEngineer[i]]!!)
            }
        }
        return eventsBasedOnEngineers
    }


    /* Scroll users left and right */
    fun increaseEngineerIndex(): ArrayList<List<Events>>? {
        var eventsData: ArrayList<List<Events>>? = null
        Log.e("this checking    ", " increases  ${usersListIndex} ")
        Log.e("this checking    ", " increases  ${usersListIndex>0} ")
        if (usersListIndex > 0) {
            usersListIndex -= 1

            keysEngineer = DiaryData.getKeyIndex(arrFiveGroupsArrayKeys, usersListIndex)
            Log.e("this checking    ", " increases   $usersListIndex")

            //setEngineerColumnNames(curr)

            println("eventGroupedOnEngineerIDs inccc   $eventGroupedOnEngineerIDs  ${eventGroupedOnEngineerIDs?.size}")

            eventGroupedOnEngineerIDs?.let {
                eventsData = getFiveEvents(it, keysEngineer)
                println("increases   $eventsData")

            }
        }
        return eventsData
    }


    fun decreaseEngineerIndex(): ArrayList<List<Events>>? {
        var eventsData: ArrayList<List<Events>>? = null
        Log.e("this checking    ", " decreases  ${usersListIndex < arrFiveGroupsArrayKeys.size - 1} ")
        Log.e("this checking    ", " decreases  ${usersListIndex}   size  ${arrFiveGroupsArrayKeys.size} ")

        if (usersListIndex < arrFiveGroupsArrayKeys.size - 1) {
            usersListIndex += 1
            Log.e("this checking    ", " decreases  $usersListIndex ")

            keysEngineer = DiaryData.getKeyIndex(arrFiveGroupsArrayKeys, usersListIndex)

           // setEngineerColumnNames()
            println("eventGroupedOnEngineerIDs decccc   $eventGroupedOnEngineerIDs   ${eventGroupedOnEngineerIDs?.size}")

            eventGroupedOnEngineerIDs?.let {
                eventsData = getFiveEvents(it, keysEngineer)
            }
        }
        return eventsData

    }
}