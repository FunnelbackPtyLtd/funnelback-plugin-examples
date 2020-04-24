# Plugin: title-prefix-plugin

This plugin cleans search result titles by replacing the start of a title which matches a pattern with a provided pattern. If a replacement is not provided, it will remove the matching part of the title.

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.title-prefix-plugin.enabled=true
plugin.title-prefix-plugin.version=1.0
```

Add the following to your `collection.cfg` or `profile.cfg` to define the pattern:

```ini
plugin.title-prefix-plugin.pattern=<Pattern to look for at the start of the title>
```

You may optionally supply a second pattern to replace the first one with:
```ini
plugin.title-prefix-plugin.replaceWith=<Text to replace the pattern with>
```

e.g.

```ini
plugin.title-prefix-plugin.pattern=Product Documentation |
plugin.title-prefix-plugin.replaceWith=Funnelback Documentation v16 |
```

will replace occurrences of 'Product Documentation |' with 'Funnelback Documentation v16' in any search result titles 
(`result.title`).