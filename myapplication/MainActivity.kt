package com.example.myapplication

import android.R
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


public class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

var EditText: EditText = findViewById(R.id.noidung)
var Button: Editbutton = findViewById(R.id.editbutton)
View.OnClickListener listenerEditbutton = new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Toast.makeText(view.getContext(), "Vừa bấm vào Access",
            Toast.LENGTH_SHORT).show();
    }
};
edibutton.setOnClickListener(listenerEditbutton);

}
Button edibutton = findViewById(R.id.editbutton);
View.OnClickListener listenerEditbutton = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(view.getContext(), MainActivity.class);
        intent.putExtra("noidung", textView.getText());
        startActivity(intent);


    }
};
edibutton.setOnClickListener(listenerEditbutton);