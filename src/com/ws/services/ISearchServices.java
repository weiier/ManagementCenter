package com.ws.services;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.jws.WebService;
import org.ow2.aspirerdfid.tracking.demo.db.Item;


@WebService
public interface ISearchServices {
	//�������� ������ؼ��ַ��������б�,""��������
	public abstract List<Item> getMaterials(String keyword) throws UnsupportedEncodingException;
	//
	public abstract List<Item> getMaterial();
}
