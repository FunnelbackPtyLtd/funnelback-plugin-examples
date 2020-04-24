<sub>Note: This plugin is explained in detail as part of the 'Developing your first plugin' guide in the Funnelback product documentation. 
The code and test files may have additional line markers e.g. `//A` or `//1` to help refer to them within the documentation. These comments might not make sense outside of the context for which they were written.</sub> 

# Plugin: title-prefix-plugin

This plugin cleans search result titles by replacing the start of a title which matches a pattern with a provided pattern. If a replacement is not provided, it will remove the matching part of the title.

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.title-prefix-plugin.enabled=true
plugin.title-prefix-plugin.version=1.0
```

The following `collection.cfg` or `profile.cfg` settings can be used to configure the plugin:
* `plugin.title-prefix-plugin.pattern`: Pattern to look for at the start of each result title
* `plugin.title-prefix-plugin.replaceWith`: (Optional) Text to replace the pattern with

If `replaceWith` is not provided, then matching patterns will be removed.

For example, if you were to set the following settings in `collection.cfg`:

```ini
plugin.title-prefix-plugin.pattern=Product Documentation |
plugin.title-prefix-plugin.replaceWith=Funnelback Documentation v16 |
```

On any search results for that collection, titles which begin with `'Product Documentation |'` will now start with `'Funnelback Documentation v16'` instead. 
