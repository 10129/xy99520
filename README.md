# xy99520 
#kettle依赖
mvn  install:install-file  -Dfile=D:\kettle\lib\commons-vfs2-2.2.jar  -DgroupId=pentaho-kettle  -DartifactId=vfs2  -Dversion=2-2.2  -Dpackaging=jar
mvn install:install-file -Dfile=D:\kettle\lib\kettle-core-8.2.0.0-342.jar -DgroupId=pentaho-kettle -DartifactId=kettle-core  -Dversion=8.2.0.0-342  -Dpackaging=jar
mvn  install:install-file  -Dfile=D:\kettle\lib\kettle-engine-8.2.0.0-342.jar  -DgroupId=pentaho-kettle  -DartifactId=kettle-engine  -Dversion=8.2.0.0-342  -Dpackaging=jar
mvn  install:install-file  -Dfile=D:\kettle\lib\metastore-8.2.0.0-342.jar  -DgroupId=pentaho-kettle  -DartifactId=metastore  -Dversion=8.2.0.0-342  -Dpackaging=jar
mvn  install:install-file  -Dfile=D:\kettle\lib\scannotation-1.0.2.jar  -DgroupId=pentaho-kettle  -DartifactId=scannotation  -Dversion=1.0.2  -Dpackaging=jar
mvn  install:install-file  -Dfile=D:\kettle\lib\simple-jndi-1.0.0.jar  -DgroupId=simple-jndi  -DartifactId=simple-jndi  -Dversion=1.0.0  -Dpackaging=jar
