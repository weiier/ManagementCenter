package com.ws.services;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.jws.WebService;
import org.ow2.aspirerdfid.tracking.demo.db.Item;


@WebService
public interface ISearchServices {
	//物资搜索 ，传入关键字返回物资列表,""返回所有
	public abstract List<Item> getMaterials(String keyword) throws UnsupportedEncodingException;
	//
	public abstract List<Item> getMaterial();
}
