# Plugin: custom-gatherer

Demonstrates using a plugin to create documents in gathering phase.

This plugin reads from `collection.cfg`, creates documents and adds some metadata to those documents.

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.custom-gatherer.enabled=true
plugin.custom-gatherer.version=1.0
```

The following `collection.cfg` settings can be used to configure the plugin:

* `plugin.custom-gatherer.number-of-documents-to-make`: Specify the number of documents that need to be created.
* `plugin.custom-gatherer.document-url`: Specify the base URL of the documents that should be generated.

An example to specify the number of documents that should be generated and the URLs of those documents.

```ini
plugin.custom-gatherer.number-of-documents-to-make=10
plugin.custom-gatherer.document-url=http://www.example.com/
```

Which will create 10 documents with the `http://example.com/<DOCUMENT_NUMER>` URL.