package com.hkt.client.swingexp.app.component;

import java.lang.reflect.InvocationTargetException;
import java.text.Normalizer;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class CollectionUtil<E> {

  private static CollectionUtil instancce;

  public static CollectionUtil getInstancce() {
      if (instancce == null) {
          instancce = new CollectionUtil();
      }
      return instancce;
  }

  /**
   * <p>Phương thức tìm kiếm trên List theo 1 tiêu chí nào đó của bean</p>
   *
   * @param beans Danh sách bean
   * @param propertyName Tiêu chí trên bean
   * @param textSearch Từ khóa tìm kiếm
   * @return Danh sách sau khi tìm kiếm
   */
  public List<E> filter(List<E> beans, final List<String> propertyNames, final String textSearch) {
      Predicate<E> filter = new Predicate<E>() {

          @Override
          public boolean apply(E input) {
              String str = null;
              try {
                  str = Normalizer.normalize(textSearch, Normalizer.Form.NFD).replaceAll("\\p{Block=CombiningDiacriticalMarks}+", "").toLowerCase().replaceAll("\u0111", "d");
                  if (propertyNames != null && !propertyNames.isEmpty()) {
                      for (int i = 0; i < propertyNames.size(); i++) {
                          if (propertyNames.get(i).equalsIgnoreCase("toString")) {
                              if (Normalizer.normalize(input.toString(),
                                      Normalizer.Form.NFD).replaceAll("\\p{Block=CombiningDiacriticalMarks}+", "").toLowerCase().replaceAll("\u0111", "d").contains(str)) {
                                  return true;
                              }
                          } else {
                              if (BeanUtils.getProperty(input, propertyNames.get(i)) != null) {
                                  if (Normalizer.normalize(BeanUtils.getProperty(input, propertyNames.get(i)),
                                          Normalizer.Form.NFD).replaceAll("\\p{Block=CombiningDiacriticalMarks}+", "").toLowerCase().replaceAll("\u0111", "d").contains(str)) {
                                      return true;
                                  }
                              }
                          }
                      }
                  }
                  return false;
              } catch (IllegalAccessException ex) {
              } catch (InvocationTargetException ex) {
              } catch (NoSuchMethodException ex) {
              } finally {
                  str = null;
              }
              return false;
          }
      };
      return Lists.newArrayList(Collections2.filter(beans, filter));
  }
}
