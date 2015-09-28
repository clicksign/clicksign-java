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
### Exception Handling

All API methods throw a _com.clicksign.exception.ClicksignException_ if something unexpected happen.

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
import java.util.ArrayList;
import java.util.List;
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


### Retrieving a document

```java
import com.clicksign.models.Document;

...

Document found = Document.find(documentKey);
```

### Downloading a document

```java
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import com.clicksign.models.Document;

...
String key = "document_key";
InputStream inputStream = Document.download(key);
if (inputStream != null) {
	Path newFile = FileSystems.getDefault().getPath("mydoc.zip");
	Files.copy(inputStream, newFile);
}
```

If _inputStream_ is _null_, it means that the server is preparing the zip file.
When the zip is ready, its contents are retrieved.

### Creating a signature list

The method `Document.createList()` accepts **3 arguments**.

The first argument is `documentKey`, which represents the document's unique identification.

The second argument is `signers`, an ```List<Signature>``` with the e-mails of the signers and their actions (`act`). The available options for `act` are described in our [documentation](http://clicksign.github.io/rest-api/#criacao-de-lista-de-assinatura).

The third and optional parameter is `skipEmail`, a boolean that says whether the API should send e-mails to the signers or not.

Example:

```java
import java.util.ArrayList;
import java.util.List;
import com.clicksign.models.Document;
import com.clicksign.models.Signature;

...

String key = "document_key";
List<Signature> signers = new ArrayList<Signature>();
signers.add(new Signature("john.doe@example.com", "sign"));
Document doc = Document.createList(key, signers, true);
```

### Resending a signature request

Use the following snippet to send a email to a signer that have not signed yet:

```java
import com.clicksign.models.Document;

...

String key = "document_key";
String message = "This is a reminder for you to sign the document.";
String email = "john.nash@example.com";
Document.resend(key, email, message);
```

### Canceling a document

```java
import com.clicksign.models.Document;

...

String key = "document_key";
Document doc = Document.cancel(key);
```

The method returns the canceled document.

### Hooks

You can perform three different actions with hooks: **retrieve** all, **create** a new one or **delete** an existing hook.

Listing all hooks that belong to a document:

```java
import com.clicksign.models.Hook;
import com.clicksign.models.HookCollection;

...

String key = "document_key";
HookCollection collection = Hook.all(key);
```

Creating a new hook for a specific document:

```java
import com.clicksign.models.Hook;

...

String key = "document_key";
String url = "http://example.com";
Hook hook = Hook.create(key, url);
```

Destroying an existing hook:

```java
import com.clicksign.models.Hook;

...

String key = "document_key";
String hookId = "42";
Hook.delete(key, hookId);
```

### Batches

Batches are you used to group documents in a package and perform batch signatures via the [Clicksign Widget](https://github.com/clicksign/widget).

You can perform three different actions with batches: **retrieve** all, **create** a new one or **delete** an existing batch.

Listing all batches:

```java
import com.clicksign.models.Batch;
import com.clicksign.models.BatchCollection;

...

BatchCollection collection = Batch.all();
```

Creating a new batch for a group of documents:

```java
import java.util.ArrayList;
import java.util.List;
import com.clicksign.models.Batch;

...

List<String> keys = new ArrayList<String>();
keys.add("my_document1");
keys.add("my_document2");
keys.add("my_document3");
Batch batch = Batch.create(keys);
```

Destroying an existing batch:

```java
import com.clicksign.models.Batch;

...

String key = "batch_key";
Batch.delete(key);
```