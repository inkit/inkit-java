The official Inkit Java client library.

## Installation

### Requirements

- Java 1.8 or later

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "com.inkit:inkit-java:0"
```

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.inkit</groupId>
  <artifactId>inkit-java</artifactId>
  <version>0</version>
</dependency>
```


## Usage

InkitExample.java

```java

import com.inkit.sdk;
import com.inkit.sdk.models.Render;
import java.util.Map;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Inkit.apiKey = "xxx";

        
        Map<String, Object> params = new HashMap<>();
        params.put("name", "java sdk test");
        params.put("html", "<html>hello there {{fname}}</html>");
        params.put("height", 11);
        params.put("width", 8.5);
        params.put("unit", "in");

        Map<String, Object> mergeParams = new HashMap<>();
        mergeParams.put("fname", "Bob");
        params.put("merge_parameters", mergeParams);

        try {
            Render render = Render.create(params);
            Render.retrievePdfAndSaveToFile(render.id, "your_pdf.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
```