package com.hkt.client.swingexp.model;

import java.util.List;

import com.hkt.client.rest.ClientContext;
import com.hkt.client.rest.service.LocaleServiceClient;
import com.hkt.module.config.locale.Country;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.config.locale.ProductUnit;

public class LocaleModelManager {

  private static LocaleModelManager instance = new LocaleModelManager();

  private ClientContext clientContext = ClientContext.getInstance();
  private LocaleServiceClient clientCore = clientContext.getBean(LocaleServiceClient.class);

  private LocaleModelManager() {
  }

  public void saveSettings() {
    ClientContext.getInstance();
  }

  static public LocaleModelManager getInstance() {
    return instance;
  }

  public List<Country> getCountries() throws Exception {
    return clientCore.getCountries();
  }

  public Country getCountry(String code) throws Exception {
    return clientCore.getCountry(code);
  }

  public Country saveCountry(Country country) throws Exception {
    return clientCore.saveCountry(country);
  }

  public boolean deleteCountry(Country country) throws Exception {
    return clientCore.deleteCountry(country);
  }

  public List<Currency> getCurrencies() throws Exception {
    return clientCore.getCurrencies();
  }

  public Currency getCurrencyByCode(String code) {
    try {
      return clientCore.getCurrency(code);
    } catch (Exception e) {
      return null;
    }

  }

  public Currency saveCurrency(Currency c) throws Exception {
    return clientCore.saveCurrency(c);
  }

  public boolean deleteCurrency(Currency c) throws Exception {
    return clientCore.deleteCurrency(c);
  }

  public List<ProductUnit> getProductUnits() throws Exception {
    return clientCore.getProductUnits();
  }

  public ProductUnit getProductUnitByCode(String code){
  	try {
  		return clientCore.getProductUnit(code);
    } catch (Exception e) {
	    return null;
    }
    
  }

  public ProductUnit getProductUnitByDefault() {
    try {

      ProductUnit pU = LocaleModelManager.getInstance().getProductUnitByCode("dv");
      if (pU == null) {
        pU = new ProductUnit();
        pU.setCode("dv");
        pU.setName("Đơn vị");
        pU.setDescription("dv");
        pU.setRate(1);
      }
      return pU;
    } catch (Exception ex) {
      return null;
    }
  }

  public ProductUnit saveProductUnit(ProductUnit productUnit) throws Exception {
    return clientCore.saveProductUnit(productUnit);
  }

  public boolean deleteProductUnit(ProductUnit productUnit) throws Exception {
    return clientCore.deleteProductUnit(productUnit);
  }

  public void deleteTest(String test) throws Exception {
    List<Country> countries = getCountries();
    for (int i = 0; i < countries.size(); i++) {
      if (countries.get(i).getName().indexOf(test) >= 0) {
        clientCore.deleteCountry(countries.get(i));
      }
    }

    List<Currency> cities = getCurrencies();
    for (int i = 0; i < cities.size(); i++) {
      if (cities.get(i).getName().indexOf(test) >= 0) {
        clientCore.deleteCurrency(cities.get(i));
      }
    }

    List<ProductUnit> productUnits = getProductUnits();
    for (int i = 0; i < productUnits.size(); i++) {
      if (productUnits.get(i).getName().indexOf(test) >= 0) {
        clientCore.deleteProductUnit(productUnits.get(i));
      }
    }
  }
}
