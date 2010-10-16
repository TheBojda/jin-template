<?php
	
	class VariableBlock {
				
		public function __construct($name) {
			$this->variableName = $name;
			$this->blocks = array();
		}
				
		public function setBlocks($blocks) {
			$this->blocks = $blocks;
		}
		
		public function addBlock($block) {
			$this->blocks[] = $block;
		}
		
		public function createCopy() {
			$result = new VariableBlock($this->variableName);
			$new_blocks = array();
			foreach($this->blocks as $b)
				$new_blocks[] = $b->createCopy();
			$result->setBlocks($new_blocks);
			return $result;
		}
		
		public function getName() {
			return $this->variableName;
		}
		
		public function render() {
			$result = '';
			foreach($this->blocks as $b)
				$result .= $b->render();
			return $result;
		}
		
	}
	
?>