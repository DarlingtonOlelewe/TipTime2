package com.example.tiptime2

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime2.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener {
            var toast: Toast = Toast.makeText(this, "calculated", Toast.LENGTH_SHORT)
            toast.show()
            calculate()
        }
        binding.costOfService.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }


    }

    private fun calculate() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        val options = binding.selectTipOption.checkedRadioButtonId
        var percent:Double = when (options){
            R.id.twenty_percent -> 0.50
            R.id.eighteen_percent -> 0.18
            else -> 0.15
        }
        if(cost == null){
            binding.result.text = "0.00"
            return
        }
        var calTip = cost * percent

        if (binding.roundUp.isChecked){
            calTip = ceil(calTip)
        }

        val tip = NumberFormat.getCurrencyInstance().format(calTip)

        binding.result.text = tip
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}