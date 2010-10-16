<?php

	class TextBlock {
		
		public function __construct($content) {
			$this->content = $content;
		}
		
		public function render() {
			return $this->content;
		}
		
		public function createCopy() {
			return new TextBlock($this->content);
		}
		
	}
	

?>