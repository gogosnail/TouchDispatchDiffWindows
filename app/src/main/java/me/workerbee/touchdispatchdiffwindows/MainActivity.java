package me.workerbee.touchdispatchdiffwindows;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TopPopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPopupWindow = new TopPopupWindow(this);
        findViewById(R.id.btn_popwindow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePopupWindow();
            }
        });
        findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "顶部Window点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void togglePopupWindow() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, 0);
        }
    }

}
