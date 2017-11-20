package com.myproject.util.excel;

public class DataCount
{
  private String province;
  private String city;
  private String area;
  private Integer dog;
  private Integer cat;
  private Integer people;

  public DataCount()
  {
  }

  public DataCount(String province, String city, String area, Integer dog, Integer cat, Integer people)
  {
    this.province = province;
    this.city = city;
    this.area = area;
    this.dog = dog;
    this.cat = cat;
    this.people = people;
  }
  public String getProvince() {
    return this.province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCity() {
    return this.city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getArea() {
    return this.area;
  }
  public void setArea(String area) {
    this.area = area;
  }
  public Integer getDog() {
    return this.dog;
  }
  public void setDog(Integer dog) {
    this.dog = dog;
  }
  public Integer getCat() {
    return this.cat;
  }
  public void setCat(Integer cat) {
    this.cat = cat;
  }
  public Integer getPeople() {
    return this.people;
  }
  public void setPeople(Integer people) {
    this.people = people;
  }
}