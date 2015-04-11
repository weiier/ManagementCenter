package org.ow2.aspirerdfid.tracking.demo.db;

import java.util.List;

public class SubSystem implements java.io.Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Factory_ID;//�ֿ���
    private String Factory_Name;//�ֿ�����
    private String Factory_IP;//�ֿ�ip
    private String Factory_City;//�ֿ����ڳ���
    private String Factory_Loacation;//�ֿ�����
    private String Factory_Description;//�ֿ�����
    private int Factory_State;//�ֿ�״̬0���� 1����
    private int Factory_Owner;//����Ȩ 0 �������� 1 ����������
    private List<Item> items;  //�Լ��¼ӵ�����list

    public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public SubSystem( String factory_id, String factory_name,String factory_ip, String factory_city, String factory_location,String factory_description,int factory_owner){
    	this.Factory_City = factory_city;
    	this.Factory_Description = factory_description;
    	this.Factory_ID = factory_id;
    	this.Factory_IP = factory_ip;
    	this.Factory_Name = factory_name;
    	this.Factory_Loacation = factory_location;
    	this.Factory_State = 1;
    	this.Factory_Owner = factory_owner;
    }
    public SubSystem(SubSystem factory){
    	this.Factory_City = factory.getFactory_City();
    	this.Factory_Description = factory.getFactory_Description();
    	this.Factory_ID = factory.getFactory_ID();
    	this.Factory_IP = factory.getFactory_IP();
    	this.Factory_Name = factory.getFactory_Name();
    	this.Factory_Loacation = factory.getFactory_Loacation();
    	this.Factory_State = factory.getFactory_State();
    	this.Factory_Owner = factory.getFactory_Owner();
    }
    public SubSystem(){
    	this.Factory_City = "";
    	this.Factory_Description="";
    	this.Factory_ID="";
    	this.Factory_IP="";
    	this.Factory_Loacation="";
    	this.Factory_Name="";
    	this.Factory_State = 1;
    	this.Factory_Owner = -1;
    }

	public String getFactory_ID() {
		return Factory_ID;
	}

	public void setFactory_ID(String factory_ID) {
		Factory_ID = factory_ID;
	}

	public String getFactory_Name() {
		return Factory_Name;
	}

	public void setFactory_Name(String factory_Name) {
		Factory_Name = factory_Name;
	}

	public String getFactory_IP() {
		return Factory_IP;
	}

	public void setFactory_IP(String factory_IP) {
		Factory_IP = factory_IP;
	}

	public String getFactory_City() {
		return Factory_City;
	}

	public void setFactory_City(String factory_City) {
		Factory_City = factory_City;
	}

	public String getFactory_Loacation() {
		return Factory_Loacation;
	}

	public void setFactory_Loacation(String factory_Loacation) {
		Factory_Loacation = factory_Loacation;
	}

	public String getFactory_Description() {
		return Factory_Description;
	}

	public void setFactory_Description(String factory_Description) {
		Factory_Description = factory_Description;
	}
	public int getFactory_State() {
		return Factory_State;
	}
	public void setFactory_State(int factory_State) {
		Factory_State = factory_State;
	}
	public int getFactory_Owner() {
		return Factory_Owner;
	}
	public void setFactory_Owner(int factory_Owner) {
		Factory_Owner = factory_Owner;
	}
    

}
    

    
	
   
