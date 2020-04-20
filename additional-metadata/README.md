# Plugin: additional-metadata

This example explains how we can use the `Indexing` plugin to add some additional metadata
The additional metadata value must be provided as a `collection.cfg` parameter.

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.additional-metadata.enabled=true
plugin.additional-metadata.version=1.0
```

Add the following to your `collection.cfg` to define the regex pattern:

```ini
plugin.additional-metadata.metadata=<STRING>
```

e.g.

```ini
plugin.additional-metadata.metadata=author
```

will add `author` metadata.