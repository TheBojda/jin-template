package com.estontorise.template.blocks;

public interface Block {

	public String render();
	public Block createCopy();

}
