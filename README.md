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

#### ALLOWED STAGES ####
* ALL
* GET_ORDER
* INSPECT_AND_PACK
* RFQ
* BOOK
* PICKUP_AND_SHIP
* INVOICE
* FEEDBACK

1. Create new 'Sales Order' and verify it till specific stage. Run below command.
##### mvn clean -Dupto_stage="BOOK" test  -Dsurefire.suiteXmlFiles=SanityTestSuite.xml ##### 


2. Verify all 6 steps for a specific order step by step. Run below command: 
##### mvn clean -Dupto_stage="BOOK" -Dorder_id=136 test  -Dsurefire.suiteXmlFiles=GetSpecificOrder.xml ##### 


<!-- CONTACT -->
## Contact

Your Name - [@your_twitter](https://twitter.com/your_username) - email@example.com
