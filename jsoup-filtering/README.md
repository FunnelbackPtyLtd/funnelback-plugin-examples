# Plugin: jsoup-filtering

Enabled this by adding it to the Jsoup filter chain as well as enabling the plugin. 
Example shows reading from configuration and setting metadata.

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.jsoup-filtering.enabled=true
plugin.jsoup-filtering.version=1.0
```

Add the following to your `collection.cfg` to define the regex pattern:

```ini
plugin.jsoup-filtering.class-to-count=<STRING>
```

e.g.

```ini
plugin.jsoup-filtering.class-to-count=cities
```

will count how many classes that have `cities` and will set that number in `my-class-count` metadata.