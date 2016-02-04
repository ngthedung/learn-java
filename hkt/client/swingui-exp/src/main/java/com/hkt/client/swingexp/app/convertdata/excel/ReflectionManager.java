package com.hkt.client.swingexp.app.convertdata.excel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionManager {
  private static Class clsInput;

  public ReflectionManager() {
  }

  public ReflectionManager(Class clsInput) {
      this.clsInput = clsInput;
  }

  public Class getClsInput() {
      return clsInput;
  }

  public void setClsInput(Class clsInput) {
      this.clsInput = clsInput;
  }

  public Field[] getListField() {
      return clsInput.getDeclaredFields();
  }

  public Method[] getListMethod() {
      return clsInput.getDeclaredMethods();
  }

  public static String getFieldName(Field f) {
      return f.getName();
  }

  public static String getDeclaredField(Field f) {
      int mod = f.getModifiers();
      String declared = Modifier.toString(mod);
      return declared;
  }

  public static String getReturnTypeOfField(Field f) {
      Class c = f.getType();
      String type = c.getSimpleName();
      return type;
  }

  public static String getMethodName(Method m) {
      return m.getName();
  }

  public static String getDeclaredMethod(Method m) {
      int mod = m.getModifiers();
      String declared = Modifier.toString(mod);
      return declared;
  }

  public static String getReturnTypeOfMethod(Method m) {
      Class c = m.getReturnType();
      String type = c.getSimpleName();
      return type;
  }

  /**
   * Lay thong tin Constructor
   */
  public static Constructor getConstructor() {
      Constructor[] cons = clsInput.getDeclaredConstructors();
      for (Constructor c : cons) {
          Class cl[] = c.getParameterTypes();
          if (cl.length == 0) {
              return c;
          }
      }
      return null;
  }

  /**
   * 
   * @param methodName : ten method
   * @param inputMethod : Danh sach type dau vao cua method
   * @param methobj : Doi tuong chua method
   * @param arglist : Danh sach value truyen cho type dau vao
   * @return 
   */
  public static Object runMethod(String methodName, Class[] inputMethod, Object methobj, Object arglist[]) {
      try {
          Method meth = clsInput.getMethod(
                  methodName, inputMethod);
          meth.setAccessible(true);
          Object retobj = meth.invoke(methobj, arglist);
          return retobj;
      } catch (Throwable e) {
          System.err.println(e);
          return "";
      }
  }

  public static Object runMethod(Method method, Object methobj) {
      try {
          Object[] arglist = {};
          method.setAccessible(true);
          Object retobj = method.invoke(methobj, arglist);
          return retobj;
      } catch (Throwable e) {
          System.err.println(e);
          return "";
      }
  }
}
