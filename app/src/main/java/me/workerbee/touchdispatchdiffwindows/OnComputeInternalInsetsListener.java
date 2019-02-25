package me.workerbee.touchdispatchdiffwindows;

import android.graphics.Region;
import android.inputmethodservice.InputMethodService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by max on 2019/2/22.<br/>
 */
public class OnComputeInternalInsetsListener implements InvocationHandler {

    private Region touchRegion = null;
    public Object getListener() {
        Object target = null;
        try {
            Class class1 = Class.forName("android.view.ViewTreeObserver$OnComputeInternalInsetsListener");
            target = Proxy.newProxyInstance(OnComputeInternalInsetsListener.class.getClassLoader(),
                    new Class[]{class1}, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    public Region getTouchRegion() {
        return touchRegion;
    }

    public void setTouchRegion(Region touchRegion) {
        this.touchRegion = touchRegion;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            Field regionField = args[0].getClass()
                    .getDeclaredField("touchableRegion");
            regionField.setAccessible(true);
            Field insetField = args[0].getClass()
                    .getDeclaredField("mTouchableInsets");
            insetField.setAccessible(true);
            if (touchRegion != null) {
                Region region = (Region) regionField.get(args[0]);
                region.set(touchRegion);
                insetField.set(args[0], InputMethodService.Insets.TOUCHABLE_INSETS_REGION);
            } else {
                insetField.set(args[0], InputMethodService.Insets.TOUCHABLE_INSETS_FRAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
