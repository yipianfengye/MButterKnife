package uuzuche.com.mbutterknife.parser;

import android.app.Activity;
import android.drm.DrmManagerClient;
import android.text.TextUtils;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liuchao on 2016/1/28.
 */
public class ViewBinderParser {

    /**
     * 初始化解析
     * @param object
     */
    public static void inject(Object object) {
        // 创建解析对象并开始执行解析方法
        ViewBinderParser parser = new ViewBinderParser();
        try {
            parser.parser(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始执行解析方法
     * @param object
     * @throws Exception
     */
    public void parser(final Object object) throws Exception{
        // 获取目标对象字节码
        final Class<?> clazz = object.getClass();
        // 解析成员变量
        parserField(clazz, object);
        // 解析成员方法
        parserMethod(clazz, object);
    }


    /**
     * 解析成员方法
     * @param clazz
     * @throws Exception
     */
    public void parserMethod(Class<?> clazz, final Object object) throws Exception{
        View view = null;
        // 获取目标对象定义的成员方法
        Method[] methods = clazz.getDeclaredMethods();
        // 循环遍历成员变量
        for (final Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                OnClick inject = method.getAnnotation(OnClick.class);
                int id = inject.id();
                if (id < 0) {
                    throw new Exception("id must not be null!!!");
                }
                if (id > 0) {
                    if (object instanceof View) {
                        view = ((View) object).findViewById(id);
                    } else if (object instanceof Activity) {
                        view = ((Activity) object).findViewById(id);
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                method.invoke(object, null);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 解析成员变量
     * @param clazz
     * @throws Exception
     */
    public void parserField(Class<?> clazz, Object object) throws Exception{
        View view = null;
        // 获取目标对象定义的成员变量
        Field[] fields = clazz.getDeclaredFields();
        // 获取目标对象定义的成员方法
        Method[] methods = clazz.getDeclaredMethods();
        // 循环遍历成员变量
        for (Field field : fields) {
            if (field.isAnnotationPresent(ViewBinder.class)) {
                ViewBinder inject = field.getAnnotation(ViewBinder.class);
                int id = inject.id();
                if (id < 0) {
                    throw new Exception("id must not be null!!!");
                }
                if (id > 0) {
                    field.setAccessible(true);
                    if (object instanceof View) {
                        view = ((View) object).findViewById(id);
                    } else if (object instanceof Activity) {
                        view = ((Activity) object).findViewById(id);
                    }
                    field.set(object, view);// 给我们要找的字段设置id
                }
            }
        }
    }

}
