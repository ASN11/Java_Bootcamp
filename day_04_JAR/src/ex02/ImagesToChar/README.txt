rm -rf target && mkdir target



- classpath указывает путь к классам, который компилятор должен использовать при компиляции кода.
В этом случае путь к классам означает, что компилятор должен искать классы в файлах jcommander-1.82.jar и JColor-5.5.1.jar, расположенных в lib каталоге.
- sourcepath указывает путь к исходному коду для компилятора. В этом случае для пути к исходному коду задано значение .src/java,
что означает, что компилятор должен искать исходный код в src/java каталоге.
- -d указывает выходной каталог для скомпилированных файлов .class. В этом случае выходной каталог устанавливается в target.
./src/java/app/Program.java указывает путь к Program.java файлу, который необходимо скомпилировать.
./src/java/logic/ImageColor.java указывает путь к ImageColor.java файлу, который необходимо скомпилировать.

javac -classpath lib/jcommander/jcommander-1.82.jar:lib/JColor/JColor-5.5.1.jar -sourcepath .src/java -d target ./src/java/app/Program.java ./src/java/logic/ImageColor.java



- jar xf ../lib/jcommander/jcommander-1.82.jar извлекает содержимое файла jcommander-1.82.jar,
расположенного в lib/jcommander каталоге, и помещает его в текущий каталог. Параметры x(извлечь) и f(файл) используются с jar командой.
- jar xf ../lib/JColor/JColor-5.5.1.jar то же самое
- В целом, эти команды используются для извлечения содержимого двух файлов JAR (JColor и jcommander) в текущий каталог ( target)
и удаления META-INF каталога, если он существует. Каталог META-INF обычно содержит метаданные о файле JAR, такие как его манифест, и не требуется для запуска программы.

cd target && jar xf ../lib/jcommander/jcommander-1.82.jar && jar xf ../lib/JColor/JColor-5.5.1.jar && rm -rf META-INF && cd ..


mkdir -p target/resources && cp src/resources/it.bmp target/resources


- cmf src/manifest.txt указывает, что манифест для файла JAR находится в src/manifest.txt файле.
Файл манифеста описывает содержимое файла JAR и другую информацию, например номера версий.
- target/images-to-chars-printer.jar указывает имя выходного JAR-файла, который будет расположен в target каталоге.
- -C target указывает, что target каталог должен быть корневым каталогом для файла JAR,
что означает, что все файлы классов в target каталоге будут включены в файл JAR.
- .указывает текущий каталог, что означает, что любые файлы в текущем каталоге также будут включены в файл JAR.
В целом, эта команда создаст файл JAR с именем images-to-chars-printer.jar в target каталоге, используя src/manifest.txt файл в качестве манифеста
и включая все файлы классов в target каталоге, а также любые другие файлы в текущем каталоге. После создания этот файл JAR можно использовать для выполнения программы.

jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target .


java -jar target/images-to-chars-printer.jar --white=black --black=GREEN     (проверять на разных терминалах - в приложении Терминал не отображается цвет, во всех остальных работает хорошо)


Для быстрого запуска:

rm -rf target && mkdir target
javac -classpath lib/jcommander/jcommander-1.82.jar:lib/JColor/JColor-5.5.1.jar -sourcepath .src/java -d target ./src/java/app/Program.java ./src/java/logic/ImageColor.java
cd target && jar xf ../lib/jcommander/jcommander-1.82.jar && jar xf ../lib/JColor/JColor-5.5.1.jar && rm -rf META-INF && cd ..
mkdir -p target/resources && cp src/resources/it.bmp target/resources
jar cmf src/manifest.txt target/images-to-chars-printer.jar -C target .
java -jar target/images-to-chars-printer.jar --white=black --black=GREEN