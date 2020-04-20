# Plugin: custom-gatherer

This example shows how to use the `CustomGatherer` plugin to read the configuration settings 
from `collection.cfg` , create documents and adding some metadata to those documents.

Number of documents that should be generated and the URLs of those documents can be supplied as `collection.cfg` settings.

## Usage

Add the following to your `collection.cfg` to enable the plugin.

```ini
plugin.custom-gatherer.enabled=true
plugin.custom-gatherer.version=1.0
```

Add the following to your `collection.cfg` to define the regex pattern:

```ini
plugin.custom-gatherer.number-of-documents-to-make=<INTEGER>
plugin.custom-gatherer.document-url=<URL>
```

e.g.

```ini
plugin.custom-gatherer.number-of-documents-to-make=10
plugin.custom-gatherer.document-url=http://www.example.com/
```

will create 10 documents with the `http://example.com/<DOCUMENT_NUMER>` URL.