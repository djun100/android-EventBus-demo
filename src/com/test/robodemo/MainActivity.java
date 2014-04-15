package com.test.robodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

public class MainActivity extends Activity implements View.OnClickListener{
    Button button1,button2,button3,button4;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        textView = (TextView) findViewById(R.id.textView);
        //注册：三个参数分别是，消息订阅者（接收者），接收方法名，事件类
        EventBus.getDefault().register(this,"setTextA",SetTextAEvent.class);
        EventBus.getDefault().register(this,"setTextB",SetTextBEvent.class);
//        EventBus.getDefault().register(this,"messageFromSecondActivity",SecondActivityEvent.class);
        EventBus.getDefault().registerSticky(this, "messageFromSecondActivity", SecondActivityEvent.class);
        EventBus.getDefault().register(this,"countDown",CountDownEvent.class);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }
    /**
     * 与注册对应的方法名和参数,没有后缀，默认使用PostThread模式，即跟发送事件在同一线程执行
     * @param event
     */
    public void setTextA(SetTextAEvent event)
    {
        textView.setText("A");
    }
    public void setTextB(SetTextBEvent event)
    {
        textView.setText("B");
    }
    public void messageFromSecondActivity(SecondActivityEvent event)
    {
        textView.setText(event.getText());
    }
    
    /**
     * 加Async后缀，异步执行。还有MainThread和BackgroundThread，分别是在主线程（UI）执行和后台线程（单一）执行
     * @param event
     */
    public void countDownAsync(CountDownEvent event)
    {
        for(int i=event.getMax();i>0;i--)
        {
            Log.v("CountDown", String.valueOf(i));
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onClick(View v) {
       //发送事件
        switch(v.getId())
        {
            case R.id.button1:
                EventBus.getDefault().post(new SetTextAEvent());
                break;
            case R.id.button2:
                EventBus.getDefault().post(new SetTextBEvent());
                break;
            case R.id.button3:
                //测试SecondActivity中发送事件，MainActivity接收
                Intent intent=new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button4:
                EventBus.getDefault().post(new CountDownEvent(99));
                break;
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().unregister(this);
    }
}
