# Plugin: additional-metadata

Demonstrates using a plugin to supply additional metadata mappings.

Also shows a plugin reading from collection.cfg, in this case to change the metadata 
mappings supplied.

## Usage

Add the following to `collection.cfg` to enable the plugin.

```ini
plugin.additional-metadata.enabled=true
plugin.additional-metadata.version=1.0
```

The following `collection.cfg` settings can be used to configure the plugin:

* `plugin.additional-metadata.metadata`: Defines an additional HTML <meta> 'name' to map to the 'authors' metadata class.


For example to also map the content of HTML meta tags with `name='publishers'` to metadata class authors add to collection.cfg:


```ini
plugin.additional-metadata.metadata=publishers
```

which will result in the content of tags like:

```
<meta name='publishers' content="...">
```

being mapped and indexed into metadata class 'authors'. This will be available to the query processor.
