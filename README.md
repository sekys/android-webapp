# Android WebApp
Run RESTEasy endpoints (annnotated by JAX-RS) at Android enviroment.
We are intercepting Web-view (browser) requests, dispatching it to the endpoints. 
So there is no need for application server.

## How is it possible?
Android 5.1 web-view has support for intercepting request, but only for GET requests.
We bring ApiClient (javascript Ajax wrapper) for intercepting POST requests.
POST requests are submitted by WebAppInterface, serialized by JacksonXML and handled again in endpoint.

## Custom interceptors
- AjaxProxyInterceptor - intercept Ajax requests for non-localhost target
- CustomCacheResolver - intercept and cache request's body (useful for images)
- LoadStaticResources - intercept and redirect request to Android assets (useful for static files in webdir)
- ServerOriginInterceptor - intercept and redirect local request to RESTEasy dispatcher

## RESTEasy providers
HtmlBodyProvider - provider for HTML annotated endpoint, loads HTML file from assets dir
JsonBodyProvider - serialize/deserialize method parameters from request body
QueryParamInjector - serialize/deserialize method parameters from request get parameters

## JAX-RS support
REST endpoints can be annotatted with any annotation.
But, there is standard JAX-RS. We import implementation from RESTEasy.

## Custom session
To simulate user's session at Adnroid Backend we implement JsonFileStorage and MemoryStorage.

## Preloader
There is native preloader. It is visible until everything (Web-View, Android BE, custom initializations) is prepared and loaded.
