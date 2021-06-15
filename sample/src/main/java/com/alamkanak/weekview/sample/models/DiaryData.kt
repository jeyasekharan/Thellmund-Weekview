package com.alamkanak.weekview.sample.models

class DiaryData {

    companion object {
        var timeHash = HashMap<String, String>()

        /* This is just a reference to our time to compare the postions of the layout*/
        val arrDateTime = arrayListOf<String>(
            "06:00:00.0",
            "07:00:00.0",
            "08:00:00.0",
            "09:00:00.0",
            "10:00:00.0",
            "11:00:00.0",
            "12:00:00.0",
            "13:00:00.0",
            "14:00:00.0",
            "15:00:00.0",
            "16:00:00.0",
            "17:00:00.0",
            "18:00:00.0",
            "19:00:00.0",
            "20:00:00.0"
        )

        fun setTimeData() {
            arrDateTime.forEachIndexed { index, s ->
                timeHash[s] = index.toString()
            }
        }

        /* Get only 3 engineers events  */
        fun splitInto5Keys(arList: Map<String, List<Events>>): List<List<String>> {
            val arrayKeys = arList.keys.chunked(5)
            return arrayKeys
        }

        fun getKeyIndex(listKeys : List<List<String>>, index: Int) : List<String>? {
            return if (listKeys.isNotEmpty()) {
                listKeys[index]
            } else {
                null
            }
        }

    }
}