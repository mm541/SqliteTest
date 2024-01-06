package com.example.sqlitetest

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val TAG = "MainActivity"
private const val REQUEST_CODE_READ_CONTACTS = 1
class MainActivity : AppCompatActivity() {
    private lateinit var fab:FloatingActionButton
    private var readGranted = false
    @SuppressLint("Range", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val hasReadContactPermission = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)
        if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG,"Permission granted")
            readGranted = true
        }else {
            Log.d(TAG,"Requesting permissions() ")
            requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_READ_CONTACTS)
            Log.d(TAG,"Permission denied")
        }
        fab = findViewById(R.id.fab)
        val contacts = ArrayList<Contacts>()
        fab.setOnClickListener {
            Log.d(TAG,"fab Clicked: starts")
            val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                cursor?.use {
                    while (it.moveToNext()) {
                        contacts.add(Contacts(it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)),
                            0,
                            ""))
                    }
                }
            findViewById<ListView>(R.id.contacts).adapter = ContactsArrayAdapter(this,R.layout.contacts_detail,contacts)
            }

        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG,"onRequestPermissionsResult() called")
        when(requestCode) {
            REQUEST_CODE_READ_CONTACTS -> {
                readGranted = grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED
            }
        }
        fab.isEnabled = readGranted
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}