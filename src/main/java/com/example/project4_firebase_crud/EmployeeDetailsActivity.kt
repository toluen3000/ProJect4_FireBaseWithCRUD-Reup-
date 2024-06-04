package com.example.project4_firebase_crud

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project4_firebase_crud.data.EmployeeModel
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var dialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        showValueToView()
        //xoa du lieu
        val btnDelete = findViewById<AppCompatButton>(R.id.btnDeleteInfor)
        btnDelete.setOnClickListener {
            deleteEmployee( intent.getStringExtra("empId").toString())
        }

        //btn update
        val btnUpdate = findViewById<AppCompatButton>(R.id.btnUpdateInfor)
        btnUpdate.setOnClickListener {
            showUpdateDiaLog(
                         intent.getStringExtra("empId").toString(),
                         intent.getStringExtra("empName").toString()
            )
        }


    }

    private fun showUpdateDiaLog(empId: String, empName: String) {
        val build = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)
        build.setView(mDialogView)
        //khai bao lai tham so hien thi
        val txtName = findViewById<TextView>(R.id.txtNameInfor)
        val txtAge = findViewById<TextView>(R.id.txtAgeInfor)
        val txtSalary = findViewById<TextView>(R.id.txtSalaryInfor)

        //link tham so vao dialog
        val edtName = mDialogView.findViewById<EditText>(R.id.edtUpdateName)

        val edtAge = mDialogView.findViewById<EditText>(R.id.edtUpdateAge)

        val edtSalary = mDialogView.findViewById<EditText>(R.id.edtUpdateSalary)

        val btnUpdateEmp = mDialogView.findViewById<AppCompatButton>(R.id.btnUpdating)

        // lay thong tin de chen vao dialog
        txtName.text = intent.getStringExtra("empName")
        txtAge.text = intent.getStringExtra("empAge")
        txtSalary.text = intent.getStringExtra("empSalary")

        edtName.setText(txtName.text)
        edtAge.setText(txtAge.text)
        edtSalary.setText(txtSalary.text)


        //build.setTitle("Updating...")
        val alertDialog = build.create()
        alertDialog.show()

        //btnUpdate
        btnUpdateEmp.setOnClickListener {
            updateDataEmp(
                empId,edtName.text.toString(),edtAge.text.toString(),edtSalary.text.toString()
            )

            Toast.makeText(applicationContext,"Data updated",Toast.LENGTH_SHORT).show()
            //setText hien thi cap nhat cho dialog
            txtName.setText(edtName.text.toString())
            txtAge.setText(edtAge.text.toString())
            txtSalary.setText(edtSalary.text.toString())
            alertDialog.dismiss()
        }

    }

    private fun updateDataEmp(
        empId: String,
        empName: String,
        empAge: String,
        empSalary: String
    ) {
        val dbReference = FirebaseDatabase.getInstance().getReference("Employees").child(empId)
        val empInfo = EmployeeModel(empId,empName,empAge,empSalary)
        //update
        dbReference.setValue(empInfo)
    }


    private fun deleteEmployee(string: String) {
        val txtId = findViewById<TextView>(R.id.txtIdInfor)
        val id = txtId.text.toString()
        val dbReference = FirebaseDatabase.getInstance().getReference("Employees").child(id)

        val mTask = dbReference.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this,"Delete completed",Toast.LENGTH_SHORT).show()
            val intentGoBack  = Intent(this@EmployeeDetailsActivity,FetchingActivity::class.java)
            startActivity(intentGoBack)
        }
            .addOnFailureListener {err ->
                Toast.makeText(this,"ERROR ${err.message}!!!",Toast.LENGTH_SHORT).show()
            }
    }

    private fun showValueToView() {
        //khai bao
        val txtId = findViewById<TextView>(R.id.txtIdInfor)
        val txtName = findViewById<TextView>(R.id.txtNameInfor)
        val txtAge = findViewById<TextView>(R.id.txtAgeInfor)
        val txtSalary = findViewById<TextView>(R.id.txtSalaryInfor)

        txtId.text = intent.getStringExtra("empId")
        txtName.text = intent.getStringExtra("empName")
        txtAge.text = intent.getStringExtra("empAge")
        txtSalary.text = intent.getStringExtra("empSalary")
    }
}