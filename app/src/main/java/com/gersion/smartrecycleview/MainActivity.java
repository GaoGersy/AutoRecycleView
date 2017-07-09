package com.gersion.smartrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gersion.smartrecycleviewlibrary.SmartRecycleView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Bean> data = new ArrayList<>();
    private PullAdapter mAdapter;
    private SmartRecycleView mTestView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void toViewTest(View view){
        Intent intent = new Intent(this,ViewTestActivity.class);
        startActivity(intent);
    }

    public void toBuilderTest(View view){
        Intent intent = new Intent(this,BuilderTestActivity.class);
        startActivity(intent);
    }

}
