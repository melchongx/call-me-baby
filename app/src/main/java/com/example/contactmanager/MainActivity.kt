package com.example.contactmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanager.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    public lateinit var databaseReference: DatabaseReference
    public lateinit var contactAdapter: ContactAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("DatabaseName")
        var databaseClass = Contact("26011", "09054709460")

        databaseReference.child("MelItem10").setValue(databaseClass).addOnSuccessListener {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }

        contactAdapter = ContactAdapter(mutableListOf(
            Contact("John Daniel", "09458854796")
        ))

        binding.rvContactItems.adapter = contactAdapter
        binding.rvContactItems.layoutManager = LinearLayoutManager(this)

        binding.ibCreateLink.setOnClickListener {
//            val newContactActivityIntent = Intent(this, NewContactActivity::class.java)
//            this.startActivity(newContactActivityIntent)


            val newContact = Contact(binding.etNewContactName.text.toString(), binding.etNewContactNumber.text.toString())
            if (newContact.name.isNotBlank() && newContact.mobileNo.isNotBlank()) {
                contactAdapter.addContact(newContact)

                // implement logic to add in list, then send back to main activity for recycler view injection, HOW TO EVEN DO THAT
                binding.etNewContactName.text.clear()
                binding.etNewContactNumber.text.clear()
            } else {
                Toast.makeText(this, "Fill in the fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cbShowFavorites.setOnCheckedChangeListener { _, isChecked ->
            contactAdapter.filterFavorites(isChecked)
        }
    }
}
