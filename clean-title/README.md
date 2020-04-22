# Plugin: clean-title

Demonstrates using a plugin to clean the search result titles.

This plugin cleans search result titles by removing sections of the title that match a regular expression supplied in 
the `collection.cfg`

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.clean-title.enabled=true
plugin.clean-title.version=1.0
```

The following `collection.cfg` settings can be used to configure the plugin:

```ini
plugin.clean-title.regex-pattern=<REGEX PATTERN>
```

An example to specify regex pattern in `collection.cfg` to match a section which needs to be removed from the title.

```ini
plugin.clean-title.regex-pattern=(- Funnelback Documentation - Version )?\d+\.\d+\.\d+(-SNAPSHOT)?
```

Which will remove occurrences of 'Funnelback documentation: ' and ' Funnelback version numbers' from any search result titles 
(`result.title`).