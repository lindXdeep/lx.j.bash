SHELL:=/bin/bash -O globstar   # tell make to use bash.

.PHONY: all clean test         # phony target - is not name of a file, it is a name for a recipe to be executed when you make an explicit request.

.DEFAULT_GOAL:= compile-run

# ----- CLEAN ----- #

clean-test:
	find out/ -name "*Test.class" -exec rm {} \;

clean:
	rm -r out/* bin/* &

# ----- COMPILE ----- #

compile: clean
	javac -d out/ -cp .:lib/* -sourcepath src/ src/main/**/*.java

compile-jdk8: clean
	${JDK8_HOME}/bin/javac -d out/ -cp .:lib/* -sourcepath src/ src/main/**/*.java

# ----- TEST ----- #

test: compile
	javac -d out/ -cp .:out/:${JUNIT5_HOME}/junit-platform-console-standalone-1.8.2.jar src/**/*.java
	java -jar ${JUNIT5_HOME}/junit-platform-console-standalone-1.8.2.jar --class-path out/ --scan-class-path

# ----- PACKAGE ----- #

jar:
	cp src/main/resources/* out/
	jar cvfe bin/app.jar lx.lindx.bash.App -C out/ .

package: compile jar

jar-jdk8:
	cp src/main/resources/* out/
	${JDK8_HOME}/bin/jar cvfe bin/app.jar lx.lindx.bash.App -C out/ .

package-jdk8: compile-jdk8 jar-jdk8

# ----- RUN MAIN----- #

run:
	java -cp .:out/:lib/*:src/main/resources lx.lindx.bash.App

run-jdk8:
	${JDK8_HOME}/bin/java -cp .:out/:lib/*:src/main/resources lx.lindx.bash.App
	

# ----- EXECUTE JAR ----- #

execute:	
	java -cp .:lib/*:bin/* lx.lindx.bash.App

# ----- DEFAUL GOAL----- #

compile-run: compile-jdk8 run