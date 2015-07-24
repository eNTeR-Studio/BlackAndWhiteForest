package enter.blackandwhiteforest.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import enter.blackandwhiteforest.api.BAWFEventReceiver;
import enter.blackandwhiteforest.api.IBAWFEventReceiver;

/**@author fxzjshm*/
public class BAWFEventBus {

	public List<IBAWFEventReceiver> receiverList = new ArrayList<IBAWFEventReceiver>();
	public List<Method> receiverMethodList = new ArrayList<Method>();
	public List<Object> receiverInstanceList = new ArrayList<Object>();

	public boolean register(Object receiver) {
		boolean isSuccessful = true;
		Method[] methods = receiver.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {

			Annotation[] annotations = methods[i].getAnnotations();
			for (int j = 0; j < annotations.length; j++) {
				if (annotations[j].annotationType().getName().equals(BAWFEventReceiver.class.getName())) {
					isSuccessful = isSuccessful && receiverMethodList.add(methods[i])&&receiverInstanceList.add(receiver);
					break;
				} else if (j == annotations.length) {
					isSuccessful = false;
				}
			}
			/*
			 * boolean isAnnotationPresent =
			 * methods[i].isAnnotationPresent(BAWFEventReceiver.class); if
			 * (isAnnotationPresent) isSuccessful = isSuccessful &&
			 * receiverMethodList.add(methods[i]);
			 */
		}
		if (receiver instanceof IBAWFEventReceiver) {
			if (isSuccessful) {
				return receiverList.add((IBAWFEventReceiver) receiver);
			} else {
				return false;
			}
		}
		return isSuccessful;
	}

	public boolean post(BAWFEvent event) {
		for (Iterator<IBAWFEventReceiver> iterator = receiverList.iterator(); iterator.hasNext();) {
			iterator.next().receiveEvent(event);
		}
		for (Iterator<Method> iterator = receiverMethodList.iterator(); iterator.hasNext();) {
			Method currentMethod = iterator.next();
			Class<?>[] parClassType = currentMethod.getParameterTypes();
			if (parClassType.length == 1 && parClassType[0].getName().equals(event.getClass().getName())) {
				try {
					currentMethod.invoke(receiverInstanceList.get(receiverMethodList.indexOf(currentMethod)),event);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	public boolean unregister(Object receiver) {
		boolean isSuccessful = true;
		Method[] methods = receiver.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {

			Annotation[] annotations = methods[i].getAnnotations();
			for (int j = 0; j < annotations.length; j++) {
				if (annotations[j].annotationType().getName().equals(BAWFEventReceiver.class.getName())) {
					isSuccessful = isSuccessful && receiverMethodList.remove(methods[i])&&receiverInstanceList.remove(receiver);
					break;
				} else if (j == annotations.length) {
					isSuccessful = false;
				}
			}
			/*
			 * boolean isAnnotationPresent =
			 * methods[i].isAnnotationPresent(BAWFEventReceiver.class); if
			 * (isAnnotationPresent) isSuccessful = isSuccessful &&
			 * receiverMethodList.remove(methods[i]);
			 */
		}
		if (receiver instanceof IBAWFEventReceiver) {
			if (isSuccessful) {
				return receiverList.remove((IBAWFEventReceiver) receiver);
			} else {
				return false;
			}
		}
		return isSuccessful;
	}
}
