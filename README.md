#### Bot for telegram, allowing simple sending messages to predefined group/channel
* First, register telegram bot from @BotFather and get bot token and username. 
* Second - add bot to desired chat/group 
and get him admin permissions. 
* Third - get chatId (e.g., calling "https://api.telegram.org/bot${token}/getUpdates").

That's all you need. Attention: requires Java 11.
 
##### How to run:

./mvnw clean package

java -jar target/ci-bot-mq-0.0.1-SNAPSHOT.jar \
--bot.token="bot token here" \
--bot.chatId="chat - group or channel - id here" \
--bot.name="bot user name here" \
--spring.boot.admin.client.username="admin name here" \
--spring.boot.admin.client.password="admin password here" 

#####Implemented REST API:
######Send message to bot.chatId group or channel
`POST /api/message body:
{
  "message": "Some text here"
}`
######curl example: 
`curl -XPOST -u username:password -H "Content-type:application/json" -d '{ "message": "Hello, world!" }' http://localhost:8081/api/message` 