package com.cy;

import com.test.robodemo.R;

import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends Activity implements View.OnClickListener {
    Button button1,button2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId())
        {
            case R.id.button1:
//                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.button2:
                //postSticky会缓存最新的event事件，不管接收方是否消亡，载入时都会检测最新状态 
                EventBus.getDefault().postSticky(new SecondActivityEvent("Message From SecondActivity"));
                break;
        }
    }
}
