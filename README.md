jin-template is a really simple template engine, it is using pure HTML templates for source, and Java or PHP code to build the result. 

Most of the template engines using special entities inside the template to define view building logic (selections, iterations, etc.). JSP uses raw Java code, or JSTL, Velocity or Smarty also uses special expressions, etc. The aim of jin-template is to separate the view building logic from the view, and keep the pure HTML on the template side. The solution of the jin-template is very easy: define variable blocks in the HTML (or XML) with comments, and use these blocks form the code.

jin-template uses only one entity in the HTML code, this entity is variable. You can define variables with {{{<!--{variable: name}-->...<!--{/variable}-->}}}, or the {{{${name}}}} sort form.

Look at a template example, which uses both of the two form:

```html
<html>
	<body>
		SAMPLE:<br/>
		<!--{variable: item}-->
			Hello ${name}!<br/>
		<!--{/variable}-->
	</body>
<html>
```

The another component is the template building logic, which is defined as code. This separation gives you maximal freedom, and help to make beautiful code. In code you can mix different templates, and reuse the blocks. 

Look at a simple example in Java:

```java
Template t = TemplateFactory.createTemplate(new FileInputStream("test.html"));
Template source_item = t.getTemplateByName("item");
String[] values = new String[]{"Foo", "Bar"};
List<Template> items = new ArrayList<Template>();
for(String value : values) {
    Template item = source_item.createCopy();
    item.setVariable("name", value);
    items.add(item);
}
t.setVariable("item", items);
System.out.println(t.render());
```

... and the same in PHP:

```php
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
```

Small, nice, and easy ...  

See also:

[jin-plugin](http://code.google.com/p/jin-plugin/) - simple plugin framework for Java and PHP

[jin-webcore](http://code.google.com/p/jin-webcore/) - minimalistic web framework for Java and PHP
