package com.hkt.client.swingexp.app.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate implements Comparable<MyDate> {
  private Date d;

  public MyDate(Date d) {
    this.d = d;
  }

  public String toString() {
  	if(d!=null){
    return new SimpleDateFormat("dd/MM/yyyy").format(d);
  	}else {
			return "";
		}
  }

  public Date getD() {
    return d;
  }

  @Override
  public int compareTo(MyDate anotherDate) {
  	try {
  		 long thisTime = d.getTime();
  	    long anotherTime = anotherDate.getD().getTime();
  	    return (thisTime < anotherTime ? -1 : (thisTime == anotherTime ? 0 : 1));
    } catch (Exception e) {
	     return 1;
    }
   
  }

}
