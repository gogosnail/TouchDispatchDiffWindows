package me.workerbee.touchdispatchdiffwindows;

import android.view.ViewTreeObserver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by max on 2019/2/22.<br/>
 */
public class ReflectionUtils {

    private ReflectionUtils() {

    }

    public static void removeOnComputeInternalInsetsListener(ViewTreeObserver viewTree) {
        if (viewTree == null) {
            return;
        }
        try {
            Class<?> clazz = Class.forName("android.view.ViewTreeObserver");
            Field field = viewTree.getClass().getDeclaredField("mOnComputeInternalInsetsListeners");
            field.setAccessible(true);
            Object listenerList = field.get(viewTree);
            Method method = listenerList.getClass().getDeclaredMethod("getArray");
            method.setAccessible(true);
            ArrayList<Object> list = (ArrayList<Object>) method.invoke(listenerList);
            Class<?> classes[] = {Class.forName("android.view.ViewTreeObserver$OnComputeInternalInsetsListener")};
            if (list != null && list.size() > 0) {
                clazz.getDeclaredMethod("removeOnComputeInternalInsetsListener", classes).invoke(viewTree,
                        list.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addOnComputeInternalInsetsListener(ViewTreeObserver viewTree, Object object) {
        if (viewTree == null) {
            return;
        }
        try {
            Class<?> classes[] = {Class.forName("android.view.ViewTreeObserver$OnComputeInternalInsetsListener")};
            Class<?> clazz = Class.forName("android.view.ViewTreeObserver");
            clazz.getDeclaredMethod("addOnComputeInternalInsetsListener", classes).invoke(viewTree,
                    object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
