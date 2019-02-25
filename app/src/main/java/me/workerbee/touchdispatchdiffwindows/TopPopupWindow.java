package me.workerbee.touchdispatchdiffwindows;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by max on 2019/2/22.<br/>
 * 上面可以穿透Touch事件到下面的PopupWindow
 */
public class TopPopupWindow extends PopupWindow {
    private Context mContext;
    private View mRootView;

    private OnComputeInternalInsetsListener mInvocationHandler;
    private Region mTouchRegion = new Region();

    public TopPopupWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_top, null);
        setContentView(mRootView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTouchRegion.setEmpty();
                mTouchRegion.op(getViewBounds(R.id.tv_left_top), Region.Op.UNION);
                mTouchRegion.op(getViewBounds(R.id.tv_right_top), Region.Op.UNION);
                mTouchRegion.op(getViewBounds(R.id.tv_right_bottom), Region.Op.UNION);
                mTouchRegion.op(getViewBounds(R.id.tv_left_bottom), Region.Op.UNION);
                mInvocationHandler.setTouchRegion(mTouchRegion);
            }
        });
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "顶部Window点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        configTouch();
    }

    private void configTouch() {
        mInvocationHandler = new OnComputeInternalInsetsListener();
        ReflectionUtils.removeOnComputeInternalInsetsListener(mRootView.getViewTreeObserver());
        ReflectionUtils.addOnComputeInternalInsetsListener(mRootView.getViewTreeObserver(), mInvocationHandler.getListener());
        mInvocationHandler.setTouchRegion(mTouchRegion);
    }

    private Rect getViewBounds(int id) {
        View view = mRootView.findViewById(id);
        return new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }
}
