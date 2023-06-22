rm -rf target && mkdir target
javac -d target ./src/java/edu/school21/printer/app/Program.java
java -classpath ./target src.java.edu.school21.printer.app.Program /Users/stephanr/java/Java_Bootcamp._Day04-1-master/src/ex00/ImagesToChar/it.bmp --white=. --black=0
