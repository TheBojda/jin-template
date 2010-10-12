package com.estontorise.template.interfaces;

import java.util.List;

public interface Template {

	public Template createCopy();
		
	public Template getTemplateByName(String name);
	
	public void setVariable(String name, String value);
	public void setVariable(String name, Template value);
	public void setVariable(String name, List<Template> value);

	public String render();
		
}
