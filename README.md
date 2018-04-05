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
    //Presets of algorithm, mode, optional padding, iv, etc.
   .encrypt(SOME_PRESET) 
   //By default, the results are made available to the caller as `key`
   .save()               
```

The resulting `Key` object will be made available to the caller.

If a key needs to be encrypted with the key of another asset, you can look for it using the `Repository` object, and feed it to the pipeline using the `with` construct. 
The following example shows that, as well as the ability to produce multiple keys in a single scrirpt:

```groovy
//The asset repository is available as the `repository` object

kek = repository.find("master asset")

//Maybe the key encryption key (kek) must also be encrypted
format(key)
   .toBytes()
   .encrypt(ROT13_PRESET)
   //You can specify another name under which the call retreives the result
   .save("kek")

format(asset.keys["keytype"])
   .with("key encryption key", kek)
   .toBytes()
   .pad(16, "0")
   .encrypt(AES128CBC)
   //You can specify another name under which the call retreives the result
   .save()
```

