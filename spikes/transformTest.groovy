#!env groovy
println asset.name

/* 
This call fails. engine needs to be Closure created on the Java side

   encryptedKey = engine.save()

The closure will look like this:
    t = new TransformationEngine(currentAsset, currentKey);
    closure = makeClosureOutOf(t);

The real work will be in makeClosureOutOf(t), which I don't know how to do yet,
although this looks like the right way https://stackoverflow.com/a/32297842/591064
//*/

def zzz = encryptedKey = 
    engine.format(asset, key)
       .append("00")
       //.pad(16, "0")
       //.hex("two")
       //.base64Encode("three")
       .save()

//This is blocked by the sandbox filter
//def s = System
//s.exit(-1)

println asset.name

