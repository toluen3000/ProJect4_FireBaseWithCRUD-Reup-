package com.example.project4_firebase_crud

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project4_firebase_crud.adapter.EmpAdapter
import com.example.project4_firebase_crud.data.EmployeeModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text

class FetchingActivity : AppCompatActivity() {
    private lateinit var listEmp:ArrayList<EmployeeModel>
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)
        //khaibao
        val layoutRecyclerView = findViewById<RecyclerView>(R.id.RecycleViewList)
        //tao layout cho recyleView
        layoutRecyclerView.layoutManager = LinearLayoutManager(this)
        layoutRecyclerView.setHasFixedSize(true)

        listEmp = arrayListOf<EmployeeModel>()
        //lay du lieu tu FireBase
        getInforEmployee()


    }

    private fun getInforEmployee() {
        //khai bao
        val layoutRecyclerView = findViewById<RecyclerView>(R.id.RecycleViewList)
        val txtLoading = findViewById<TextView>(R.id.txtLoading)
        // an recycle khi dang lay du lieu tu firebase
        layoutRecyclerView.visibility = View.GONE
        txtLoading.visibility = View.VISIBLE

        //lay du lieu
        dbReference = FirebaseDatabase.getInstance().getReference("Employees")
        //su dung addValueEventListener()

        dbReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listEmp.clear()
                if (snapshot.exists()){
                    for ( empSnap in snapshot.children ){
                        val empData = empSnap.getValue(EmployeeModel::class.java) // vi moi dong du lieu deu tuan theo class empModel
                        // gan vao 1 bien co kieu du lieu va thuoc tinh nhu the -> add vao list

                        listEmp.add(empData!!)

                    }
                    val mAdapter = EmpAdapter(listEmp)
                    layoutRecyclerView.adapter = mAdapter
                    //on item click
                    mAdapter.setOnItemClickListener(object :EmpAdapter.onItemClickListenter{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity,EmployeeDetailsActivity::class.java)
                            // goi du lieu

                            intent.putExtra("empId",listEmp[position].empId)
                            intent.putExtra("empName",listEmp[position].empName)
                            intent.putExtra("empAge",listEmp[position].empAge)
                            intent.putExtra("empSalary",listEmp[position].empSalary)

                            startActivity(intent)
                        }

                    })
                    // an loading va show list
                    txtLoading.visibility = View.GONE
                    layoutRecyclerView.visibility = View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}