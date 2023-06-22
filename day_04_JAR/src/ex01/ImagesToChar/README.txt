rm -rf target && mkdir target

javac -d target ./src/java/app/Program.java

mkdir -p target/resources && cp src/resources/it.bmp target/resources

jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target .

java -jar target/images-to-chars-printer.jar --white=" " --black=o

