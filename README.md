## Setup

Create a `fabric.properties` file in the app directory and set the following
variable using your Fabric keys.

```
apiSecret=
apiKey=
```

Create a `secrets.xml` in you `values` resource directories containing:

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="mixpanel_token"></string>
</resources>
```