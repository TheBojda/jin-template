package com.estontorise.template.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.estontorise.template.TemplateFactory;
import com.estontorise.template.interfaces.Template;

public class TemplateTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Template t = TemplateFactory.createTemplate(new FileInputStream("test.html"));
		Template source_item = t.getTemplateByName("item");
		String[] values = new String[]{"Foo", "Bar"};
		List<Template> items = new ArrayList<Template>();
		for(String value : values) {
			Template item = source_item.createCopy();
			item.setVariable("name", value);
			items.add(item);
		}
		t.setVariable("item", items);
		System.out.println(t.render());
	}

}
