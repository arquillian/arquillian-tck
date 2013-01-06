Arquillian Test Compliance Kit
==============================

Container TCK
-------------

Contain tests to verify common technology integration and core behavior related to implementing a correct Container Adapter.

Maven Setup example

```xml
<profile>
    <id>container-tck</id>
    <properties>
        <version.arquillian_core>1.0.3.Final</version.arquillian_core>
        <version.shrinkwrap_descriptors>2.0.0-alpha-4</version.shrinkwrap_descriptors>
        <tck.classes>${project.build.directory}/tck-test-classes</tck.classes>
        <tck.reports>${project.build.directory}/surefire-reports/tck</tck.reports>
    </properties>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.jboss.arquillian</groupId>
                <artifactId>arquillian-bom</artifactId>
                <version>${version.arquillian_core}</version>
                <scope>import</scope>
                <type>pom</type>
            </dependency>
            <dependency>
                <groupId>org.jboss.shrinkwrap.descriptors</groupId>
                <artifactId>shrinkwrap-descriptors-bom</artifactId>
                <version>${version.shrinkwrap_descriptors}</version>
                <scope>import</scope>
                <type>pom</type>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.jboss.arquillian.junit</groupId>
            <artifactId>arquillian-junit-container</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.shrinkwrap.descriptors</groupId>
            <artifactId>shrinkwrap-descriptors-impl-javaee</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <executions>
                    <execution>
                        <id>tck-unpack</id>
                        <phase>process-test-classes</phase>
                        <goals>
                            <goal>unpack</goal>
                        </goals>
                        <configuration>
                            <artifactItems>
                                <artifactItem>
                                    <groupId>org.arquillian.tck.container</groupId>
                                    <artifactId>arquillian-tck-container</artifactId>
                                    <version>1.0.0.Final-SNAPSHOT</version>
                                    <classifier>tests</classifier>
                                    <outputDirectory>${tck.classes}</outputDirectory>
                                </artifactItem>
                            </artifactItems>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.13</version>
                <executions>
                    <execution>
                        <id>tck-tests</id>
                        <phase>test</phase>
                        <goals>
                            <goal>test</goal>
                        </goals>
                        <configuration>
                            <!-- <groups>org.arquillian.tck.group.EE6</groups> -->
                            <!-- <excludedGroups>org.arquillian.tck.group.EE5</excludedGroups> -->
                            <testClassesDirectory>${tck.classes}</testClassesDirectory>
                            <reportsDirectory>${tck.reports}</reportsDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</profile>
```


TODO: 

Container Adapters

Core

  @Deployment
		jar / war / ear
		
	@Deployment multiple
	
	ContainerController
		Custom Mode
		Manual Mode	

	

RunAsClient

@ArquillianResource
	Deployer (deploy / undeploy)
	
	Context/InitialContext


InContainer

EJB 3.0

	@EJB inject Local
	@EJB inject Remote
		
	jar / ear
	
EJB 3.1

	@EJB inject Local
	@EJB inject Remote
		
	jar / war / ear

Resource
	@Resource inject jndi binding
	
