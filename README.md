# TeamCast
Simple Java app for distributing cross-platform announcements via webhooks

To build, on a system with Java 11 and Maven installed:

```
mvn clean install
```

To execute:
```
java -jar target/TeamCast-*-standalone.jar
```

To use:

* Set up incoming webhooks for your target channels in whatever combination of Slack, MS Teams, and Discord you need. Keep your webhook URLs in a safe place!
* When you need to broadcast a message, open TeamCast, and compose your message. The pane on the right side of the UI will render an approximation of what your post will look lke as you type. You can use Markdown in the body field, but keeping it simple is recommended. 
* Once the message is crafted, click the button in the very bottom-right corner of the UI.
* Into the resultant dialog, paste the set of webhook URLs you want to target, and click OK.
* The application will choose the right message format for each webhook, and post them as quickly as possible to each channel in order.
