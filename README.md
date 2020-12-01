# apptnt-tester

<!-- ABOUT THE PROJECT -->
## About The Project

<!-- GETTING STARTED -->
## Getting Started

Instructions to set up your project locally from github repo.

### Prerequisites

1. Install Java 1.8 JDK or above
2. Install latest MAVEN

### Execution
File 'TestConfiguration.properties' is responsible for all configuration related to environment. Like,
1. Login credentials
2. Email Configuration
3. Test data

File Path : \src\test\resources\TestConfiguration.properties

1. Create new 'Sales Order' and verify it till specific stage. Run below command.
##### mvn clean test -Dsurefire.suiteXmlFiles=SanityTestSuite.xml ##### 


2. Verify all 6 steps for a specific order step by step. Run below command: 
##### mvn clean test -Dsurefire.suiteXmlFiles=GetSpecificOrder.xml ##### 


<!-- CONTACT -->
## Contact

Your Name - [@your_twitter](https://twitter.com/your_username) - email@example.com
