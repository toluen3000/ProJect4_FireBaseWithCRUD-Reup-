package com.example.project4_firebase_crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.project4_firebase_crud.data.EmployeeModel
import com.example.project4_firebase_crud.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtAge: EditText
    private lateinit var edtSalary: EditText

    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)
        val btnSave  = findViewById<AppCompatButton>(R.id.btnSaveData)


        dbReference = FirebaseDatabase.getInstance().getReference("Employees")
        //xu ly su kien nut save
        btnSave.setOnClickListener {
            saveEmployee()
        }

        }

    private fun saveEmployee() {
        //khai bao
        val edtName  = findViewById<EditText>(R.id.edtName)
        val edtAge  = findViewById<EditText>(R.id.edtAge)
        val edtSalary  = findViewById<EditText>(R.id.edtSalary)

        val empName = edtName.text.toString()
        val empAge = edtAge.text.toString()
        val empSalary = edtSalary.text.toString()
        // lay du lieu
        val empId = dbReference.push().key!!
        val employee = EmployeeModel(empId,empName,empAge,empSalary)

        //kiem tra dieu kien
        if (empName.isEmpty()){
            edtName.error = "Please enter the name"
            return
        }
        if (empAge.isEmpty()){
            edtAge.error = "Please enter the age"
            return
        }
        if (empSalary.isEmpty()){
            edtSalary.error = "Please enter the salary"
            return
        }

        dbReference.child(empId).setValue(employee).addOnCompleteListener {
            Toast.makeText(this,"Data insert completed",Toast.LENGTH_SHORT).show()
            edtName.setText("")
            edtAge.setText("")
            edtSalary.setText("")

        }
            .addOnFailureListener {err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
            }
    }
}
