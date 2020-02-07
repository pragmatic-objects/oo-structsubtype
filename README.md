# OO-Structsubtype

[![Travis](https://travis-ci.org/pragmatic-objects/oo-structsubtype.svg?branch=master)](https://travis-ci.org/pragmatic-objects/oo-structsubtype)
[![AppVeyor](https://ci.appveyor.com/api/projects/status/7we0mja0vs7kc9lh?svg=true)](https://ci.appveyor.com/project/skapral/oo-structsubtype)
[![Codecov](https://codecov.io/gh/pragmatic-objects/oo-structsubtype/branch/master/graph/badge.svg)](https://codecov.io/gh/pragmatic-objects/oo-structsubtype)

Annoation processor that enables declaring Java interfaces with imitation of structural subtyping support

## Quick start

1. Add maven dependency:

```xml
<dependency>
    <groupId>com.pragmaticobjects.oo.structsubtype</groupId>
    <artifactId>structsubtype-codegen</artifactId>
    <version>x.y.z</version>
</dependency>
```

2. Declare some interfaces:

```java
interface UserName {
    String userName();
}

interface UserAddress {
    String zip();
    String address();
}
```

3. Place `@StructSubtype` declaration to the `package-info.java` of the package where you want the subtype to be generated

```java
@StructSubtype(name = "User", inherits = { UserName.class, UserAddress.class }
package com.example;

import com.pragmaticobjects.oo.structsubtype.api.StructSubtype;
```

4. Build the project. A source for new type will be generated at the annotated package. The type will be substitutable at each of it's testators.

```java
interface User extends UserName, UserAddress {}
```

Note that generated interfaces within one package may be subtypes of each other, if one inherits all features of the other:

```java
@StructSubtype(name = "Recipient", inherits = { UserName.class, UserAddress.class }
@StructSubtype(name = "PassportIdentity", inherits = { UserName.class, PassportId.class }
@StructSubtype(name = "User", inherits = { UserName.class, PassportId.class, UserAddress.class  }
package com.example;
```

```java
// Since `User` inherits every feature of `Recipient` and `PassportIdentity`, it is considered as a subtype of them.
interface User extends UserName, PassportId, UserAddress, Recipient, PassportIdentity {}
```

