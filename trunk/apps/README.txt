Notes:
The inheritance in poms has been removed so to build the modules follow the next steps:

1-Remove .m2/repository/org/ontospread
2-Install module ontospread: 
	cd ontospread
	mvn install
3-Build other modules:
	Ex: cd ontospread-test
	    mvn compile

Contact:

Jose María Alvarez Rodriguez

chema.ar@gmail.com