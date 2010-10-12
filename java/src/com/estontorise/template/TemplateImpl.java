package com.estontorise.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.estontorise.template.blocks.Block;
import com.estontorise.template.blocks.TextBlock;
import com.estontorise.template.blocks.VariableBlock;
import com.estontorise.template.interfaces.Template;

public class TemplateImpl implements Template {

	private VariableBlock mainBlock = new VariableBlock("_main");
	
	public TemplateImpl(InputStream is) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		String content = fileData.toString();
		parse(content);
	}

	public TemplateImpl(VariableBlock block) {
		this.mainBlock = block.createCopy();
	}

	private void parse(String source) {
		source = source.replaceAll("\\$\\{([a-zA-Z_\\/\\[\\]\\(\\)]*)\\}", "<!--{variable: $1}--><!--{/variable}-->");
		VariableBlock container = this.mainBlock;
		Stack<VariableBlock> blockStack = new Stack<VariableBlock>();
		blockStack.add(container);
		int pos = 0, lastPos = 0;
		String frag;
		while (pos > -1) {
			pos = source.indexOf("<!--{", lastPos);
			if (pos < 0)
				continue;
			frag = source.substring(lastPos, pos);
			pos += 5;
			container.getBlocks().add(new TextBlock(frag));
			lastPos = pos;
			pos = source.indexOf("}-->", lastPos);
			frag = source.substring(lastPos, pos);
			pos += 4;
			String blockContent = frag.trim();
			if (blockContent.startsWith("/")) {
				blockStack.pop();
				container = blockStack.peek();
			} else {
				String[] frags = blockContent.split(" ");
				blockContent = frags[1];
				VariableBlock block = new VariableBlock(blockContent);
				container.getBlocks().add(block);
				blockStack.push(block);
				container = block;
			}
			lastPos = pos;
		}
		frag = source.substring(lastPos, source.length());
		container.getBlocks().add(new TextBlock(frag));		
	}

	@Override
	public Template getTemplateByName(String name) {
		VariableBlock block = recursiveSearch(mainBlock, name);
		return new TemplateImpl(block);
	}

	private VariableBlock recursiveSearch(VariableBlock container, String name) {
		for(Block b : container.getBlocks()) {
			if(b instanceof VariableBlock) {
				VariableBlock v = (VariableBlock)b;
				if(v.getName().equals(name))
					return v;
				v = recursiveSearch(v, name);
				if(v != null)
					return v;
			}
		}
		return null;
	}

	@Override
	public void setVariable(String name, String value) {
		List<Block> blocks = new ArrayList<Block>();
		VariableBlock block = recursiveSearch(mainBlock, name);
		block.setBlocks(blocks);
		blocks.add(new TextBlock(value));
	}

	@Override
	public void setVariable(String name, Template template) {
		setVariable(name, template.render());
	}

	@Override
	public void setVariable(String name, List<Template> templates) {
		StringBuffer sb = new StringBuffer();
		for(Template t : templates) {
			sb.append(t.render());
		}
		setVariable(name, sb.toString());
	}

	@Override
	public String render() {
		StringBuffer sb = new StringBuffer();
		for(Block b : mainBlock.getBlocks()) {
			sb.append(b.render());
		}
		return sb.toString();
	}

	@Override
	public Template createCopy() {
		return new TemplateImpl(mainBlock);
	}

}
