# Plugin: X-Forwarded-For Edit

Search Request Filter plugin to manipulate the X-Forwarded-For header of HTTP Search Requests.

Allows the removal of the first or last value of the X-Forwarded-For header, or removal of all values but the first.

Add the following to the Results Page (`profile.cfg`) to enable the plugin.

```ini
plugin.x-forwarded-for-edit.enabled=true
plugin.x-forwarded-for-edit.version=1.0.0
```

The following `collection.cfg` setting can be used to configure the plugin:

* `plugin.x-forwarded-for-edit.config.mode` Specifies the mode of operation. If not set, this plugin does nothing. Value may be one of:
  * `RemoveFirst` Remove first value of the X-Forwarded-For
  * `RemoveLast` Remove last value of the X-Forwarded-For
  * `KeepFirst` Keep only the first value of the X-Forwarded-For


For example, to keep only the first value of the X-Forwarded-For header for search requests:
```ini
plugin.x-forwarded-for-edit.config.mode=KeepFirst
```
which will result in the `X-Forwarded-For` header for a search request being modified like so:

```ini
X-Forwarded-For: 1.1.1.1,2.2.2.2 => X-Forwarded-For: 1.1.1.1
```

## Background

The X-Forwarded-For header contains a list of IP addresses of the various "hops" that the request
went through before reaching the Funnelback backend server.

In a complex scenario, a request can go through multiple hops:
1. Proxy server of the client, to the CMS
2. CMS to the Funnelback load balancer (partial HTML example)
3. Funnelback load balancer to the actual backend server

In this example, the X-Forwarded-For would have 3 values:
1. The IP address of the client proxy (1.2.3.4)
2. The IP address of the CMS (5.6.7.8)
3. The IP address of the load balancers (9.10.11.12)

```
X-Forwarded-For: 1.2.3.4,5.6.7.8,9.10.11.12
```

Another example is when Funnelback is put behind a Content Delivery Network such as Akamaï:
1. Client makes a request to the Funnelback domain name, that sends to Akamaï
2. Akamaï makes a request to Funnelback load balancers on behalf of the client
3. Load balancers forwards the request to the actual backend server

In all these cases it is desirable to only retain the IP address of the original client to ensure
accurate Analytics. Funnelback has partial support for this with `logging.ignored_x_forwarded_for_ranges`
that will cause the last IP address (load balancer → backend server) to be scrubbed. Multiple IP addresses
may remain however and there's no easy way to control which one should be kept.

For the example in the Akamaï case it would not be practical to configure all the possible IP addresses
of Akamaï (hundreds) to be ignored. A better approach instead is to simply ignore the last X-Forwarded-For
value and consider the one before last, as it will be the one of the client that issued the request to
Akamaï.

## Considerations

Note that:
- The first IP address in the X-Forwarded-For header will be the one closer to the client
- X-Forwarded-For should not be trusted for access restriction as it can be easily spoofed

## See Also

The following datasource and results page settings:
* access_restriction.prefer_x_forwarded_for
* logging.ignored_x_forwarded_for_ranges

