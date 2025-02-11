package jazmin.server.web;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.util.Iterator;

import javax.el.BeanELResolver;
import javax.el.ELContext;
import javax.el.PropertyNotFoundException;
/**
 * 
 * @author yama
 * 24 Jan, 2015
 */
public class PublicFieldELResolver extends BeanELResolver {
	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return null;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context,
			Object base) {
		return null;
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		return null;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		if (base == null) {
			return null;
		}
		try {
			Field field = base.getClass().getDeclaredField((String) property);
			field.setAccessible(true);
			Object value = field.get(base);
			context.setPropertyResolved(true);
			return value;
		} catch (NoSuchFieldException e) {
			return super.getValue(context,base, property);
		}catch (Exception e) {
			throw new PropertyNotFoundException(e);
		}
	}
	
	//
	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		return false;
	}

	@Override
	public void setValue(ELContext context, Object base, Object property,
			Object value) {
		if (base == null) {
			return;
		}
		try {
			Field field = base.getClass().getDeclaredField((String) property);
			field.setAccessible(true);
			field.set(base, value);
			context.setPropertyResolved(true);
		} catch (NoSuchFieldException e) {
			 super.setValue(context,base, property,value);
		}catch (Exception e) {
			throw new PropertyNotFoundException(e);
		}
	}
}