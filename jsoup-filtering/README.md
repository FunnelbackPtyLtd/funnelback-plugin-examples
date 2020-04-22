# Plugin: jsoup-filtering

Demonstrates using a plugin to count number of elements which contain the specified class name.

Enabled this by adding it to the Jsoup filter chain as well as enabling the plugin. 
Example shows reading from configuration and setting metadata.

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.jsoup-filtering.enabled=true
plugin.jsoup-filtering.version=1.0
```

The following `collection.cfg` settings can be used to configure the plugin:

* `plugin.jsoup-filtering.class-to-count=<STRING>` : Name of the class which needs to be counted.

An example to specify which class which needs to be counted.

```ini
plugin.jsoup-filtering.class-to-count=cities
```

Which will count how many elements that have the class name `cities` and will set that number in `my-class-count` metadata.