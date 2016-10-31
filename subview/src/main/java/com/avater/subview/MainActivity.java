package com.avater.subview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private MySubView mySubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySubView = (MySubView) findViewById(R.id.mysubview);
        mySubView.setOnButtonClickListener(new MySubView.OnButtonClickListener() {
            @Override
            public void onButtonSubClick(View view, int value) {
                Toast.makeText(MainActivity.this, "value==" + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onButtonAddClick(View view, int value) {
                Toast.makeText(MainActivity.this, "value==" + value, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
