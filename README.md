# crypto-dsl
A project to play with the idea of providing a easy to use DSL in Groovy to express the various transformations 
a blob of binary data must go through before it gets encrypted.

Typical transformations include:
  - Padding 
  - Convert to hex string (or vice versa)
  - Base64 encoding

This DSL is meant to be used in an application that `Asset` objects that have any number of `Key` objects. Each `Key` can be of any type. 
A Groovy DSL would look like this:

```groovy
//the current asset is made available by the framework in the asset variable

format(asset.keys["keytype"])
   .toBytes()
   .pad(16, "0")
   .encrypt(AES128CBC)
   .build()
```

The resulting `Key` object will be made available to the caller.
