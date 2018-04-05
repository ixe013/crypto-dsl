#!env groovy
println asset.name
def x = engine.someProperty
engine.someMethod(x, 42, "testing...")
//Composition of non-existing method
engine.format(asset, key)
   .pad(16, "0")
   .hex("two")
   .base64Encode("three")
   .save()
