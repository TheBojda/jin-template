package com.estontorise.template;

import java.io.IOException;
import java.io.InputStream;

import com.estontorise.template.interfaces.Template;

public class TemplateFactory {

	public static Template createTemplate(InputStream is) throws IOException {
		return new TemplateImpl(is);
	}
	
}
