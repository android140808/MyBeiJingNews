package com.avater.mybeijingnews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.avater.mybeijingnews.R;
import com.avater.mybeijingnews.fragment.ContentFragment;
import com.avater.mybeijingnews.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.xutils.common.util.DensityUtil;

public class MainActivity extends SlidingFragmentActivity {
    public static final String CONTENT_TAG = "content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    private SlidingMenu slidingMenu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //加载主布局
        setContentView(R.layout.activity_main);
        //加载左侧侧滑菜单
        setBehindContentView(R.layout.leftmenu);
        //获得菜单对象
        slidingMenu = getSlidingMenu();
        //设置菜单的滑动模式
        slidingMenu.setTouchModeAbove(SlidingMenu.LEFT);
        //设置页面的模式
        slidingMenu.setMode(SlidingMenu.LEFT);
        //设置主页面占的宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(200));//进行适配

        initFragment();
    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_leftmenu,new LeftMenuFragment(),LEFTMENU_TAG);
        transaction.replace(R.id.fl_main_content,new ContentFragment(),CONTENT_TAG);
        transaction.commit();
    }

    /**
     * 这个方法的作用：
     * 方便在其他类中，通过获取MainActivity的对象m，m再调用此方法得到LeftMenuFragment 的实例。
     * 就可以调用LeftMenuFragment中的方法。
     * @return
     */
    public LeftMenuFragment getLeftMenuFragment() {
        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }

    /**
     * 方便在其他的类中，通过获取Activity的对象m,m再调用此方法得到ContentFragment的实例。
     * 就可以调用ContentFragment中的方法
     * @return
     */
    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(CONTENT_TAG);
    }
    
    private boolean flag = true;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    flag = true;
                    break;
            }
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(flag) {
            flag = false;
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(1,2000);
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
