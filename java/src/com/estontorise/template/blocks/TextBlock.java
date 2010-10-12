package com.estontorise.template.blocks;

public class TextBlock implements Block {

	private String content;

	public TextBlock(String content) {
		this.content = content;
	}

	@Override
	public String render() {
		return this.content;
	}

	@Override
	public Block createCopy() {
		return new TextBlock(content);
	}

}
