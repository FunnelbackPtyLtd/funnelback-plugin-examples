# Plugin: mock-gathering

This plugin demonstrates mocking out the part of a plugin gatherer 
which makes HTTP calls to a remote web server. In this way, this shows
how it is possible to test the gatherer (or part of it) without needing a 
remote web server setup.

The plugin gather class is `MockGatheringPluginGatherer` and it uses a `RemoteFetcher` to
fetch remote content. By default the gather uses: `RealRemoteFetcher` to make HTTP
web requests to fetch document. Our test is going to show how to replace the 
`RemoteFetcher` used. So instead of using the `RealRemoteFetcher`, our test uses a fake
implementation (a mock) which doesn't sent HTTP requests. This allows us to still test parts
of our gather such as the `FetchedResultParser` which parses the result of the
web request as well as other logic without needing a remote web server.



Add the following to `collection.cfg` to enable the plugin.

```ini
plugin.mock-gathering.enabled=true
plugin.mock-gathering.version=1.0-SNAPSHOT
```

The following `collection.cfg` settings can be used to configure the plugin:

* `plugin.mock-gathering.security-token`: Whatever is set here will be sent to the web server in the HTTP header `security-token`
* `plugin.mock-gathering.start-urls`: A space separated list of URL to start crawling from.


The plugin expects to crawl JSON pages which look like:

```
{
  "content" : "the document content",
  "next": "https://the.next/url/to/fetch"
}
```


