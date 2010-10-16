<?php
include "lib/jin_template/TemplateFactory.class.php";

$t = TemplateFactory::createTemplate('test.html');
$source_item = $t->getTemplateByName('item');
$values = array('Foo', 'bar');
$items = array();
foreach($values as $value) {
	$item = $source_item->createCopy();
	$item->setVariable('name', $value);
	$items[] = $item;
}
$t->setVariable('item', $items);
echo $t->render();
?>
