package com.lestep.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.lepower.utils.LogUtils;
public class BeanToCls {

	@SuppressWarnings("unchecked")
	public static <T1, T2> T2 beanToCls(T1 bean, T2 clz, String clzMethod,
			Class<?>... paramsTypes) {
		T2 cls = null;
		try {
			if (clz instanceof Class) {
				cls = (T2) ((Class<?>) clz).newInstance();
			} else {
				cls = clz;
			}
			Class<?> beanClz = bean.getClass();
			Field[] beanField = beanClz.getDeclaredFields();
			Class<?>[] params = paramsTypes.clone();
			for (Field field : beanField) {
				field.setAccessible(true);
				BeanEntry entry = field.getAnnotation(BeanEntry.class);
				LogUtils.e("e"+(entry == null));
				if (entry.ignore())
					continue;
				for (int i = 0; i < paramsTypes.length; i++) {
					if (paramsTypes[i].toString().endsWith("Object")) {
						params[i] = field.getType();
					}
				}

				Method method = cls.getClass().getDeclaredMethod(clzMethod,
						params);
				// ///////////请对此处修改为指定的实参处理方式//////////////
				if (entry.name().equals("")) {
					method.invoke(cls, field.getName(), field.get(bean));
				} else {
					method.invoke(cls, entry.name(), field.get(bean));
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cls;

	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	@Documented
	public @interface BeanEntry {
		boolean ignore() default false;

		String name() default "";
	}

}
