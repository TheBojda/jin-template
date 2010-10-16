<?php

	require_once "blocks/TextBlock.class.php";
	require_once "blocks/VariableBlock.class.php";

	class TemplateImpl {
		
		public static function constructFromFile($file) {
			$block = TemplateImpl::parse(file_get_contents($file));
			return new TemplateImpl($block);
		} 
		
		public static function constructFromBlock($block) {
			return new TemplateImpl($block);
		}
		
		public function __construct($block) {
			$this->mainBlock = $block->createCopy();
		}
		
		private static function parse($source) {
			$source = preg_replace('/\$\{([a-zA-Z_\/\[\]\(\)]*)\}/', '<!--{variable: $1}--><!--{/variable}-->', $source);
			$container = new VariableBlock("_main");
			$blockStack = array();
			array_push($blockStack, $container);
			$pos = TRUE; $lastPos = 0;
			while ($pos) {
				$pos = strpos($source, "<!--{", $lastPos);
				if(!$pos)
					continue;
				$frag = substr($source, $lastPos, $pos-$lastPos);
				$pos +=5;
				$container->addBlock(new TextBlock($frag));
				$lastPos = $pos;
				$pos = strpos($source, "}-->", $lastPos);
				$frag = substr($source, $lastPos, $pos-$lastPos);
				$pos += 4;
				$blockContent = trim($frag);
				if (substr($blockContent, 0, 1) == '/') {
					array_pop($blockStack);
					$container = end($blockStack);
				} else{
					$frags = split(" ", $blockContent);
					$blockContent = $frags[1];
					$block = new VariableBlock($blockContent);
					$container->addBlock($block);
					array_push($blockStack, $block);
					$container = $block;
				}
				$lastPos = $pos;
			}
			$frag = substr($source, $lastPos, strlen($source)-$lastPos);
			$container->addBlock(new TextBlock($frag));
			return $container;
		}
		
		public function getTemplateByName($name) {
			$block = $this->recursiveSearch($this->mainBlock, $name);
			return new TemplateImpl($block);
		}
		
		private function recursiveSearch($container, $name) {
			foreach($container->blocks as $block) {
				if($block instanceof VariableBlock) {
					if($block->getName() == $name)
						return $block;
					$block = $this->recursiveSearch($block, $name);
					if($block)
						return $block;
				}
			}
			return FALSE;
		}
		
		public function setVariable($name, $value) {
			if(is_array($value)) {
				$content = '';
				foreach($value as $t) {
					$content .= $t->render();
				}
				$this->setVariable($name, $content);
			}
			else if($value instanceof TemplateImpl) {
				$this->setVariable($name, $value->render());
			} 
			else {
				$blocks = array();
				$block = $this->recursiveSearch($this->mainBlock, $name);
				$blocks[] = new TextBlock($value);
				$block->setBlocks($blocks);
			}
		}
		
		public function render() {
			$result = '';
			foreach($this->mainBlock->blocks as $b) {
				$result .= $b->render();
			}
			return $result;
		}
		
		public function createCopy() {
			return new TemplateImpl($this->mainBlock);
		}
	}
	
?>