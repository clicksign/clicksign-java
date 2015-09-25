# clicksign-java

Java wrapper for [Clicksign](http://clicksign.com) [REST API](http://clicksign.readme.io).

Requirements
------------

* Java 1.5+
* [Google Gson](http://code.google.com/p/google-gson/) from <http://google-gson.googlecode.com/files/google-gson-2.2.4-release.zip>.
* [Apache HTTPClient](https://hc.apache.org/httpcomponents-client-4.5.x/index.html) from <http://ftp.unicamp.br/pub/apache//httpcomponents/httpclient/binary/httpcomponents-client-4.5.1-bin.zip>.
* [Apache HTTPCore](https://hc.apache.org/httpcomponents-core-4.4.x/index.html) from <http://mirror.nbtelecom.com.br/apache//httpcomponents/httpcore/binary/httpcomponents-core-4.4.3-bin.zip>.

Installation
------------

```sh
mvn package
```

or build the jar from src!

## Usage

### Setting up the client

You must provide a valid `token` in order to use the library. As an option, you can also set a different `endpoint`.

The required `token` is provided by the Clicksign support team.

```java
import com.clicksign.Clicksign;

...

Clicksign.accessToken = "ac96303488bc525fa9df2fb65e1d45fc";
Clicksign.endpoint = 'https://api.clicksign-demo.com' # Default: 'https://api.clicksign.com'
```

### Retrieving a list of documents

You'll be able to make requests to the Clicksign API right after the initial setup. The first step would be to retrieve a list of documents that you've previously uploaded to your account.

```java
import com.clicksign.models.Document;
import com.clicksign.models.DocumentCollection;

...

DocumentCollection documents = Document.all();
```

### Uploading a document

To upload a new document to Clicksign you can use the following snippet:

```java
import com.clicksign.models.Document;

...

Document document = Document.create(new File('example.pdf'));
```

You can also upload a new document and at the same time set up a signature list
as follow:

```java
import com.clicksign.models.Document;
import com.clicksign.models.Signature;

...

List<Signature> signers = new ArrayList<Signature>();
signers.add(new Signature("john.doe@example.com", "sign"));

String message = "Please sign it";
Boolean skipEmail = true;

Document document = Document.create(File.new('example.pdf'), signers, message, skipEmail);

```

It is important to notice that the additional parameters to `create` method are
the same of the ones in the section [Creating a signature list](#user-content-creating-a-signature-list)
