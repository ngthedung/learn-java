package com.hkt.client.swingexp.app.hethong;

public enum Information {
  
  EXCEL(1),ZIP(2),ALL(3);
  private int type;
  Information(int type){
      this.type = type;
  }

  public int getType() {
      return type;
  }       
  
}
