This project consumes an already exposed webservice
Checks for its staus (success or fail)
Expectation is to get a success code say 200
The response retireved is then verified appropraitely

TestCase 1 : Retrieves all country list "http://services.groupkt.com/country/get/all"
Expectation is to get a list of all contries by their element "name"

TestCase 2 : Retrieves all state list from India as a country "http://services.groupkt.com/state/get/IND/all"
Expectation is to get a list of all states by their element "name"

Main class is ApiTestRunner class
Reports are generated in jbehave
org.apache.httpclient is called for consuming the WS
Assertions are carried through Junit
