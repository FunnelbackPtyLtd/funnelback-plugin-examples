# Plugin: clean-title

This example explains how we can use the `SearchLifeCycle` plugin to modify the title of a document 
during the `postDataFetch` phase. The title of the documents will be modified after gathering finished.

This plugin cleans search result titles by removing sections of the title that match a regular expression supplied in 
the collection.cfg

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.clean-title.enabled=true
plugin.clean-title.version=1.0
```

Add the following to your `collection.cfg` to define the regex pattern:

```ini
plugin.clean-title.regex-pattern=<REGEX PATTERN>
```

e.g.

```ini
plugin.clean-title.regex-pattern=(- Funnelback Documentation - Version )?\d+\.\d+\.\d+(-SNAPSHOT)?
```

will remove occurrences of 'Funnelback documentation: ' and ' Funnelback version numbers' from any search result titles 
(`result.title`).