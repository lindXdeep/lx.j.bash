SHELL:=/bin/bash -O globstar			# tell make to use bash.

.PHONY: all test clean						# phony target - is not name of a file, it is a name for a recipe to be executed when you make an explicit request.

.DEFAULT_GOAL:= clean-compile-run

clean:
	rm -r out/* &

copy-resources:
	cp -r src/main/resources/* out/

compile:
	javac -d out -sourcepath src/ src/**/*.java

run:
	java -cp out App

clean-compile-run: clean copy-resources compile run