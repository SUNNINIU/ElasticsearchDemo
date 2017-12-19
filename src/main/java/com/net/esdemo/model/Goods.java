package com.net.esdemo.model;  
  
public class Goods {  
	
    private Long id;  
    private String name;  
    private String[] regionIds;  
      
    public Goods() {  
        super();  
    }  
      
    public Goods(Long id, String name, String[] regionIds) {  
        super();  
        this.id = id;  
        this.name = name;  
        this.regionIds = regionIds;  
    }  
  
    public Long getId() {  
        return id;  
    }  
    public void setId(Long id) {  
        this.id = id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public String[] getRegionIds() {  
        return regionIds;  
    }  
  
    public void setRegionIds(String[] regionIds) {  
        this.regionIds = regionIds;  
    }  
  
  
    @Override  
    public String toString() {  
        return id+" : " + name + " : "+regionIds;  
    }  
}  