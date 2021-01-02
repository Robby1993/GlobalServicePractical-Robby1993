package com.robinson.globalserviceproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.robinson.globalserviceproject.interfaces.ItemClickListener
import com.robinson.globalserviceproject.model.EmailItem
import com.robinson.globalserviceproject.utilities.SharedPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, ItemClickListener {
    private lateinit var sharedPreference: SharedPreference
    var department = arrayOf("Android", "IOS")
    var selectedDepartment = "Android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, department)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sharedPreference = SharedPreference()
        with(spDepartment)
        {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@MainActivity
            prompt = "Select your Department"
            gravity = Gravity.CENTER

        }

        ivPluse.setOnClickListener {
            val id = "" + System.currentTimeMillis();
            //sharedPreference.addData(this, EmailItem(id, "", selectedDepartment))
            SharedPreference.userList.add(EmailItem(id, "", selectedDepartment))
            getData()
        }
        tvSave.setOnClickListener {
            if (SharedPreference.userList.size > 0) {
                for (i in 0 until SharedPreference.userList.size) {
                    val mailItem = SharedPreference.userList[i]
                    if (mailItem.type == selectedDepartment && mailItem.email.isNotEmpty()) {
                        val listGet = sharedPreference.getData(this)
                        if (listGet != null) {
                            if (!sharedPreference.contains(listGet, mailItem.email)) {
                                sharedPreference.addData(this, mailItem)
                            }
                        } else {
                            sharedPreference.addData(this, mailItem)
                        }
                    }
                }
            }
            showToast(message = "Success")
            if (SharedPreference.userList.size > 0) {
                SharedPreference.userList.clear()
            }
            Log.d("getDATA", "------" + "add" + Gson().toJson(SharedPreference.userList))
            getData()
        }
        getData()
    }


    private fun getData() {
        val displayData: ArrayList<EmailItem> = ArrayList()
        val listGet = sharedPreference.getData(this)
        if (listGet != null) {
            if (SharedPreference.userList.size > 0) {
                listGet.addAll(SharedPreference.userList)
            }
            for (i in 0 until listGet.size) {
                val mailItem = listGet[i]
                if (mailItem.type == selectedDepartment) {
                    displayData.add(mailItem)
                }
            }
            Log.d("getDATA", "------" + listGet.size)
            Log.d("getDATA", "------" + displayData.size)
        } else {
            if (SharedPreference.userList.size > 0) {
                for (i in 0 until SharedPreference.userList.size) {
                    val mailItem = SharedPreference.userList[i]
                    if (mailItem.type == selectedDepartment) {
                        displayData.add(mailItem)
                    }
                }
            }
        }

        val adapter = DepartmentListAdapter(this, displayData, this)
        rvDepartment.adapter = adapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //  showToast(message = "Spinner 1 Position:${position} and department: ${department[position]}")
        if (department[position] == "Android") {
            tvDepartment.text = "Android";
            selectedDepartment = "Android";
            getData()
        } else {
            tvDepartment.text = "IOS";
            selectedDepartment = "IOS";
            getData()
        }

    }

    private fun showToast(
            context: Context = applicationContext,
            message: String,
            duration: Int = Toast.LENGTH_LONG
    ) {
        Toast.makeText(context, message, duration).show()
    }

    override fun onClick(data: Any) {
        TODO("Not yet implemented")
    }


}