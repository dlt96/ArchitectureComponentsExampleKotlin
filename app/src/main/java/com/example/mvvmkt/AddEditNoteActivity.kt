package com.example.mvvmkt

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast

class AddEditNoteActivity : AppCompatActivity() {

    companion object {
        @JvmField val EXTRA_ID = "EXTRA_ID"
        @JvmField val EXTRA_TITLE = "EXTRA_TITLE"
        @JvmField val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"
        @JvmField val EXTRA_PRIORITY = "EXTRA_PRIORITY"
    }

    private var editTextTitle: EditText? = null
    private var editTextDescription: EditText? = null
    private var numberPickerPriority: NumberPicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        numberPickerPriority = findViewById(R.id.number_picker_priority)

        numberPickerPriority?.minValue = 1
        numberPickerPriority?.maxValue = 100//ojo

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)//ojo
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            editTextTitle?.setText(intent.getStringExtra(EXTRA_TITLE))
            editTextDescription?.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            numberPickerPriority?.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Note"
        }

    }

    private fun saveNote() {
        val title = editTextTitle?.text.toString()
        val description = editTextDescription?.text.toString()
        val priority = numberPickerPriority?.value

        if (checkFieldsEmpty(title, description)) return

        val data = Intent()
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, description)
        data.putExtra(EXTRA_PRIORITY, priority)

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun checkFieldsEmpty(vararg fields: String): Boolean {
        for (field in fields) {
            if (field.trim().isEmpty()) {
                Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
