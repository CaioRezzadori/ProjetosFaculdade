UTIL=Util/FileManager.c Util/StopWatch.c
MAIN=Maior.c
BINARY=maior

all1:
	gcc -Wall programa1.c $(UTIL) -o programa1 -I Util/

run1:
	./programa1

debug1:
	gcc -DDEBUG -Wall programa1.c $(UTIL) -o programa1 -I Util/


all2:
	gcc -Wall programa2.c $(UTIL) -o programa2 -I Util/

run2:
	./programa2

debug2:
	gcc -DDEBUG -Wall programa2.c $(UTIL) -o programa2 -I Util/


all3:
	gcc -Wall programa3.c $(UTIL) -o programa3 -I Util/

run3:
	./programa3

debug3:
	gcc -DDEBUG -Wall programa3.c $(UTIL) -o programa3 -I Util/


all4:
	gcc -Wall programa4.c $(UTIL) -o programa4 -I Util/

run4:
	./programa4

debug4:
	gcc -DDEBUG -Wall programa4.c $(UTIL) -o programa4 -I Util/


plot:
	gnuplot "plotdata" -p

clean:
	@rm *.o
