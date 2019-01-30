all: ChatServer.jar

ChatServer.jar: ChatServer.class MainThread.class ClientThread.class
	jar cvfe $@ ChatServer $^

%.class: %.java
	javac $<

clean:
	rm -f *.class
	rm -f ChatServer.jar
