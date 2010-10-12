package com.estontorise.template.blocks;

import java.util.ArrayList;
import java.util.List;

public class VariableBlock implements Block {

	private List<Block> blocks = new ArrayList<Block>();
	private String variableName;
	
	public VariableBlock(String variableName) {
		this.variableName = variableName;
	}

	public List<Block> getBlocks() {
		return blocks;
	}
	
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public VariableBlock createCopy() {
		VariableBlock result = new VariableBlock(variableName);
		List<Block> new_blocks = new ArrayList<Block>();
		for(Block b : blocks)
			new_blocks.add(b.createCopy());
		result.setBlocks(new_blocks);
		return result;
	}

	public String getName() {
		return variableName;
	}

	@Override
	public String render() {
		StringBuffer sb = new StringBuffer();
		for(Block b : blocks)
			sb.append(b.render());
		return sb.toString();
	}

}
