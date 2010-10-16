<?php
	require_once "TemplateImpl.class.php";

	class TemplateFactory {
		public static function createTemplate($file) {
			return TemplateImpl::constructFromFile($file);
		}
	}

?>