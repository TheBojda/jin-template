jin-template is a really simple template engine, it is using pure HTML templates for source, and Java or PHP code to build the result.

Most of the template engines using special entities inside the template to define view building logic (selections, iterations, etc.). JSP uses raw Java code, or JSTL, Velocity or Smarty also uses special expressions, etc. The aim of jin-template is to separate the view building logic from the view, and keep the pure HTML on the template side. The solution of the jin-template is very easy: define variable blocks in the HTML (or XML) with comments, and use these blocks form the code.

jin-template uses only one entity in the HTML code, this entity is variable. You can define variables with `<!--{variable: name}-->...<!--{/variable}-->`, or the `${name`} sort form.

Look at a template example, which uses both of the two form:

```
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

```
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

```
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
> http://code.google.com/p/jin-plugin/ - simple plugin framework for Java and PHP

> http://code.google.com/p/jin-webcore/ - minimalistic web framework for Java and PHP

&lt;wiki:gadget url="http://www.ohloh.net/p/486105/widgets/project\_users\_logo.xml" height="43" border="0"/&gt;

<a href='https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=LMQGC6YTEQKE4&item_name=Beer'>
<img src='http://www.paypal.com/en_US/i/btn/x-click-but04.gif' /><br />Buy me some beer if you like my code ;)</a>

If you like the code, look at my other projects on http://code.google.com/u/TheBojda/.

If you have any question, please feel free to contact me at thebojda AT gmail DOT com.