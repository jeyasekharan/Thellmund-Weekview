package com.alamkanak.weekview.sample.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Users(
    val boxchecked: Boolean,
    val days_future: Int,
    val days_past: Int,
    val diary_position: Int,
    val diarycolor: Int,
    val diarycolor_hex: String,
    val dragHandle: Boolean,
    val firstname: String,
    val selected: Boolean,
    val surname: String,
    val user_id: Int,
    val usergroup_id: String,
    val usergroup_uuid: String,
    val username: String,
    val uuid: String
)

class UserDetails {

    companion object {

        val users = """
   [
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":0,
      "diarycolor":-1245204,
      "diarycolor_hex":"#ecffec",
      "dragHandle":true,
      "firstname":"Daniel",
      "selected":true,
      "surname":"Clements",
      "user_id":2,
      "usergroup_id":"5",
      "usergroup_uuid":"cca9b235-bd13-47cb-907f-19cacc4efa01",
      "username":"DanielC",
      "uuid":"0fd8605f-f180-4406-b823-a1d2a5ffb85d"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":1,
      "diarycolor":-8196819,
      "diarycolor_hex":"#82ed2d",
      "dragHandle":true,
      "firstname":"Daniel",
      "selected":true,
      "surname":"Harris",
      "user_id":5,
      "usergroup_id":"3",
      "usergroup_uuid":"7966588e-4ba7-4117-9e36-521a4edd41f2",
      "username":"DanielH",
      "uuid":"db49398a-f072-4969-98a3-500fdd1729b8"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":2,
      "diarycolor":-13921305,
      "diarycolor_hex":"#2b93e7",
      "dragHandle":true,
      "firstname":"Gareth",
      "selected":true,
      "surname":"Hill",
      "user_id":1,
      "usergroup_id":"3",
      "usergroup_uuid":"7966588e-4ba7-4117-9e36-521a4edd41f2",
      "username":"Gareth",
      "uuid":"9030c48d-8caf-4618-9e78-af48614a3853"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":3,
      "diarycolor":-2194634,
      "diarycolor_hex":"#de8336",
      "dragHandle":true,
      "firstname":"James",
      "selected":true,
      "surname":"Williams",
      "user_id":10,
      "usergroup_id":"2",
      "usergroup_uuid":"1bf6146d-4fe2-4a49-9fec-adac9053e62d",
      "username":"Jamie",
      "uuid":"2a618d23-fb43-46ae-9f57-181ec611953f"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":4,
      "diarycolor":-1245204,
      "diarycolor_hex":"#ecffec",
      "dragHandle":true,
      "firstname":"James",
      "selected":true,
      "surname":"Sullivan",
      "user_id":3,
      "usergroup_id":"5",
      "usergroup_uuid":"cca9b235-bd13-47cb-907f-19cacc4efa01",
      "username":"Jamo",
      "uuid":"efc6e22c-3b49-4403-a791-d6a781def90d"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":0,
      "diarycolor":-725257,
      "diarycolor_hex":"#f4eef7",
      "dragHandle":true,
      "firstname":"Jordanne",
      "selected":true,
      "surname":"Lawrence",
      "user_id":7,
      "usergroup_id":"2",
      "usergroup_uuid":"1bf6146d-4fe2-4a49-9fec-adac9053e62d",
      "username":"Jordanne",
      "uuid":"133d2745-76a4-4116-bd1d-afbd69da78eb"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":0,
      "diarycolor":-1449257,
      "diarycolor_hex":"#e9e2d7",
      "dragHandle":true,
      "firstname":"Luke",
      "selected":true,
      "surname":"Reynolds",
      "user_id":6,
      "usergroup_id":"3",
      "usergroup_uuid":"7966588e-4ba7-4117-9e36-521a4edd41f2",
      "username":"Luke",
      "uuid":"6ef2d2fc-aa4b-420b-afff-40efbfb8f9a0"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":0,
      "diarycolor":-1356898,
      "diarycolor_hex":"#eb4b9e",
      "dragHandle":true,
      "firstname":"Marco",
      "selected":true,
      "surname":"Lopes",
      "user_id":4,
      "usergroup_id":"3",
      "usergroup_uuid":"7966588e-4ba7-4117-9e36-521a4edd41f2",
      "username":"Marco",
      "uuid":"46d62857-6b32-419f-87c1-4a691e93a42b"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":0,
      "diarycolor":-4871,
      "diarycolor_hex":"#ffecf9",
      "dragHandle":true,
      "firstname":"Nisha",
      "selected":true,
      "surname":"Raghwani",
      "user_id":11,
      "usergroup_id":"5",
      "usergroup_uuid":"cca9b235-bd13-47cb-907f-19cacc4efa01",
      "username":"Nisha",
      "uuid":"4ed1bd0e-68c7-45f1-8810-a85a913e1fb1"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":0,
      "diarycolor":-5764959,
      "diarycolor_hex":"#a808a1",
      "dragHandle":true,
      "firstname":"Office",
      "selected":true,
      "surname":"Staff",
      "user_id":8,
      "usergroup_id":"5",
      "usergroup_uuid":"cca9b235-bd13-47cb-907f-19cacc4efa01",
      "username":"Admin",
      "uuid":"dc7024c5-2861-452b-b938-ad9409d65891"
   },
   {
      "boxchecked":true,
      "days_future":0,
      "days_past":0,
      "diary_position":0,
      "diarycolor":-2036887,
      "diarycolor_hex":"#e0eb69",
      "dragHandle":true,
      "firstname":"Wil",
      "selected":true,
      "surname":"Crompton",
      "user_id":9,
      "usergroup_id":"2",
      "usergroup_uuid":"1bf6146d-4fe2-4a49-9fec-adac9053e62d",
      "username":"Wil",
      "uuid":"061b163b-6f15-42cc-83dd-ac0ecf496b60"
   }
] 
"""

        // Pojo for engineers
        var arEngineersObjectList: List<Users>? = null

        fun getModelFromJson() {
            /* Convert string data to Class  */

            val listType: Type = object : TypeToken<List<Users>>() {}.type
            arEngineersObjectList = Gson().fromJson<List<Users>>(users, listType)
        }

        fun getNameFromEngineerID(id: Int): String? {

            val engineerName = arEngineersObjectList?.filter { it.user_id == id }?.first()?.username
            return engineerName
        }
    }

}
