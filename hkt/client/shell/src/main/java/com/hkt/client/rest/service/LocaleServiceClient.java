
package com.hkt.client.rest.service;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hkt.client.rest.RESTClient;
import com.hkt.module.config.locale.Country;
import com.hkt.module.config.locale.Currency;
import com.hkt.module.config.locale.ProductUnit;
import com.hkt.module.core.rest.Request;
import com.hkt.module.core.rest.Response;

@Component
public class LocaleServiceClient {
  
  @Autowired
  private RESTClient client;
  
  public List<Country> getCountries() throws Exception {
    Request req = create("getCountries");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Country>>() {
    });
  }
  
  public Country getCountry(String code) throws Exception {
    Request req = create("getCountry");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(Country.class);
  }
  
  public Country saveCountry(Country country) throws Exception {
    Request req = create("saveCountry");
    req.addParam("country", country);
    Response res = client.POST(req);
    return res.getDataAs(Country.class);
  }
  
  public boolean deleteCountry(Country country) throws Exception {
    Request req = create("deleteCountry");
    req.addParam("country", country);
    client.POST(req);
    return true;
  }
  
  public List<Currency> getCurrencies() throws Exception {
    Request req = create("getCurrencies");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<Currency>>() {
    });
  }
  
  public Currency getCurrency(String code) throws Exception {
    Request req = create("getCurrency");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(Currency.class);
  }
  
  public Currency saveCurrency(Currency currency) throws Exception {
    Request req = create("saveCurrency");
    req.addParam("currency", currency);
    Response res = client.POST(req);
    return res.getDataAs(Currency.class);
  }
  
  public boolean deleteCurrency(Currency c) throws Exception {
    Request req = create("deleteCurrency");
    req.addParam("currency", c);
    client.POST(req);
    return true;
  }
  
  public List<ProductUnit> getProductUnits() throws Exception {
    Request req = create("getProductUnits");
    Response res = client.POST(req);
    return res.getDataAs(new TypeReference<List<ProductUnit>>() {
    });
  }
  
  public ProductUnit getProductUnit(String code) throws Exception {
    Request req = create("getProductUnit");
    req.addParam("code", code);
    Response res = client.POST(req);
    return res.getDataAs(ProductUnit.class);
  }
  
  public ProductUnit saveProductUnit(ProductUnit productUnit) throws Exception {
    Request req = create("saveProductUnit");
    req.addParam("productUnit", productUnit);
    Response res = client.POST(req);
    return res.getDataAs(ProductUnit.class);
  }
  
  public boolean deleteProductUnit(ProductUnit productUnit) throws Exception {
    Request req = create("deleteProductUnit");
    req.addParam("productUnit", productUnit);
    client.POST(req);
    return true;
  }
  
  Request create(String method) {
    return new Request("config", "LocaleService", method);
  }
}
